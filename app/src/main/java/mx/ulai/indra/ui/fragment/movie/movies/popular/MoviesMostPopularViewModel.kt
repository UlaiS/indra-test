package mx.ulai.indra.ui.fragment.movie.movies.popular

import androidx.lifecycle.*
import mx.ulai.indra.model.MovieResponse
import mx.ulai.indra.model.MoviesResponse
import mx.ulai.indra.repository.MovieMostPopularRepository
import mx.ulai.indra.repository.Resource
import mx.ulai.indra.repository.Status
import javax.inject.Inject

class MoviesMostPopularViewModel @Inject constructor(movieMostPopularRepository: MovieMostPopularRepository) : ViewModel() {

    private val page = MutableLiveData<Int>(null)

    private val nextPageHandler = NextPageHandler(movieMostPopularRepository)

    val loadMoreStatus: LiveData<LoadMoreState>
        get() = nextPageHandler.loadMoreState

    var movies: MutableLiveData<Resource<MoviesResponse>> = Transformations
        .switchMap(page) {
            movieMostPopularRepository.loadMovies(it)
        } as MutableLiveData<Resource<MoviesResponse>>


    fun retry() {
        movies.value?.data?.page?.let {
            movies.value?.data?.page = it
        }
    }

    fun loadNextPage(){
        movies.value?.data?.total_pages?.let { totalPages ->
            if (movies.value?.data?.page?.plus(1)!!  > totalPages)
                return
            nextPageHandler.queryNextPage(movies.value?.data?.page?.plus(1)!!)
        }
    }

    class LoadMoreState(val isRunning: Boolean, val errorMessage: String?){
        private var handleError = false

        val errorMessageIfNotHandled: String?
            get() {
                if(handleError){
                    return null
                }
                handleError = true
                return errorMessage
            }
    }

    inner class NextPageHandler(private val repository: MovieMostPopularRepository): Observer<Resource<MoviesResponse>> {

        private var nextPageLiveData: LiveData<Resource<MoviesResponse>>? = null
        val loadMoreState = MutableLiveData<LoadMoreState>()

        init {
            reset()
        }

        fun queryNextPage(currentPage: Int){
            unregister()
            nextPageLiveData = repository.nextPage(currentPage)
            loadMoreState.value = LoadMoreState(
                isRunning = true,
                errorMessage = null
            )
            nextPageLiveData?.observeForever(this)
        }

        override fun onChanged(result: Resource<MoviesResponse>?) {
            if(result == null){
                reset()
            }else{
                when(result.status){
                    Status.FINALIZADO ->{
                        unregister()
                        loadMoreState.value = LoadMoreState(
                            isRunning = false,
                            errorMessage = null
                        )
                        val list: MutableList<MovieResponse> = mutableListOf()
                        movies.value?.data?.movie_response?.let { list.addAll(it) }
                        result.data?.movie_response?.let { list.addAll(it) }

                        result.data?.movie_response = list
                        movies.value = result
                    }
                    Status.ERROR -> {
                        unregister()
                        loadMoreState.value = LoadMoreState(
                            isRunning = false,
                            errorMessage = result.message
                        )

                    }
                    Status.CARGANDO ->{

                    }
                }
            }
        }

        private fun unregister(){
            nextPageLiveData?.removeObserver(this)
            nextPageLiveData = null
        }

        fun reset(){
            unregister()
            loadMoreState.value = LoadMoreState(
                isRunning = false,
                errorMessage = null
            )
        }
    }
}