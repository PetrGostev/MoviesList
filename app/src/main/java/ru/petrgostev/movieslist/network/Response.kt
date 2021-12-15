package ru.petrgostev.movieslist

import com.google.gson.annotations.SerializedName


class Response(
    @SerializedName("results")
    val results: List<ResultsItem>,
)

class Multimedia(
    @SerializedName("src")
    val src: String? = null,

    @SerializedName("type")
    val type: String? = null,
)

data class ResultsItem(
    @SerializedName("multimedia")
    val multimedia: Multimedia? = null,

    @SerializedName("display_title")
    val displayTitle: String,

    @SerializedName("summary_short")
    val summaryShort: String? = null,

    @SerializedName("headline")
    val headline: String? = null,
)

