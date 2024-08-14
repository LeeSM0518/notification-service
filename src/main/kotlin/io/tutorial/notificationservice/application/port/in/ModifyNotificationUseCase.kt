package io.tutorial.notificationservice.application.port.`in`

import io.tutorial.notificationservice.adapter.`in`.dto.CheckNotificationsRequest

interface ModifyNotificationUseCase {
    suspend fun check(request: CheckNotificationsRequest): Int
}
