package com.slowerror.tobuy.presentation.base

open class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContent(): T? =
        if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }

    fun peekContent(): T = content
}
