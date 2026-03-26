package com.example.wealthwatch.presentation.market

sealed interface SectionType {
    data object Standard : SectionType
    data object Gainer : SectionType
    data object Loser : SectionType
}