package com.isaac.budgetingapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BudgetCategoryAdapter(
    private val context: Context,
    private val budgetCategories: List<BudgetItem>,
    private val onDepositWithdrawClickListener: (BudgetItem) -> Unit,
    private val onEditClickListener: (BudgetItem) -> Unit,
    private val onDeleteClickListener: (BudgetItem) -> Unit,
    private val budgetItems: MutableList<BudgetItem>
) : RecyclerView.Adapter<BudgetCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_budget_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val budgetCategory = budgetCategories[position]
        holder.textCategoryName.text = budgetCategory.name
        holder.textSpentAmount.text = "${budgetCategory.spent} / ${budgetCategory.total}"

        holder.progressBar.max = budgetCategory.total.toInt()
        holder.progressBar.progress = budgetCategory.spent.toInt()

        holder.buttonDepositWithdraw.setOnClickListener {
            onDepositWithdrawClickListener(budgetCategory)
        }

        holder.buttonEdit.setOnClickListener {
            onEditClickListener(budgetCategory)
        }

        holder.buttonDelete.setOnClickListener {
            onDeleteClickListener(budgetCategory)
        }
    }

    override fun getItemCount(): Int {
        return budgetCategories.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textCategoryName: TextView = itemView.findViewById(R.id.text_category_name)
        val textSpentAmount: TextView = itemView.findViewById(R.id.text_spent_amount)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_budget)
        val buttonDepositWithdraw: ImageButton = itemView.findViewById(R.id.button_deposit_withdraw)
        val buttonEdit: ImageButton = itemView.findViewById(R.id.button_edit)
        val buttonDelete: ImageButton = itemView.findViewById(R.id.button_delete)
    }

    fun addBudgetItem(newBudgetItem: BudgetItem) {
        budgetItems.add(newBudgetItem)
        notifyItemInserted(budgetItems.size - 1)
    }
}