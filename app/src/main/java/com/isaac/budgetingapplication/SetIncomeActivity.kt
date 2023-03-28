package com.isaac.budgetingapplication

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SetIncomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_income)

        val editIncomeAmount = findViewById<EditText>(R.id.edit_income_amount)
        val buttonSaveIncome = findViewById<Button>(R.id.button_save_income)

        buttonSaveIncome.setOnClickListener {
            val incomeString = editIncomeAmount.text.toString()
            if (incomeString.isNotEmpty()) {
                val income = incomeString.toFloat()
                saveIncome(income)
                finish()
            } else {
                // Display a message to the user to enter the income
            }
        }
    }

    private fun saveIncome(income: Float) {
        val sharedPreferences = getSharedPreferences("budget_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("monthly_income", income)
        editor.apply()
    }
}
