package io.tutorial.notificationservice.adapter.`in`

import io.tutorial.notificationservice.config.IntegrationTest
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerSentEvent
import org.springframework.test.web.reactive.server.WebTestClient

@IntegrationTest
internal class NotificationRouterTest @Autowired constructor(
    private val webTestClient: WebTestClient,
) {

    @Test
    fun `알림을 받을 수 있다`(): Unit = runBlocking {
        val result = webTestClient
            .get()
            .uri("/notification")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .exchange()
            .expectStatus().isOk
            .returnResult(ServerSentEvent::class.java)

        val eventStream = result.responseBody.blockFirst()!!
        assertThat(eventStream.event()).isEqualTo("periodic-event")
    }
}