package io.tutorial.notificationservice.adapter.`in`.dto

import io.tutorial.notificationservice.domain.NotificationType
import io.tutorial.notificationservice.domain.event.CreateNotificationEvent
import io.tutorial.notificationservice.domain.event.NotificationContent
import io.tutorial.notificationservice.domain.event.NotificationNote
import java.util.*

data class CreateNotificationRequest(
    val content: NotificationContent,
    val note: NotificationNote,
    val receiverId: UUID,
    val notificationType: NotificationType,
) {
    companion object {
        fun CreateNotificationEvent.toCreateNotificationRequest() =
            CreateNotificationRequest(
                content = content,
                note = createNote(),
                receiverId = receiverId,
                notificationType = notificationType
            )
    }
}
