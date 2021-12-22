package mx.ulai.indra.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import mx.ulai.indra.api.ApiResponse
import mx.ulai.indra.api.EmptyResponse
import mx.ulai.indra.api.ErrorResponse
import mx.ulai.indra.api.SuccessResponse
import mx.ulai.indra.application.IndraApplicationExecutors

abstract class NetworkBoundResource<ResultType, RequestType>
    @MainThread constructor(private val appExecutor: IndraApplicationExecutors){
        private val result = MediatorLiveData<Resource<ResultType>>()
    init {
        result.value = Resource.loading(null)
        val dbSource: LiveData<ResultType> = loadFromDB()
        result.addSource(dbSource){ data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)){
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource){newData ->
                    setValue(Resource.success(newData))

                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue){
            result.value = newValue
        }
    }

    protected open fun onFetchFailed(){
    }

    protected open fun onLoadDataBase(){
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: SuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>){
        val apiResponse = createCall()
        result.addSource(dbSource){ newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse){ response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when(response){
                is SuccessResponse ->{
                    appExecutor.diskIo.execute {
                        saveCallResult(processResponse(response))
                        appExecutor.mainThread().execute {
                            result.addSource(loadFromDB()){ newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }
                is EmptyResponse -> {
                    appExecutor.mainThread().execute {
                        result.addSource(loadFromDB()){ newData ->
                            setValue(Resource.success(newData))

                        }
                    }
                }
                is ErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource){ newData ->
                        setValue(Resource.error(newData, response.errorMessage))
                    }
                }
            }

        }
    }

    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDB(): LiveData<ResultType>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}