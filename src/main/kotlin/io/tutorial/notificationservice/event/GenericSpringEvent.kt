package io.tutorial.notificationservice.event

data class GenericSpringEvent<T>(
    val what: T,
    val success: Boolean
)