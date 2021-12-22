package mx.ulai.indra.ui.fragment.movie.movies.playing

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.ulai.indra.R
import mx.ulai.indra.databinding.FragmentNowPlayingMoviesBinding
import mx.ulai.indra.ui.adapters.movies.MoviesAdapter
import mx.ulai.indra.ui.common.RetryCallback
import mx.ulai.indra.ui.fragment.MainFragment
import mx.ulai.indra.util.GridConfig
import mx.ulai.indra.util.autoCleared

class MoviesNowPlayingFragment: MainFragment<FragmentNowPlayingMoviesBinding, MoviesNowPlayingViewModel>() {


    private var adapter by autoCleared<MoviesAdapter>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridSpanCount = GridConfig.calculateGridSpanCount(view.context)

        dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                viewModel.retry()
            }
        }

        View.inflate(context, R.layout.header, dataBinding.content)
        val adapter = MoviesAdapter(dataBindingComponent, appExecutors){
            findNavController().navigate(
                MoviesNowPlayingFragmentDirections
                .actionNavigationNowPlayingToMovieDetailFragment(it.id))
        }
        var linearLayoutManager = LinearLayoutManager(view.context)
        linearLayoutManager = GridLayoutManager(view.context, gridSpanCount)
        binding.nowPlayingList.layoutManager = linearLayoutManager
        binding.nowPlayingList.adapter = adapter
        this.adapter = adapter
        observe()

    }

    private fun observe() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
                movies -> adapter.submitList(movies.data?.movie_response)

        })

        viewModel.loadMoreStatus.observe(viewLifecycleOwner, Observer { loading ->
            if(loading == null){
                binding.loadingMovies = false
            } else {
                binding.loadingMovies = loading.isRunning
                val error = loading.errorMessageIfNotHandled
                if(error != null){
                    Log.d("TAG1", "Error")
                }
            }
        })

        binding.nowPlayingList.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1){
                    viewModel.loadNextPage()
                }
            }
        })

    }

    override val layoutRes: Int
        get() = R.layout.fragment_now_playing_movies

    override fun getViewModel(): Class<MoviesNowPlayingViewModel> {
        return MoviesNowPlayingViewModel::class.java
    }
}