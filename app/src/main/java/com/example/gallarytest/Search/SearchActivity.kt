package com.example.gallarytest.Search

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallarytest.PhotosAdapter
import com.example.gallarytest.R
import com.example.gallarytest.network.ApiClient
import com.example.gallarytest.network.ApiService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var searchButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var photoAdapter: PhotoAdapter2

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.flickr.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = ApiClient.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        editText = findViewById(R.id.editText)
        searchButton = findViewById(R.id.searchButton)
        recyclerView = findViewById(R.id.recyclerView)

     photoAdapter = PhotoAdapter2()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = photoAdapter

        searchButton.setOnClickListener {
            lifecycleScope.launch {
                makeApiCall()
            }
        }
    }



    private suspend fun makeApiCall() {
        val searchKeyword = editText.text.toString()
        try {
            val searchResponse = apiService.searchPhotos(
                method = "flickr.photos.search",
                apiKey = "6f102c62f41998d151e5a1b48713cf13",
                format = "json",
                noJsonCallback = 1,
                extras = "url_s",
                text = searchKeyword,

            )
            if (true) {


                val photos = searchResponse.photos.photo.map { photo ->
                    PhotoItem(photo.id, photo.title, photo.url_s)
                }
                withContext(Dispatchers.Main) {

                    photoAdapter.updatePhotos(photos)
                }
            } else {
                showRetrySnackbar()
            }
        } catch (e: Exception) {
            showRetrySnackbar()
        }
    }


    private fun showRetrySnackbar() {
        Snackbar.make(recyclerView, "Network Error. Retry?", Snackbar.LENGTH_LONG)
            .setAction("Retry") {
                lifecycleScope.launch {
                    makeApiCall()
                }
            }
            .show()
    }
}
