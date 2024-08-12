package io.tutorial.notificationservice.adapter.`in`.dto

import io.tutorial.notificationservice.domain.NotificationType
import io.tutorial.notificationservice.domain.event.NotificationContent
import io.tutorial.notificationservice.domain.event.NotificationNote
import java.time.Instant
import java.util.*

data class NotificationResponse(
    val id: UUID,
    val type: NotificationType,
    val content: NotificationContent,
    val note: NotificationNote,
    val receiverId: UUID,
    val notifiedDate: Instant,
    val checked: Boolean,
)