package com.example.gallarytest

import MainViewModel
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallarytest.Search.SearchActivity
import com.example.gallarytest.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var photosAdapter: PhotosAdapter
    private lateinit var viewModel: MainViewModel

    private val totalPages = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        photosAdapter = PhotosAdapter()

        val layoutManager = GridLayoutManager(this, 1)
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.adapter = photosAdapter.withLoadStateFooter(PhotosLoadStateAdapter { photosAdapter.retry() })

        loadPages(totalPages)
        supportActionBar?.hide()

        photosAdapter.addLoadStateListener { loadStates ->
            val loadState = loadStates.refresh

            if (loadState is LoadState.Loading) {
                showLoader()
            } else {
                hideLoader()
            }

            val errorState = loadState as? LoadState.Error
            val errorMsg = errorState?.error?.message
            if (errorMsg != null) {
                showError(errorMsg)
            }
        }
        binding.search.setOnClickListener {
            val i = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(i)
        }

    }

    private fun loadPages(totalPages: Int) {
        lifecycleScope.launch {
            for (page in 1..totalPages) {
                viewModel.getPhotos(page).collectLatest { pagingData ->
                    photosAdapter.submitData(pagingData)
                }
            }
        }
    }

    private fun showLoader() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        binding.progressBar.visibility = View.GONE
    }


    private fun showError(errorMsg: String) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
    }
}
