package io.tutorial.notificationservice.application.port.out

import java.util.*

interface LoadNotificationPort {
    suspend fun loadCountOfUncheckedBy(memberId: UUID): Long
}
