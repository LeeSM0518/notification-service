package io.tutorial.notificationservice.event.listener

import io.tutorial.notificationservice.event.CustomSpringEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

@Component
class CustomSpringEventListener : ApplicationListener<CustomSpringEvent> {

    override fun onApplicationEvent(event: CustomSpringEvent) {
        println("Received spring custom event - ${event.message}")
    }
}