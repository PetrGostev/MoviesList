package ru.petrgostev.movieslist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Response(
    @SerialName("results")
    val results: List<ResultsItem>,
)

@Serializable
class Multimedia(
    @SerialName("src")
    val src: String?,
)

@Serializable
data class ResultsItem(
    @SerialName("multimedia")
    val multimedia: Multimedia?,

    @SerialName("display_title")
    val displayTitle: String?,

    @SerialName("summary_short")
    val summaryShort: String?
)

