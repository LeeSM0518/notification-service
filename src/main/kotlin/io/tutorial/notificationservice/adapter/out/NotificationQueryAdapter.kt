package io.tutorial.notificationservice.adapter.out

import io.tutorial.notificationservice.adapter.out.persistence.NotificationRepository
import io.tutorial.notificationservice.application.port.out.LoadNotificationPort
import java.util.*
import org.springframework.stereotype.Component

@Component
class NotificationQueryAdapter(
    private val notificationRepository: NotificationRepository,
) : LoadNotificationPort {

    override suspend fun loadCountOfUncheckedBy(memberId: UUID): Long =
        notificationRepository.countByReceiverIdAndCheckedIsTrue(memberId)

}