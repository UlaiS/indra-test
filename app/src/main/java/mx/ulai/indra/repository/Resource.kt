package mx.ulai.indra.repository

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object{
        fun<T> success(data: T?): Resource<T>{
            return Resource(Status.FINALIZADO, data, null)
        }

        fun<T> error(data: T?, msg: String): Resource<T>{
            return Resource(Status.ERROR, data, msg)
        }


        fun<T> loading(data: T?): Resource<T>{
            return Resource(Status.CARGANDO, data, null)
        }
    }
}