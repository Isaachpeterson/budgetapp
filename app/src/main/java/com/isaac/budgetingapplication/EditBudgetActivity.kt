package com.isaac.budgetingapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EditBudgetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_budget)

        val buttonCreateCategory = findViewById<Button>(R.id.button_create_category)
        val linearLayoutCategories = findViewById<LinearLayout>(R.id.linear_layout_categories)
        val textMoneyLeft = findViewById<TextView>(R.id.text_money_left)

        // Get the saved income
        val savedIncome = getSavedIncome()
        if (savedIncome != null) {
            textMoneyLeft.text = "Money left to budget: $savedIncome"
        } else {
            textMoneyLeft.text = "Money left to budget: Not set"
        }

        buttonCreateCategory.setOnClickListener {
            val categoryView = LayoutInflater.from(this).inflate(R.layout.category_item, null, false)
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
        editor.putInt("categories_count", categoriesCount)

        for (i in 0 until categoriesCount) {
            val categoryLayout = linearLayoutCategories.getChildAt(i) as LinearLayout
            val categoryName = categoryLayout.findViewById<EditText>(R.id.edit_category_name).text.toString()
            val categoryPercentage = categoryLayout.findViewById<EditText>(R.id.edit_percentage).text.toString()
            editor.putString("category_name_$i", categoryName)
            editor.putString("category_percentage_$i", categoryPercentage)
        }

        editor.apply()
        Toast.makeText(this, "Budget settings saved", Toast.LENGTH_SHORT).show()
    }

    private fun getSavedIncome(): Float? {
        val sharedPreferences = getSharedPreferences("budget_preferences", Context.MODE_PRIVATE)
        return if (sharedPreferences.contains("monthly_income")) {
            sharedPreferences.getFloat("monthly_income", 0f)
        } else {
            null
        }
    }
}
