package io.tutorial.notificationservice.application.service

import io.tutorial.notificationservice.application.port.`in`.GetNotificationUseCase
import io.tutorial.notificationservice.application.port.out.LoadNotificationPort
import java.util.*
import org.springframework.stereotype.Service

@Service
class NotificationQueryService(
    private val loadNotificationPort: LoadNotificationPort,
) : GetNotificationUseCase {

    override suspend fun getCountOfUncheckedBy(memberId: UUID): Long =
        loadNotificationPort.loadCountOfUncheckedBy(memberId)
}