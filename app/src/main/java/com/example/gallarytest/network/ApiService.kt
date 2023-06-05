package com.example.gallarytest.network


import com.example.gallarytest.Search.SearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("services/rest/?method=flickr.photos.getRecent")
    suspend fun getRecentPhotos(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("extras") extras: String = "url_s"
    ): Assignment

    @GET("services/rest/?method=flickr.photos.search")
    suspend fun searchPhotos(
        @Query("text") text: String,
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String="6f102c62f41998d151e5a1b48713cf13",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("extras") extras: String = "url_s"
    ):SearchResponse
}

object ApiClient {
    private const val BASE_URL = "https://api.flickr.com/"

    fun create(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}

