package mx.ulai.indra.ui.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class MainViewHolder<out T: ViewDataBinding> constructor(val binding: T): RecyclerView.ViewHolder(binding.root) {
}