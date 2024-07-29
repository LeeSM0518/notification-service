package io.tutorial.notificationservice.event

import org.springframework.context.ApplicationEvent

class CustomSpringEvent(
    source: Any,
    val message: String,
) : ApplicationEvent(source)