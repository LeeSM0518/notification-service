package io.tutorial.notificationservice.application.service

import io.tutorial.notificationservice.adapter.`in`.dto.CreateNotificationRequest
import io.tutorial.notificationservice.application.port.`in`.CreateNotificationUseCase
import io.tutorial.notificationservice.application.port.out.SaveNotificationPort
import io.tutorial.notificationservice.domain.Notification
import io.tutorial.notificationservice.domain.Notification.Companion.toDomain
import org.springframework.stereotype.Service

@Service
class NotificationCommandService(
    private val saveNotificationPort: SaveNotificationPort,
) : CreateNotificationUseCase {

    override suspend fun create(request: CreateNotificationRequest): Notification {
        val notification: Notification = request.toDomain()
        return saveNotificationPort.save(notification)
    }
}