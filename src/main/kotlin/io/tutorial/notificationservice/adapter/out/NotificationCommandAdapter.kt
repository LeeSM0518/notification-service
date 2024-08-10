package io.tutorial.notificationservice.adapter.out

import io.tutorial.notificationservice.adapter.out.persistence.NotificationEntity
import io.tutorial.notificationservice.adapter.out.persistence.NotificationEntity.Companion.toEntity
import io.tutorial.notificationservice.adapter.out.persistence.NotificationRepository
import io.tutorial.notificationservice.application.port.out.SaveNotificationPort
import io.tutorial.notificationservice.domain.Notification
import org.springframework.stereotype.Component

@Component
class NotificationCommandAdapter(
    private val notificationRepository: NotificationRepository,
) : SaveNotificationPort {

    override suspend fun save(notification: Notification) {
        val entity: NotificationEntity = notification.toEntity()
        notificationRepository.save(entity)
    }
}