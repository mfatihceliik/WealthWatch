package com.example.wealthwatch.domain.use_case.asset

import com.example.wealthwatch.domain.model.asset.AssetType
import com.example.wealthwatch.domain.use_case.transaction.AddTransactionUseCase
import jakarta.inject.Inject

class SellAssetUseCase @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase
) {
    suspend operator fun invoke(
        symbol: String,
        name: String,
        type: AssetType,
        amount: Double,
        price: Double,
        note: String = ""
    ) {
        addTransactionUseCase(
            AddTransactionUseCase.Params(
                symbol = symbol,
                name = name,
                type = type,
                amount = amount,
                price = price,
                isBuy = false,
                note = note
            )
        )
    }
}
