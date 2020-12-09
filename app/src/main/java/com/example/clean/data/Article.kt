package com.example.clean.data

//Data class holding an article to be saved on Firebase
data class Article (
    var urlToImage: String? = "",
    val author: String? = "",
    var source: String = "",
    var publishedAt: String? = "",
    var description: String = "",
    var title: String = "",
    var url: String = "",
)