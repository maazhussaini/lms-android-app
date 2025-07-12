package com.example.orb_ed.domain.model

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
    
    /**
     * Returns `true` if this instance represents a successful outcome.
     */
    val isSuccess: Boolean
        get() = this is Success<T>
    
    /**
     * Returns `true` if this instance represents a failed outcome.
     */
    val isError: Boolean
        get() = this is Error
    
    /**
     * Returns `true` if this instance represents a loading state.
     */
    val isLoading: Boolean
        get() = this is Loading
    
    /**
     * Returns the encapsulated result if this instance represents [Success] or `null` if it is not.
     */
    fun getOrNull(): T? = (this as? Success<T>)?.data
    
    /**
     * Returns the encapsulated exception if this instance represents [Error] or `null` if it is not.
     */
    fun exceptionOrNull(): Exception? = (this as? Error)?.exception
    
    /**
     * Maps the result using the given [transform] function if the result is [Success].
     */
    inline fun <R> map(transform: (value: T) -> R): ApiResult<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error -> this
            Loading -> Loading
        }
    }
    
    /**
     * Executes the given [action] if the result is [Success].
     */
    inline fun onSuccess(action: (value: T) -> Unit): ApiResult<T> {
        if (this is Success) action(data)
        return this
    }
    
    /**
     * Executes the given [action] if the result is [Error].
     */
    inline fun onError(action: (exception: Exception) -> Unit): ApiResult<T> {
        if (this is Error) action(exception)
        return this
    }
    
    /**
     * Executes the given [action] if the result is [Loading].
     */
    inline fun onLoading(action: () -> Unit): ApiResult<T> {
        if (this is Loading) action()
        return this
    }
}
