package mx.ulai.indra.application

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class IndraApplicationExecutors(
    val diskIo: Executor, val networkIO: Executor, val mainThread: Executor) {

    @Inject
    constructor(): this(
        Executors.newSingleThreadExecutor(),
        Executors.newFixedThreadPool(3),
        MainThreadExecutor()
    )

    fun diskIO(): Executor{
        return diskIo
    }

    fun networkIO(): Executor{
        return networkIO
    }

    fun mainThread(): Executor{
        return mainThread
    }

    private class MainThreadExecutor: Executor{
        val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

}