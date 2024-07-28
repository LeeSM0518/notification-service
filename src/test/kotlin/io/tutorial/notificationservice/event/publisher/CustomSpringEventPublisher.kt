package io.tutorial.notificationservice.event.publisher

import io.tutorial.notificationservice.event.CustomSpringEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class CustomSpringEventPublisher(
    val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun publishCustomEvent(message: String) {
        println("Publishing custom event. ")
        val customSpringEvent = CustomSpringEvent(this, message)
        applicationEventPublisher.publishEvent(customSpringEvent)
    }
}