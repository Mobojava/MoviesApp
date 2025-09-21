package com.movis.app.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Model_user(
    val createdAt: String?,
    val email: String?,
    val id: Int?,
    val name: String?,
    val updatedAt: String?
)