package io.tutorial.notificationservice.event.publisher

import io.tutorial.notificationservice.event.GenericSpringEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class GenericSpringEventPublisher(
    val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun publishEvent(message: String) {
        println("Publishing generic event. ")
        val genericEvent = GenericSpringEvent(message, true)
        applicationEventPublisher.publishEvent(genericEvent)
    }
}