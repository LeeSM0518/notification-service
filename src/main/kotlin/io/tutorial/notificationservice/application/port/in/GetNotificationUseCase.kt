package io.tutorial.notificationservice.application.port.`in`

import io.tutorial.notificationservice.adapter.`in`.dto.GetNotificationsRequest
import io.tutorial.notificationservice.adapter.`in`.dto.NotificationResponse
import java.util.*
import org.springframework.data.domain.Page

interface GetNotificationUseCase {
    suspend fun getCountOfUncheckedBy(memberId: UUID): Long
    suspend fun getNotifications(request: GetNotificationsRequest): Page<NotificationResponse>
}
