package mx.ulai.indra.api

import retrofit2.Response

sealed class ApiResponse<T>{
    companion object{
        fun<T> create(error: Throwable): ErrorResponse<T>{
            return ErrorResponse(error.message ?: "Error desconocido")
        }

        fun<T> create(response: Response<T>): ApiResponse<T>{
            return if (response.isSuccessful){
                val body: T? = response.body()
                if (body == null || response.code() == 204){
                    EmptyResponse()
                } else {
                    SuccessResponse(body = body)
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if(msg.isNullOrEmpty()){
                    response.message()
                }else{
                    msg
                }
                ErrorResponse(errorMsg ?: "Error desconocido")
            }
        }
    }
}

class EmptyResponse<T>: ApiResponse<T>()

data class SuccessResponse<T>(val body: T): ApiResponse<T>()

data class ErrorResponse<T>(val errorMessage: String): ApiResponse<T>()

