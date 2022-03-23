package emperorfin.android.googlesquare.domain.uilayer.events.outputs

import emperorfin.android.googlesquare.domain.exceptions.Failure
import emperorfin.android.googlesquare.domain.uilayer.events.outputs.DataResultEvent.Success


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


/**
 * A generic class that holds a value with its loading status.
 * @param R
 */
sealed class DataResultEvent<out R>{
    /**
     * @param T
     */
    data class Success<out T>(val data: T) : DataResultEvent<T>()
    data class Error(val failure: Failure) : DataResultEvent<Nothing>()
    object Loading : DataResultEvent<Nothing>()

    override fun toString(): String {
        return when (this){
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[throwable=$failure]"
            Loading -> "Loading"
        }
    }
}

/**
 * `true` if [DataResultEvent] is of type [Success] & holds non-null [Success.data].
 */
val DataResultEvent<*>.succeeded
    get() = this is Success && data != null
