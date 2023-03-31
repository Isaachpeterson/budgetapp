package com.isaac.budgetingapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.isaac.budgetingapplication.R

class BudgetItemAdapter(private var budgetItems: List<BudgetItem>) :
    RecyclerView.Adapter<BudgetItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textBudgetCategory: TextView = itemView.findViewById(R.id.text_budget_category)
        val textBudgetSpent: TextView = itemView.findViewById(R.id.text_budget_spent)
        val progressBudget: ProgressBar = itemView.findViewById(R.id.progress_budget)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.budget_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = budgetItems[position]
        holder.textBudgetCategory.text = item.category
        holder.textBudgetSpent.text = "${item.spent} / ${item.total}"
        holder.progressBudget.max = item.total.toInt()
        holder.progressBudget.progress = item.spent.toInt()
    }

    override fun getItemCount(): Int {
        return budgetItems.size
    }

    fun updateBudgetItems(newBudgetItems: List<BudgetItem>) {
        budgetItems = newBudgetItems
        notifyDataSetChanged()
    }
}
