package io.tutorial.notificationservice.application.service

import io.tutorial.notificationservice.adapter.`in`.dto.GetNotificationsRequest
import io.tutorial.notificationservice.adapter.`in`.dto.NotificationResponse
import io.tutorial.notificationservice.application.port.`in`.GetNotificationUseCase
import io.tutorial.notificationservice.application.port.out.LoadNotificationPort
import java.util.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service

@Service
class NotificationQueryService(
    private val loadNotificationPort: LoadNotificationPort,
) : GetNotificationUseCase {

    override suspend fun getCountOfUncheckedBy(memberId: UUID): Long =
        loadNotificationPort.loadCountOfUncheckedBy(memberId)

    override suspend fun getNotifications(request: GetNotificationsRequest): Page<NotificationResponse> {
        val notifications = loadNotificationPort.loadNotifications(request).map { it.toResponse() }
        val count = loadNotificationPort.loadCount(request.memberId)
        return PageImpl(notifications.toList(), request.pageable, count)
    }

}