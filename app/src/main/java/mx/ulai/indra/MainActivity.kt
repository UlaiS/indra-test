package mx.ulai.indra

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class MainActivity<DB : ViewDataBinding, VM : ViewModel> : DaggerAppCompatActivity() {

    lateinit var dataBinding: DB
    lateinit var  viewModel: VM

    @get:LayoutRes
    abstract val layoutRes: Int

    abstract fun getViewModel(): Class<VM>

    @Inject
    lateinit var  viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, layoutRes)
        viewModel = ViewModelProvider(this, viewModelFactory).get(getViewModel())
    }
}