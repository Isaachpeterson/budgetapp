package com.isaac.budgetingapplication

data class BudgetItem(
    val id: Int,
    val name: String,
    val total: Double,
    val spent: Double
)
