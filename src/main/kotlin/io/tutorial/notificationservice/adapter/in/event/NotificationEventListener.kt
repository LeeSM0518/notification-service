package io.tutorial.notificationservice.adapter.`in`.event

import io.tutorial.notificationservice.adapter.`in`.dto.CreateNotificationRequest
import io.tutorial.notificationservice.adapter.`in`.dto.CreateNotificationRequest.Companion.toCreateNotificationRequest
import io.tutorial.notificationservice.application.port.`in`.CreateNotificationUseCase
import io.tutorial.notificationservice.domain.event.CreateNotificationEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class NotificationEventListener(
    private val createNotificationUseCase: CreateNotificationUseCase,
) {

    @EventListener
    fun consumeCreateDomainEvent(event: CreateNotificationEvent): Job =
        CoroutineScope(Dispatchers.IO).launch {
            val request: CreateNotificationRequest = event.toCreateNotificationRequest()
            createNotificationUseCase.create(request)
        }

}