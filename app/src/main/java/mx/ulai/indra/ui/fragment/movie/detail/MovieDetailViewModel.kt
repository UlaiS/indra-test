package mx.ulai.indra.ui.fragment.movie.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import mx.ulai.indra.model.MovieDetailsResponse
import mx.ulai.indra.model.MovieVideosResponse
import mx.ulai.indra.repository.MovieDetailsRepository
import mx.ulai.indra.repository.MovieVideoRespository
import mx.ulai.indra.repository.Resource
import mx.ulai.indra.util.AbsentLiveData
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    movieDetailsRepository: MovieDetailsRepository,
    movieVideoRespository: MovieVideoRespository
): ViewModel() {
    private val _idMovie= MutableLiveData<Int>()
    val idMovie: LiveData<Int>
        get() = _idMovie

    val detailMovie: LiveData<Resource<MovieDetailsResponse>> = Transformations
        .switchMap(_idMovie){idMovie->
            if(idMovie == null){
                AbsentLiveData.create();
            }else{
                movieDetailsRepository.loadMovieDetails(idMovie)
            }
        }

    val videoMovie: LiveData<Resource<MovieVideosResponse>> = Transformations
        .switchMap(_idMovie){idMovie->
            if(idMovie == null){
                AbsentLiveData.create()
            }else{
                movieVideoRespository.loadMovieVideo(idMovie)
            }
        }

    fun setId(idMovie: Int?){
        if(_idMovie.value != idMovie){
            _idMovie.value = idMovie!!
        }
    }

    fun retry(){
        _idMovie.value?.let{
            _idMovie.value = it
        }
    }
}