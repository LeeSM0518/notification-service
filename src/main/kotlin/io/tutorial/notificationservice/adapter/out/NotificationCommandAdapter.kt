package io.tutorial.notificationservice.adapter.out

import io.tutorial.notificationservice.adapter.`in`.dto.CheckNotificationsRequest
import io.tutorial.notificationservice.adapter.out.persistence.NotificationEntity
import io.tutorial.notificationservice.adapter.out.persistence.NotificationEntity.Companion.toEntity
import io.tutorial.notificationservice.adapter.out.persistence.NotificationRepository
import io.tutorial.notificationservice.application.port.`in`.ModifyNotificationUseCase
import io.tutorial.notificationservice.application.port.out.SaveNotificationPort
import io.tutorial.notificationservice.domain.Notification
import java.util.*
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Component

@Component
class NotificationCommandAdapter(
    private val notificationRepository: NotificationRepository,
) : SaveNotificationPort, ModifyNotificationUseCase {

    override suspend fun save(notification: Notification): Notification {
        val entity: NotificationEntity = notification.toEntity()
        val notificationEntity = notificationRepository.save(entity)
        return notificationEntity.toDomain()
    }

    override suspend fun check(request: CheckNotificationsRequest): Int =
        getNotifications(request)
            .map { it.toDomain() }
            .map { it.check(request.memberId) }
            .map { it.toEntity() }
            .let { notificationRepository.saveAll(it).count() }

    private suspend fun getNotifications(request: CheckNotificationsRequest) =
        when (request.notificationId) {
            null -> notificationRepository.findAllByReceiverIdAndCheckedIsFalse(request.memberId)
            else -> findById(request.notificationId)
        }

    private suspend fun findById(notificationId: UUID) =
        flowOf(
            notificationRepository.findById(notificationId)
                ?: throw IllegalAccessException("Notification with id $notificationId not found")
        )
}