package io.tutorial.notificationservice.domain

import io.tutorial.notificationservice.adapter.`in`.dto.CreateNotificationRequest
import io.tutorial.notificationservice.adapter.`in`.dto.NotificationResponse
import io.tutorial.notificationservice.domain.event.NotificationContent
import io.tutorial.notificationservice.domain.event.NotificationNote
import java.time.Instant
import java.util.*

data class Notification(
    val id: UUID? = null,
    val type: NotificationType,
    val content: NotificationContent,
    val note: NotificationNote,
    val receiverId: UUID,
    val notifiedDate: Instant = Instant.now(),
    val checked: Boolean = false,
) {

    fun check(requesterId: UUID): Notification {
        if (receiverId != requesterId) {
            throw IllegalAccessException("incorrect permissions.")
        }
        return copy(checked = true)
    }

    fun toResponse() =
        NotificationResponse(
            id = id!!,
            type = type,
            content = content,
            note = note,
            receiverId = receiverId,
            notifiedDate = notifiedDate,
            checked = checked
        )

    companion object {
        fun CreateNotificationRequest.toDomain() =
            Notification(
                type = type,
                content = content,
                note = note,
                receiverId = receiverId,
            )
    }
}