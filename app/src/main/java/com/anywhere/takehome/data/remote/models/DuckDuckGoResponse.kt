package com.anywhere.takehome.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DuckDuckGoResponse(
    @SerialName("Abstract")
    val `abstract`: String = "",
    @SerialName("AbstractSource")
    val abstractSource: String = "",
    @SerialName("AbstractText")
    val abstractText: String = "",
    @SerialName("AbstractURL")
    val abstractURL: String = "",
    @SerialName("Answer")
    val answer: String = "",
    @SerialName("AnswerType")
    val answerType: String = "",
    @SerialName("Definition")
    val definition: String = "",
    @SerialName("DefinitionSource")
    val definitionSource: String = "",
    @SerialName("DefinitionURL")
    val definitionURL: String = "",
    @SerialName("Entity")
    val entity: String = "",
    @SerialName("Heading")
    val heading: String = "",
    @SerialName("Image")
    val image: String = "",
    @SerialName("Redirect")
    val redirect: String = "",
    @SerialName("RelatedTopics")
    val relatedTopics: List<RelatedTopic> = listOf(),
    @SerialName("Type")
    val type: String = ""
)