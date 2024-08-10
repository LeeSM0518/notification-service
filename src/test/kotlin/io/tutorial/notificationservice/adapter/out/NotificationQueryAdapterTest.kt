package io.tutorial.notificationservice.adapter.out

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

@IntegrationTest
internal class NotificationQueryAdapterTest @Autowired constructor(
    private val adapter: NotificationQueryAdapter,
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
    fun `회원 식별자로 읽지 않은 알림의 개수를 조회할 수 있다`() = runTest {
        // given
        val memberId = expected.receiverId

        // when
        val count = adapter.loadCountOfUncheckedBy(memberId)

        // then
        assertThat(count).isEqualTo(1)
    }
}