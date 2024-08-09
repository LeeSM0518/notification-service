package io.tutorial.notificationservice.event.publisher

import io.tutorial.notificationservice.config.IntegrationTest
import io.tutorial.notificationservice.event.CustomSpringEvent
import kotlin.test.Test
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.event.ApplicationEvents
import org.springframework.test.context.event.RecordApplicationEvents

@IntegrationTest
@RecordApplicationEvents
class CustomSpringEventPublisherTest @Autowired constructor(
    val eventPublisher: CustomSpringEventPublisher,
) {

    @Autowired
    lateinit var events: ApplicationEvents

    @BeforeEach
    fun setup() {
        events.clear()
    }

    @Test
    fun `사용자 정의 이벤트를 발행할 수 있다`() = runTest {
        // given
        val expected = "message"

        // when
        eventPublisher.publishCustomEvent(expected)
        delay(100)

        // then
        val eventList = events.stream(CustomSpringEvent::class.java).toList()
        assertThat(eventList.size).isEqualTo(1)
        assertThat(eventList[0].message).isEqualTo(expected)
    }
}