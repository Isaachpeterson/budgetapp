package com.isaac.budgetingapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BudgetCategoryActivity : AppCompatActivity() {

    private lateinit var budgetCategoryRecyclerView: RecyclerView
    private lateinit var budgetCategoryAdapter: BudgetCategoryAdapter
    private lateinit var buttonCreateNew: Button
    private lateinit var budgetItems: MutableList<BudgetItem>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_category)

        budgetCategoryRecyclerView = findViewById(R.id.budget_category_recyclerview)
        budgetCategoryRecyclerView.layoutManager = LinearLayoutManager(this)

        val budgetCategories = getBudgetCategories()

        budgetCategoryAdapter = BudgetCategoryAdapter(
            budgetCategories,
            this,
            { category -> onDepositWithdraw(category) },
            { category -> onEdit(category) },
            { category -> onDelete(category) },
            budgetItems
        )
        budgetCategoryRecyclerView.adapter = budgetCategoryAdapter

        buttonCreateNew = findViewById(R.id.button_create_new)
        buttonCreateNew.setOnClickListener {
            showCreateNewDialog()
        }
    }

    private fun onDepositWithdraw(category: BudgetItem) {
    }

    private fun onEdit(category: BudgetItem) {
    }

    private fun onDelete(category: BudgetItem) {
    }

    private fun getBudgetCategories(): List<BudgetItem> {
        val sharedPreferences = getSharedPreferences("budget_preferences", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("budget_items", null)

        return if (json != null) {
            val gson = Gson()
            val type = object : TypeToken<List<BudgetItem>>() {}.type
            gson.fromJson(json, type)
        } else {
            listOf()
        }
    }

    private fun showCreateNewDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_new_category, null)
        val categoryNameEditText = dialogView.findViewById<EditText>(R.id.edit_category_name)
        val categoryPercentageEditText = dialogView.findViewById<EditText>(R.id.edit_percentage)

        val builder = AlertDialog.Builder(this)
            .setTitle("Create New Category")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val categoryName = categoryNameEditText.text.toString()
                val categoryPercentage = categoryPercentageEditText.text.toString().toDoubleOrNull()

                if (categoryName.isNotEmpty() && categoryPercentage != null) {
                    val sharedPreferences = getSharedPreferences("budget_preferences", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()

                    val newBudgetItem = BudgetItem(budgetItems.size + 1, categoryName, 0.0, categoryPercentage)
                    budgetItems.add(newBudgetItem)

                    val gson = Gson()
                    val budgetItemsJson = gson.toJson(budgetItems)
                    editor.putString("budget_items", budgetItemsJson)
                    editor.apply()

                    // Refresh the RecyclerView
                    budgetCategoryAdapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancel", null)
        builder.create().show()
    }
}
