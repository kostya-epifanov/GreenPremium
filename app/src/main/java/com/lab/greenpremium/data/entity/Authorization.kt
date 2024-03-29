package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName

data class AuthRequest(@SerializedName("login") val login: String,
                       @SerializedName("password") val password: String)

data class AuthResponse(@SerializedName("id") val id: String?,
                        @SerializedName("name") val name: String?,
                        @SerializedName("email") val email: String?,
                        @SerializedName("phone") val phone: String?,
                        @SerializedName("position") val position: String?,
                        @SerializedName("photo") val photo: String?,
                        @SerializedName("token") val token: String,
                        @SerializedName("is_demo") val is_demo: Boolean) {

    constructor(token: String) : this(null, null, null, null, null, null, token, false)

    val time: Long = System.currentTimeMillis()
}