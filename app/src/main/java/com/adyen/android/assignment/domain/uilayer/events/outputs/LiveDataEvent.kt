package com.adyen.android.assignment.domain.uilayer.events.outputs


/**
 * @Author: Francis Nwokelo (emperorfin)
 * @Date: Monday 07th March, 2022.
 */


/**
 * This interface is a contract that must be implemented by any live data event concrete class that
 * is in UI layer.
 *
 * When this interface is implemented, it's concrete class is expected to be able to, through it's
 * overridden [getContentIfNotHandled] function, return the event content or null if the event
 * content has already been returned.
 *
 * Also, the concrete class is expected to be able to, through it's overridden [peekContent]
 * function, return the event content whether or not the event content has already been returned.
 *
 * @param T The data type of the event content of this interface's concrete class.
 */
interface LiveDataEvent<out T> {

    /**
     * When this abstract function is implemented in a live data event concrete class, it should
     * return the event content that was passed into the concrete class during construction or null
     * if the event content has already been returned.
     *
     * @return the event content or null.
     */
    fun getContentIfNotHandled(): T?

    /**
     * When this abstract function is implemented in a live data event concrete class, it should
     * return the event content that was passed into the concrete class during construction whether
     * or not the event content has already been returned.
     *
     * @return the event content.
     */
    fun peekContent(): T

}