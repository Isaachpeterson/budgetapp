package com.isaac.budgetingapplication

data class SavingsGoal(
    val id: Int,
    val name: String,
    val targetAmount: Double,
    var currentAmount: Double = 0.0
)