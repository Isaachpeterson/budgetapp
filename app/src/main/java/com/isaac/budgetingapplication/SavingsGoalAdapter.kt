package com.isaac.budgetingapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.isaac.budgetingapplication.R

class SavingsGoalAdapter(private val savingsGoals: List<SavingsGoal>) :
    RecyclerView.Adapter<SavingsGoalAdapter.SavingsGoalViewHolder>() {

    inner class SavingsGoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val goalName: TextView = itemView.findViewById(R.id.savings_goal_name)
        val goalProgress: ProgressBar = itemView.findViewById(R.id.savings_goal_progress)
        val goalAmount: TextView = itemView.findViewById(R.id.savings_goal_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingsGoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.savings_goal_item, parent, false)
        return SavingsGoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavingsGoalViewHolder, position: Int) {
        val goal = savingsGoals[position]
        holder.goalName.text = goal.name
        holder.goalProgress.max = goal.targetAmount.toInt()
        holder.goalProgress.progress = goal.currentAmount.toInt()
        holder.goalAmount.text = String.format("%.2f / %.2f", goal.currentAmount, goal.targetAmount)
    }

    override fun getItemCount(): Int {
        return savingsGoals.size
    }
}
