package com.isaac.budgetingapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast


class SalaryBudgetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salary_budget)

        val radioGroupFrequency = findViewById<RadioGroup>(R.id.radioGroup_frequency)
        val radioBiWeekly = findViewById<RadioButton>(R.id.radio_bi_weekly)
        val radioMonthly = findViewById<RadioButton>(R.id.radio_monthly)
        val buttonContinue = findViewById<Button>(R.id.button_continue)

        var selectedBudgetType: String? = null

        radioBiWeekly.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedBudgetType = "bi-weekly"
            }
        }

        radioMonthly.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedBudgetType = "monthly"
            }
        }

        buttonContinue.setOnClickListener {
            if (selectedBudgetType != null) {
                saveBudgetType(selectedBudgetType!!)
                finish()
                Toast.makeText(this, "Budget Type set successfully!", Toast.LENGTH_SHORT).show()
            } else {
                // Display a message asking the user to choose an option
                Toast.makeText(this, "Please choose one of the options", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun saveBudgetType(budgetType: String) {
        val sharedPreferences = getSharedPreferences("budget_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("budget_type", budgetType)
        editor.apply()
    }
}