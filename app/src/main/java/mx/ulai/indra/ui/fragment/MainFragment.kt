package mx.ulai.indra.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import mx.ulai.indra.application.IndraApplicationExecutors
import mx.ulai.indra.binding.BindingAdapter
import mx.ulai.indra.util.autoCleared
import javax.inject.Inject

abstract class MainFragment<DB : ViewDataBinding, VM : ViewModel> :DaggerFragment(),
    DataBindingComponent {
    lateinit var dataBinding: DB
    lateinit var  viewModel: VM

    @get:LayoutRes
    abstract val layoutRes: Int

    abstract fun getViewModel(): Class<VM>

    @Inject
    lateinit var  viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: IndraApplicationExecutors

    var binding by autoCleared<DB>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(getViewModel())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        binding = dataBinding
        return dataBinding.root
    }

    class FragmentDataBindingComponent(): DataBindingComponent {
        private val adapter = BindingAdapter
        fun getFragmentBindingAdapters(): BindingAdapter = adapter
    }


}