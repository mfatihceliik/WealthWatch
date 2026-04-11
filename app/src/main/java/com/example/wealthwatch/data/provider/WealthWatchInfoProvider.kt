package com.example.wealthwatch.data.provider

import android.content.Context
import com.example.wealthwatch.core.provider.AppInfoProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WealthWatchInfoProvider @Inject constructor(
    @param:ApplicationContext private val context: Context
) : AppInfoProvider {

    companion object {
        private val TAG = this::class.simpleName
    }

    override val versionName: String
        get() = try {
            context.packageManager.getPackageInfo(context.packageName, 0).versionName ?: "0.0"
        } catch (e: Exception) {
            "Unknown"
        }
}
