package io.tutorial.notificationservice.adapter.`in`.http

import io.tutorial.notificationservice.adapter.`in`.dto.CheckNotificationResponse
import io.tutorial.notificationservice.adapter.`in`.dto.CheckNotificationsRequest
import io.tutorial.notificationservice.adapter.`in`.dto.GetNotificationsRequest
import io.tutorial.notificationservice.adapter.`in`.dto.NotificationResponse
import io.tutorial.notificationservice.adapter.`in`.dto.StreamCountResponse
import io.tutorial.notificationservice.application.port.`in`.GetMemberUseCase
import io.tutorial.notificationservice.application.port.`in`.GetNotificationUseCase
import io.tutorial.notificationservice.application.port.`in`.ModifyNotificationUseCase
import io.tutorial.notificationservice.common.PageResponse
import io.tutorial.notificationservice.common.PageResponse.Companion.toResponse
import java.time.Duration
import java.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asFlow
import org.springframework.data.domain.Page
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux.interval

@RestController
class NotificationRouter(
    private val getMemberUseCase: GetMemberUseCase,
    private val getNotificationUseCase: GetNotificationUseCase,
    private val modifyNotificationUseCase: ModifyNotificationUseCase,
) {

    @GetMapping("/notifications/count")
    suspend fun streamCountOfUncheckedNotifications(
        @RequestHeader(AUTHORIZATION)
        authorization: String,
    ): Flow<ServerSentEvent<StreamCountResponse>> =
        getMemberUseCase
            .getMemberIdBy(authorization)
            .let { memberId ->
                interval(streamIntervalDuration)
                    .asFlow()
                    .map { getCountOfUncheckedNotifications(memberId) }
            }

    @GetMapping("/notifications")
    suspend fun getNotifications(
        @RequestHeader(AUTHORIZATION)
        authorization: String,
        @RequestParam(name = "page", defaultValue = "0")
        page: Int,
        @RequestParam(name = "size", defaultValue = "10")
        size: Int,
    ): PageResponse<NotificationResponse> {
        val memberId: UUID = getMemberUseCase.getMemberIdBy(authorization)
        val request = GetNotificationsRequest(memberId, page, size)
        val notifications: Page<NotificationResponse> = getNotificationUseCase.getNotifications(request)
        return notifications.toResponse()
    }

    @PatchMapping("/notifications/{notificationId}/checked")
    suspend fun checkNotifications(
        @RequestHeader(AUTHORIZATION)
        authorization: String,
        @PathVariable("notificationId", required = false)
        notificationId: UUID?,
    ): CheckNotificationResponse {
        val memberId: UUID = getMemberUseCase.getMemberIdBy(authorization)
        val request = CheckNotificationsRequest(memberId, notificationId)
        val count: Int = modifyNotificationUseCase.check(request)
        return CheckNotificationResponse(count)
    }

    private suspend fun getCountOfUncheckedNotifications(memberId: UUID): ServerSentEvent<StreamCountResponse> {
        val count: Long = getNotificationUseCase.getCountOfUncheckedBy(memberId)
        val response = StreamCountResponse(count)
        return ServerSentEvent
            .builder<StreamCountResponse>()
            .id(memberId.toString())
            .event("streamCountOfUncheckedNotifications")
            .retry(retryDuration)
            .data(response)
            .build()
    }

    companion object {
        private val streamIntervalDuration = Duration.ofSeconds(1)
        private val retryDuration = Duration.ofSeconds(10)
    }
}