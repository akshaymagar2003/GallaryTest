package com.example.gallarytest

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.gallarytest.network.ApiService
import com.example.gallarytest.network.Photo

class PhotoPagingSource(
    private val apiService: ApiService,
    private val totalPages: Int
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getRecentPhotos(perPage = params.loadSize, page = page, apiKey = "6f102c62f41998d151e5a1b48713cf13")
            val photos = response.photos.photo

            LoadResult.Page(
                data = photos,
                prevKey = if (page > 1) page - 1 else null,
                nextKey = if (page < totalPages) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}


