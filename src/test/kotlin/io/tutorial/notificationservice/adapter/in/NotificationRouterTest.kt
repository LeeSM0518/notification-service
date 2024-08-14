package io.tutorial.notificationservice.adapter.`in`

import io.tutorial.notificationservice.adapter.`in`.dto.CheckNotificationResponse
import io.tutorial.notificationservice.adapter.`in`.dto.NotificationResponse
import io.tutorial.notificationservice.adapter.out.persistence.NotificationEntity
import io.tutorial.notificationservice.adapter.out.persistence.NotificationRepository
import io.tutorial.notificationservice.common.PageResponse
import io.tutorial.notificationservice.config.IntegrationTest
import io.tutorial.notificationservice.domain.NotificationType
import java.time.Instant
import java.util.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.http.codec.ServerSentEvent
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@IntegrationTest
internal class NotificationRouterTest @Autowired constructor(
    private val webTestClient: WebTestClient,
    private val notificationRepository: NotificationRepository,
) {
    lateinit var expected: NotificationEntity

    @BeforeTest
    fun setup() = runTest {
        val entity = NotificationEntity(
            type = NotificationType.REVIEW,
            content = "content",
            note = "note",
            receiverId = UUID.randomUUID(),
            notifiedDate = Instant.now(),
            checked = false
        )
        expected = notificationRepository.save(entity)
    }

    @AfterTest
    fun tearDown() = runTest {
        notificationRepository.deleteAll()
    }

    @Test
    fun `실시간으로 읽지 않은 알림의 개수를 조회할 수 있다`() = runTest {
        // given
        val memberId = expected.receiverId.toString()

        // when
        val eventStream = webTestClient
            .get()
            .uri("/notifications/count")
            .accept(TEXT_EVENT_STREAM)
            .header(AUTHORIZATION, memberId)
            .exchange()
            .expectStatus().isOk
            .returnResult(ServerSentEvent::class.java)
            .responseBody
            .blockFirst()!!

        // then
        val streamCountResponse = eventStream.data() as LinkedHashMap<*, *>
        assertThat(streamCountResponse["countOfUncheckedNotification"]).isEqualTo(1)
    }

    @Test
    fun `알림 목록을 조회할 수 있다`() = runTest {
        // given
        val memberId = expected.receiverId.toString()

        // when
        val notificationsResponse = webTestClient
            .get()
            .uri("/notifications?page=0&size=10")
            .accept(APPLICATION_JSON)
            .header(AUTHORIZATION, memberId)
            .exchange()
            .expectBody<PageResponse<NotificationResponse>>()
            .returnResult()
            .responseBody!!
            .data


        // then
        val actual = notificationsResponse[0]
        assertThat(actual.id).isEqualTo(expected.id)
        assertThat(actual.content).isEqualTo(expected.content)
        assertThat(actual.note).isEqualTo(expected.note)
        assertThat(actual.type).isEqualTo(expected.type)
        assertThat(actual.receiverId).isEqualTo(expected.receiverId)
        assertThat(actual.checked).isEqualTo(expected.checked)
        assertThat(actual.notifiedDate).isEqualTo(expected.notifiedDate)
    }

    @Test
    fun `읽지 않은 모든 알림의 읽음 상태를 변경 요청할 수 있다`() = runTest {
        // given
        val memberId = expected.receiverId.toString()

        // when
        val count = webTestClient
            .patch()
            .uri("/notifications/checked")
            .accept(APPLICATION_JSON)
            .header(AUTHORIZATION, memberId)
            .exchange()
            .expectStatus().isOk
            .expectBody<CheckNotificationResponse>()
            .returnResult()
            .responseBody!!
            .count

        // then
        assertThat(count).isOne()
    }

    @Test
    fun `읽지 않은 특정 알림의 읽음 상태를 변경 요청할 수 있다`() = runTest {
        // given
        val memberId = expected.receiverId.toString()

        // when
        val count = webTestClient
            .patch()
            .uri("/notifications/${expected.id}/checked")
            .accept(APPLICATION_JSON)
            .header(AUTHORIZATION, memberId)
            .exchange()
            .expectStatus().isOk
            .expectBody<CheckNotificationResponse>()
            .returnResult()
            .responseBody!!
            .count

        // then
        assertThat(count).isOne()
    }
}