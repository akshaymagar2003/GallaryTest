import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.gallarytest.PhotoPagingSource
import com.example.gallarytest.network.ApiClient
import com.example.gallarytest.network.ApiService
import com.example.gallarytest.network.Photo
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {
    private val apiService: ApiService = ApiClient.create()

    fun getPhotos(page: Int = 1): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PhotoPagingSource(apiService, page) }
        ).flow.cachedIn(viewModelScope)
    }
    fun retry() {

    }
}
