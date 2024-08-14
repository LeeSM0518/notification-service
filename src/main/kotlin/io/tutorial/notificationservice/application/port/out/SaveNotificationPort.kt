package io.tutorial.notificationservice.application.port.out

import io.tutorial.notificationservice.domain.Notification

interface SaveNotificationPort {
    suspend fun save(notification: Notification): Notification
}
