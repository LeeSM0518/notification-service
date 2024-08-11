package io.tutorial.notificationservice.adapter.out

import io.tutorial.notificationservice.adapter.`in`.dto.GetNotificationsRequest
import io.tutorial.notificationservice.adapter.out.persistence.NotificationRepository
import io.tutorial.notificationservice.application.port.out.LoadNotificationPort
import io.tutorial.notificationservice.domain.Notification
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Component

@Component
class NotificationQueryAdapter(
    private val notificationRepository: NotificationRepository,
) : LoadNotificationPort {

    override suspend fun loadCount(memberId: UUID): Long =
        notificationRepository.countByReceiverId(memberId)

    override suspend fun loadCountOfUncheckedBy(memberId: UUID): Long =
        notificationRepository.countByReceiverIdAndCheckedIsFalse(memberId)

    override suspend fun loadNotifications(request: GetNotificationsRequest): Flow<Notification> =
        notificationRepository
            .findAllByReceiverIdOrderByNotifiedDateDesc(request.memberId, request.pageable)
            .map { it.toDomain() }
}