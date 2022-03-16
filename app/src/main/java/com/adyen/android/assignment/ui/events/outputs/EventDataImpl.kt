package com.adyen.android.assignment.ui.events.outputs

import androidx.lifecycle.Observer
import com.adyen.android.assignment.domain.uilayer.events.outputs.EventData


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 14th March, 2022.
 */


/**
 * Used as a wrapper for data that is exposed via a [LiveData] that represents an event.
 *
 * @constructor Creates a wrapper for [content].
 * @param T the type of a [content] in this wrapper class that is exposed via a [LiveData].
 * @property content the data that represents an event that is exposed via [LiveData] with the help
 *     of [EventDataImpl].
 */
open class EventDataImpl<out T>(private val content: T) : EventData<T> {

    var mHasContentBeenHandled = false
        private set // Allow external read but not write

    /**
     * This function returns the [content] and prevents its use again.
     *
     * @return the [content] if [mHasContentBeenHandled] is true else null.
     */
    override fun getContentIfNotHandled(): T?{
        return if (mHasContentBeenHandled){
            null
        }else{
            mHasContentBeenHandled = true

            content
        }
    }

    /**
     * This function returns the [content], even if it's already been handled.
     *
     * @return the [content].
     */
    override fun peekContent(): T = content

    /**
     * An [Observer] for [EventDataImpl]s, simplifying the pattern of checking if the
     * [EventDataImpl]'s [content] has already been handled.
     *
     * @constructor Creates the observer for [EventDataImpl].
     * @param T the type of a [onEventUnhandledContent] in this observer.
     * @property onEventUnhandledContent is *only* called if the [EventDataImpl]'s [content] has
     *     not been handled.
     */
    class EventDataImplObserver<T>(
        private val onEventUnhandledContent: (T) -> Unit
    ) : Observer<EventDataImpl<T>> {

        override fun onChanged(event: EventDataImpl<T>?) {
            event?.getContentIfNotHandled()?.let {
                onEventUnhandledContent(it)
            }
        }

    }
}