package io.tutorial.notificationservice.event.listener

import io.tutorial.notificationservice.event.CustomSpringEvent
import io.tutorial.notificationservice.event.CustomSpringEvent2
import io.tutorial.notificationservice.event.GenericSpringEvent
import org.springframework.context.event.ContextStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class AnnotationDrivenEventListener {
    @EventListener
    fun handleContextStart(event: ContextStartedEvent) {
        println("Handling context started event.")
    }

    @EventListener
    fun handleCustomEvent(event: CustomSpringEvent): CustomSpringEvent2 {
        println("Handling custom event.")
        return CustomSpringEvent2("message")
    }

    @EventListener
    fun handleCustomEvent2(event: CustomSpringEvent2) {
        println("Handling custom event2.")
    }

    @EventListener
    fun handleSuccessful(event: GenericSpringEvent<String>) {
        println("Handling generic event (conditional).")
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT, fallbackExecution = true)
    fun handleCustom(event: CustomSpringEvent) {
        println("Handling event inside a transaction BEFORE COMMIT")
    }
}