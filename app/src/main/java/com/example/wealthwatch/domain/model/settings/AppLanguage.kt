package com.example.wealthwatch.domain.model.settings

import androidx.annotation.StringRes
import com.example.wealthwatch.R

enum class AppLanguage(
    val code: String,
    @StringRes val titleResId: Int
) {
    TURKISH("tr", R.string.language_tr),
    ENGLISH("en", R.string.language_en);

    companion object {
        fun fromCode(code: String): AppLanguage = entries.find { it.code == code } ?: ENGLISH
    }
}
