package io.tutorial.notificationservice.adapter.`in`.event

import io.tutorial.notificationservice.adapter.`in`.dto.CreateNotificationRequest
import io.tutorial.notificationservice.adapter.`in`.dto.CreateNotificationRequest.Companion.toCreateNotificationRequest
import io.tutorial.notificationservice.application.port.`in`.CreateNotificationUseCase
import io.tutorial.notificationservice.domain.event.CreateNotificationEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Controller

@Controller
class NotificationEventListener(
    private val createNotificationUseCase: CreateNotificationUseCase,
) {

    @EventListener
    suspend fun consumeCreateDomainEvent(createNotificationEvent: CreateNotificationEvent) {
        val request: CreateNotificationRequest = createNotificationEvent.toCreateNotificationRequest()
        createNotificationUseCase.create(request)
    }
}