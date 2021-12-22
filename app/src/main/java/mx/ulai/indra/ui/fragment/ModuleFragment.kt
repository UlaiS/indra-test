package mx.ulai.indra.ui.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import mx.ulai.indra.application.scope.FragmentScope
import mx.ulai.indra.ui.fragment.movie.detail.MovieDetailFragment
import mx.ulai.indra.ui.fragment.movie.movies.playing.MoviesNowPlayingFragment
import mx.ulai.indra.ui.fragment.movie.movies.popular.MoviesMostPopularFragment

@Module
abstract class ModuleFragment {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMovieDetailFragment(): MovieDetailFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMoviesNowPlayingFragment(): MoviesNowPlayingFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMoviesMostPopularFragment(): MoviesMostPopularFragment
}