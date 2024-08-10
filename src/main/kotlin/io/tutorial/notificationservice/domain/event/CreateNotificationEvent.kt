package io.tutorial.notificationservice.domain.event

import io.tutorial.notificationservice.domain.NotificationType
import java.util.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

typealias NotificationContent = String
typealias NotificationNote = String

@Serializable
sealed interface CreateNotificationEvent {
    val receiverId: UUID
    val notificationType: NotificationType
    val content: NotificationContent

    fun createNote(): NotificationNote = Json.encodeToString(kotlinx.serialization.serializer(), this)
}