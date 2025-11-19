package com.viplearner.api.models

import kotlinx.serialization.Serializable

@Serializable
data class AccountPreferencesResponse(
    val prefs: UserPreferences? = null,
    val language: String? = null
)

