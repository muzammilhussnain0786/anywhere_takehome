package com.anywhere.takehome.data.remote.models


import com.anywhere.takehome.BuildConfig
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RelatedTopic(
    @SerialName("FirstURL")
    val firstURL: String = "",
    @SerialName("Icon")
    val icon: Icon = Icon(),
    @SerialName("Result")
    val result: String = "",
    @SerialName("Text")
    val text: String = ""
){
    fun getCharacterName(): String{
        return text.split("-").first()
    }

    fun getImageUrl(): String{
        return BuildConfig.BASE_URL + icon.url
    }
}