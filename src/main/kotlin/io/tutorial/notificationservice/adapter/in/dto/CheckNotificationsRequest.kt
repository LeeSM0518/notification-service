package io.tutorial.notificationservice.adapter.`in`.dto

import java.util.*

data class CheckNotificationsRequest(
    val memberId: UUID,
    val notificationId: UUID?,
)
