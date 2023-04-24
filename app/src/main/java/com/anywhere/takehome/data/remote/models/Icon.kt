package com.anywhere.takehome.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Icon(
    @SerialName("Height")
    val height: String = "",
    @SerialName("URL")
    val url: String = "",
    @SerialName("Width")
    val width: String = ""
)