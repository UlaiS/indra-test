package mx.ulai.indra.ui.adapters.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import mx.ulai.indra.R
import mx.ulai.indra.application.IndraApplicationExecutors
import mx.ulai.indra.databinding.MoviesItemBinding
import mx.ulai.indra.model.MovieResponse
import mx.ulai.indra.ui.adapters.MainAdapter

class MoviesAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: IndraApplicationExecutors,
    private val repoClickCallback: ((MovieResponse)->Unit)?
) : MainAdapter<MovieResponse, MoviesItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<MovieResponse>(){
        override fun areItemsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return oldItem.id == newItem.id && oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MovieResponse, newItem: MovieResponse): Boolean {
            return oldItem.overview == newItem.overview && oldItem.popularity == newItem.popularity
        }

    }
) {
    override fun createBinding(parent: ViewGroup): MoviesItemBinding {
        val binding = DataBindingUtil.inflate<MoviesItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.movies_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener{
            binding.movie?.let {
                repoClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: MoviesItemBinding, item: MovieResponse) {
        binding.movie = item
    }
}