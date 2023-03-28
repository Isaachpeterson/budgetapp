package com.isaac.budgetingapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val buttonBudgetType = findViewById<Button>(R.id.button_budget_type)
        buttonBudgetType.setOnClickListener {
            val intent = Intent(this, SalaryBudgetActivity::class.java)
            startActivity(intent)
        }

        val buttonSetIncome = findViewById<Button>(R.id.button_set_income)
        buttonSetIncome.setOnClickListener {
            val intent = Intent(this, SetIncomeActivity::class.java)
            startActivity(intent)
        }

        val buttonEditBudget = findViewById<Button>(R.id.button_edit_budget)
        buttonEditBudget.setOnClickListener {
            val intent = Intent(this, EditBudgetActivity::class.java)
            startActivity(intent)
        }

        val buttonBudgetOverview = findViewById<Button>(R.id.button_budget_overview)
        buttonBudgetOverview.setOnClickListener {
            val intent = Intent(this, BudgetOverviewActivity::class.java)
            startActivity(intent)
        }

        updateBudgetTypeTextView()
        updateSavedIncomeTextView()
    }

    override fun onResume() {
        super.onResume()
        updateBudgetTypeTextView()
        updateSavedIncomeTextView()
    }

    private fun getBudgetType(): String? {
        val sharedPreferences = getSharedPreferences("budget_preferences", Context.MODE_PRIVATE)
        return sharedPreferences.getString("budget_type", null)
    }

    private fun getSavedIncome(): Float? {
        val sharedPreferences = getSharedPreferences("budget_preferences", Context.MODE_PRIVATE)
        return if (sharedPreferences.contains("monthly_income")) {
            sharedPreferences.getFloat("monthly_income", 0f)
        } else {
            null
        }
    }


    private fun updateBudgetTypeTextView() {
        val currentBudgetType = getBudgetType()
        val textViewCurrentBudgetType = findViewById<TextView>(R.id.text_current_budget_type)

        if (currentBudgetType != null) {
            textViewCurrentBudgetType.text = "Current Budget Type: $currentBudgetType"
        } else {
            textViewCurrentBudgetType.text = "Current Budget Type: Not set"
        }
    }

    private fun updateSavedIncomeTextView() {
        val savedIncome = getSavedIncome()
        val textViewSavedIncome = findViewById<TextView>(R.id.text_saved_income)

        if (savedIncome != null) {
            textViewSavedIncome.text = "Saved Income: $savedIncome"
        } else {
            textViewSavedIncome.text = "Saved Income: Not set"
        }
    }
}