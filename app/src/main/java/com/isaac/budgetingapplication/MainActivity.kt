package com.isaac.budgetingapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonFromSalary = findViewById<Button>(R.id.button_from_salary)
        val buttonFromInput = findViewById<Button>(R.id.button_from_input)

        buttonFromSalary.setOnClickListener {
            val intent = Intent(this, SalaryBudgetActivity::class.java)
            startActivity(intent)
        }

        buttonFromInput.setOnClickListener {
            // Handle the click event for "From Input" button
        }
    }
}
