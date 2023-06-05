package com.example.gallarytest.Search



data class SearchResponse(
    val photos: Photos,
    val stat: String
)

data class Photos(
    val page: Int,
    val pages: Int,
    val perPage: Int,
    val total: Int,
    val photo: List<PhotoItem>
)

data class PhotoItem(
    val id: String,
    val title: String,
    val url_s: String
)
