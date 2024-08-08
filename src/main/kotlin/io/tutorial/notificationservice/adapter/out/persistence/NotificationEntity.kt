package io.tutorial.notificationservice.adapter.out.persistence

import io.tutorial.notificationservice.domain.Notification
import io.tutorial.notificationservice.domain.NotificationType
import io.tutorial.notificationservice.domain.event.NotificationContent
import io.tutorial.notificationservice.domain.event.NotificationNote
import java.time.Instant
import java.util.*
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(value = "notification")
data class NotificationEntity(
    @Id
    @Column("notification_id")
    @get:JvmName("notificationId")
    val id: UUID? = null,
    val type: NotificationType,
    val content: NotificationContent,
    val note: NotificationNote,
    val receiverId: UUID,
    val notifiedDate: Instant,
    val checked: Boolean,
) : Persistable<UUID> {
    override fun getId(): UUID? = id
    override fun isNew(): Boolean = id == null

    companion object {
        fun Notification.toEntity() =
            NotificationEntity(
                id = this.id,
                type = this.type,
                content = this.content,
                note = this.note,
                receiverId = this.receiverId,
                notifiedDate = this.notifiedDate,
                checked = this.checked,
            )
    }
}
