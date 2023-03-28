package com.isaac.budgetingapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

class BudgetOverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_overview)

        val linearLayoutCategoryProgress = findViewById<LinearLayout>(R.id.linear_layout_category_progress)

        val sharedPreferences = getSharedPreferences("budget_preferences", Context.MODE_PRIVATE)
        val categoriesCount = sharedPreferences.getInt("categories_count", 0)

        for (i in 0 until categoriesCount) {
            val categoryName = sharedPreferences.getString("category_name_$i", "")
            val categoryPercentage = sharedPreferences.getString("category_percentage_$i", "0") ?: "0"

            val categoryProgressView = LayoutInflater.from(this).inflate(R.layout.category_progress_item, null, false)

            val categoryNameTextView = categoryProgressView.findViewById<TextView>(R.id.text_category_name)
            categoryNameTextView.text = categoryName

            val categoryProgressBar = categoryProgressView.findViewById<ProgressBar>(R.id.progress_bar_category)
// Set the progress bar max value based on the category percentage and total income.
            val income = getSavedIncome() ?: 0f
            val categoryMaxValue = (income * (categoryPercentage.toFloatOrNull() ?: 0f) / 100).toInt()
            categoryProgressBar.max = categoryMaxValue
            linearLayoutCategoryProgress.addView(categoryProgressView)
        }
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