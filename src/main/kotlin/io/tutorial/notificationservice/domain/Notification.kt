package io.tutorial.notificationservice.domain

import io.tutorial.notificationservice.adapter.`in`.dto.CreateNotificationRequest
import io.tutorial.notificationservice.domain.event.NotificationContent
import io.tutorial.notificationservice.domain.event.NotificationNote
import java.time.Instant
import java.util.*

data class Notification(
    val id: UUID = UUID.randomUUID(),
    val type: NotificationType,
    val content: NotificationContent,
    val note: NotificationNote,
    val receiverId: UUID,
    val notifiedDate: Instant = Instant.now(),
    val checked: Boolean = false,
) {
    companion object {
        fun CreateNotificationRequest.toDomain() =
            Notification(
                type = notificationType,
                content = content,
                note = note,
                receiverId = receiverId,
            )
    }
}