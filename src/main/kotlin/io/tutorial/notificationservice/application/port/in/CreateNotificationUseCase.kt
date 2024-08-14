package io.tutorial.notificationservice.application.port.`in`

import io.tutorial.notificationservice.adapter.`in`.dto.CreateNotificationRequest
import io.tutorial.notificationservice.domain.Notification

interface CreateNotificationUseCase {
    suspend fun create(request: CreateNotificationRequest): Notification
}
