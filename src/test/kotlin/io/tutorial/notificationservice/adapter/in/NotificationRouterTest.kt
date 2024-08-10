package io.tutorial.notificationservice.adapter.`in`

import io.tutorial.notificationservice.adapter.out.persistence.NotificationEntity
import io.tutorial.notificationservice.adapter.out.persistence.NotificationRepository
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
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.test.web.reactive.server.WebTestClient

@IntegrationTest
internal class NotificationRouterTest @Autowired constructor(
    private val webTestClient: WebTestClient,
    private val notificationRepository: NotificationRepository,
) {
    lateinit var expected: NotificationEntity

    @BeforeTest
    fun setup() = runTest {
        expected = NotificationEntity(
            type = NotificationType.REVIEW,
            content = "content",
            note = "note",
            receiverId = UUID.randomUUID(),
            notifiedDate = Instant.now(),
            checked = false
        )
        notificationRepository.save(expected)
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
            .accept(MediaType.TEXT_EVENT_STREAM)
            .header(HttpHeaders.AUTHORIZATION, memberId)
            .exchange()
            .expectStatus().isOk
            .returnResult(ServerSentEvent::class.java)
            .responseBody
            .blockFirst()!!

        // then
        val streamCountResponse = eventStream.data() as LinkedHashMap<*, *>
        assertThat(streamCountResponse["countOfUncheckedNotification"]).isEqualTo(1)
    }
}