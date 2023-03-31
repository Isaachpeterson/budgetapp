package com.isaac.budgetingapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BudgetOverviewActivity : AppCompatActivity() {

    private lateinit var budgetOverviewRecyclerView: RecyclerView
    private lateinit var budgetCategoryAdapter: BudgetCategoryAdapter
    private lateinit var buttonCreateNew: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_overview)

        budgetOverviewRecyclerView = findViewById(R.id.budget_overview_recyclerview)
        budgetOverviewRecyclerView.layoutManager = LinearLayoutManager(this)

        val budgetItems = getBudgetItems()

        budgetCategoryAdapter = BudgetCategoryAdapter(budgetItems, this)
        budgetOverviewRecyclerView.adapter = budgetCategoryAdapter

        buttonCreateNew = findViewById(R.id.button_create_new)
        buttonCreateNew.setOnClickListener {
            showCreateNewCategoryDialog()
        }
    }

    private fun getBudgetItems(): List<BudgetItem> {
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

    private fun showCreateNewCategoryDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_create_new_category, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Create New Category")
            .setPositiveButton("Save", null)
            .setNegativeButton("Cancel") { _, _ -> }
            .create()

        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                val categoryNameInput = dialogView.findViewById<EditText>(R.id.edit_category_name)
                val percentageInput = dialogView.findViewById<EditText>(R.id.edit_percentage)

                val categoryName = categoryNameInput.text.toString()
                val percentage = percentageInput.text.toString().toDoubleOrNull()

                if (categoryName.isNotEmpty() && percentage != null) {
                    val newBudgetItem = BudgetItem(id = getNextId(), category = categoryName, spent = 0.0, total = percentage)
                    saveNewBudgetItem(newBudgetItem)
                    budgetCategoryAdapter.addBudgetItem(newBudgetItem)
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialog.show()
    }

    private fun getNextId(): Int {
        val budgetItems = getBudgetItems()
        return (budgetItems.maxByOrNull { it.id }?.id ?: 0) + 1
    }

    private fun saveNewBudgetItem(budgetItem: BudgetItem) {
        val budgetItems = getBudgetItems().toMutableList()
        budgetItems.add(budgetItem)

        val sharedPreferences = getSharedPreferences("budget_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        val budgetItemsJson = gson.toJson(budgetItems)
        editor.putString("budget_items", budgetItemsJson)
        editor.apply()
    }

}