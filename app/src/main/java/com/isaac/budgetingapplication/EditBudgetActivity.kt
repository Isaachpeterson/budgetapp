package com.isaac.budgetingapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class EditBudgetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_budget)

        val buttonCreateCategory = findViewById<Button>(R.id.button_create_category)
        val linearLayoutCategories = findViewById<LinearLayout>(R.id.linear_layout_categories)
        val textMoneyLeft = findViewById<TextView>(R.id.text_money_left)

        loadSavedCategories(linearLayoutCategories)

        // Get the saved income
        val savedIncome = getSavedIncome()
        if (savedIncome != null) {
            textMoneyLeft.text = "Money left to budget: $savedIncome"
        } else {
            textMoneyLeft.text = "Money left to budget: Not set"
        }

        buttonCreateCategory.setOnClickListener {
            val categoryView = createCategoryView()
            linearLayoutCategories.addView(categoryView)
        }

        val buttonSaveBudget = findViewById<Button>(R.id.button_save_budget)
        buttonSaveBudget.setOnClickListener {
            saveBudgetSettings(linearLayoutCategories)
            finish()
        }
    }

    private fun saveBudgetSettings(linearLayoutCategories: LinearLayout) {
        val sharedPreferences = getSharedPreferences("budget_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val categoriesCount = linearLayoutCategories.childCount
        val budgetItems = mutableListOf<BudgetItem>()

        for (i in 0 until categoriesCount) {
            val categoryLayout = linearLayoutCategories.getChildAt(i) as LinearLayout
            val categoryName = categoryLayout.findViewById<EditText>(R.id.edit_category_name).text.toString()
            val categoryLimit = categoryLayout.findViewById<EditText>(R.id.edit_percentage).text.toString().toDoubleOrNull()

            if (categoryName.isNotEmpty() && categoryLimit != null) {
                budgetItems.add(BudgetItem(i + 1, categoryName, 0.0, categoryLimit))
            }
        }

        val gson = Gson()
        val budgetItemsJson = gson.toJson(budgetItems)
        editor.putString("budget_items", budgetItemsJson)

        editor.apply()
        Toast.makeText(this, "Budget settings saved", Toast.LENGTH_SHORT).show()
    }


    private fun saveBudgetItems(budgetItems: List<BudgetItem>) {
        val sharedPreferences = getSharedPreferences("budget_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        val json = gson.toJson(budgetItems)
        editor.putString("budget_items", json)
        editor.apply()
    }

    private fun getSavedIncome(): Float? {
        val sharedPreferences = getSharedPreferences("budget_preferences", Context.MODE_PRIVATE)
        return if (sharedPreferences.contains("monthly_income")) {
            sharedPreferences.getFloat("monthly_income", 0f)
        } else {
            null
        }
    }

    private fun loadSavedCategories(linearLayoutCategories: LinearLayout) {
        val sharedPreferences = getSharedPreferences("budget_preferences", Context.MODE_PRIVATE)
        val budgetItemsJson = sharedPreferences.getString("budget_items", "")

        if (budgetItemsJson != "") {
            val gson = Gson()
            val budgetItems = gson.fromJson(budgetItemsJson, Array<BudgetItem>::class.java).toList()

            for (budgetItem in budgetItems) {
                val categoryView = createCategoryView()
                categoryView.findViewById<EditText>(R.id.edit_category_name).setText(budgetItem.category)
                categoryView.findViewById<EditText>(R.id.edit_percentage).setText(budgetItem.total.toString())

                linearLayoutCategories.addView(categoryView)
            }
        }
    }


    private fun createCategoryView(): View {
        val categoryView = LayoutInflater.from(this).inflate(R.layout.category_item, null, false)

        val deleteButton = Button(this)
        deleteButton.text = "Delete"
        deleteButton.setOnClickListener {
            (categoryView.parent as? ViewGroup)?.removeView(categoryView)
        }

        categoryView.findViewById<LinearLayout>(R.id.category_item_container).addView(deleteButton)
        return categoryView
    }
}
