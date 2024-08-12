package io.tutorial.notificationservice.adapter.`in`.dto

import java.util.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

data class GetNotificationsRequest(
    val memberId: UUID,
    private val page: Int,
    private val size: Int,
) {
    val pageable: Pageable = PageRequest.of(page, size)
}
