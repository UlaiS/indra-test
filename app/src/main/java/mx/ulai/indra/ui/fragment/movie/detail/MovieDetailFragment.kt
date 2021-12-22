package mx.ulai.indra.ui.fragment.movie.detail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import mx.ulai.indra.R
import mx.ulai.indra.databinding.FragmentDetailMovieBinding
import mx.ulai.indra.ui.common.RetryCallback
import mx.ulai.indra.ui.fragment.MainFragment

class MovieDetailFragment: MainFragment<FragmentDetailMovieBinding, MovieDetailViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val params = arguments?.let { MovieDetailFragmentArgs.fromBundle(it) }
        viewModel.setId(params?.idMovie!!)
        observe()
    }

    private fun observe() {

        viewModel.detailMovie.observe(viewLifecycleOwner, Observer { movie ->
            if(movie.data != null)
                    binding.movie = movie.data
        })

        viewModel.videoMovie.observe(viewLifecycleOwner, Observer { video ->
            if(video.data?.movie_video_response != null)
                if (video.data.movie_video_response.isNotEmpty())
                    binding.video = video.data.movie_video_response[0]
        })

        binding.retryCallback = object : RetryCallback {
            override fun retry() {
                viewModel.retry()
            }
        }
    }

    override val layoutRes: Int
        get() = R.layout.fragment_detail_movie

    override fun getViewModel(): Class<MovieDetailViewModel> {
        return MovieDetailViewModel::class.java
    }



}