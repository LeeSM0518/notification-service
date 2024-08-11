package io.tutorial.notificationservice.application.port.out

import io.tutorial.notificationservice.adapter.`in`.dto.GetNotificationsRequest
import io.tutorial.notificationservice.domain.Notification
import java.util.*
import kotlinx.coroutines.flow.Flow

interface LoadNotificationPort {
    suspend fun loadCount(memberId: UUID): Long
    suspend fun loadCountOfUncheckedBy(memberId: UUID): Long
    suspend fun loadNotifications(request: GetNotificationsRequest): Flow<Notification>
}
