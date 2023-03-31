package com.isaac.budgetingapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SavingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savings)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // Sample data for testing
        val sampleGoals = listOf(
            SavingsGoal(1, "Italy Vacation", 5000.0, 0.0),
            SavingsGoal(2, "New Laptop", 3000.0, 2500.0)
        )

        val savingsGoalRecyclerView = findViewById<RecyclerView>(R.id.savings_goal_recyclerview)
        savingsGoalRecyclerView.layoutManager = LinearLayoutManager(this)
        savingsGoalRecyclerView.adapter = SavingsGoalAdapter(sampleGoals)
    }
}
