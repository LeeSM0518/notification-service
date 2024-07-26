package io.tutorial.notificationservice.event.publisher

import io.tutorial.notificationservice.config.IntegrationTest
import io.tutorial.notificationservice.event.GenericSpringEvent
import kotlin.test.Test
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.event.ApplicationEvents
import org.springframework.test.context.event.RecordApplicationEvents

@IntegrationTest
@RecordApplicationEvents
class GenericSpringEventPublisherTest @Autowired constructor(
    val eventPublisher: GenericSpringEventPublisher,
) {
    @Autowired
    lateinit var events: ApplicationEvents

    @BeforeEach
    fun setup() {
        events.clear()
    }

    @Test
    fun `제네릭 이벤트를 발행할 수 있다`(): Unit = runBlocking {
        // given
        val expected = "message"

        // when
        eventPublisher.publishEvent(expected)
        delay(100)

        // then
        val eventList = events.stream(GenericSpringEvent::class.java).toList()
        assertThat(eventList.size).isEqualTo(1)
        assertThat(eventList[0].success).isTrue()
        assertThat(eventList[0].what).isEqualTo(expected)
    }
}
