package com.example.wealthwatch.domain.model.asset

data class Transaction(
    val id: Int = 0,
    val assetSymbol: String,
    val isBuy: Boolean,
    val amount: Double,
    val price: Double,
    val totalValue: Double,
    val date: Long,
    val note: String? = null,
    val fee: Double = 0.0,
    val exchangeRate: Double = 1.0
)
