package io.tutorial.notificationservice.application.port.`in`

import java.util.*

interface GetNotificationUseCase {
    suspend fun getCountOfUncheckedBy(memberId: UUID): Long
}
