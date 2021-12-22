package mx.ulai.indra.ui.adapters

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import mx.ulai.indra.application.IndraApplicationExecutors

abstract class MainAdapter<T,V: ViewDataBinding>(
    appExecutors: IndraApplicationExecutors,
    diffCallback: DiffUtil.ItemCallback<T>): ListAdapter<T, MainViewHolder<V>>(
    AsyncDifferConfig.Builder(diffCallback)
        .setBackgroundThreadExecutor(appExecutors.diskIO())
        .build()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder<V> {
        val binding = createBinding(parent)
        return MainViewHolder(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup): V

    override fun onBindViewHolder(holder: MainViewHolder<V>, position: Int) {
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }

    protected abstract fun bind(binding: V, item: T)
}