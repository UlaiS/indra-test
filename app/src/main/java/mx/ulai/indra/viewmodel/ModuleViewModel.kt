package mx.ulai.indra.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import mx.ulai.indra.application.scope.ViewModelKey
import mx.ulai.indra.ui.activity.navigation.NavigationViewModel
import mx.ulai.indra.ui.fragment.movie.detail.MovieDetailViewModel
import mx.ulai.indra.ui.fragment.movie.movies.playing.MoviesNowPlayingViewModel
import mx.ulai.indra.ui.fragment.movie.movies.popular.MoviesMostPopularViewModel

@Module
abstract class ModuleViewModel {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesMostPopularViewModel::class)
    abstract fun bindMoviesViewModel(moviesMostPopularViewModel: MoviesMostPopularViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoviesNowPlayingViewModel::class)
    abstract fun bindNowPlayingModel(moviesNowPlayingViewModel: MoviesNowPlayingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailViewModel(movieDetailViewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NavigationViewModel::class)
    abstract fun bindRepoViewModel(navigationViewModel: NavigationViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: FactoryViewModel): ViewModelProvider.Factory
}
