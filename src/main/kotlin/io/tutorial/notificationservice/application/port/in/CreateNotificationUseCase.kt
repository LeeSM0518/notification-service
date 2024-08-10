package io.tutorial.notificationservice.application.port.`in`

import io.tutorial.notificationservice.adapter.`in`.dto.CreateNotificationRequest

interface CreateNotificationUseCase {
    suspend fun create(request: CreateNotificationRequest)
}
