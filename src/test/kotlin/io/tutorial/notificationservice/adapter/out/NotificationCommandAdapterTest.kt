package io.tutorial.notificationservice.adapter.out

import io.tutorial.notificationservice.adapter.out.persistence.NotificationRepository
import io.tutorial.notificationservice.config.IntegrationTest
import io.tutorial.notificationservice.domain.Notification
import io.tutorial.notificationservice.domain.NotificationType
import java.util.*
import kotlin.test.AfterTest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
internal class NotificationCommandAdapterTest @Autowired constructor(
    private val notificationCommandAdapter: NotificationCommandAdapter,
    private val notificationRepository: NotificationRepository,
) {
    @AfterTest
    fun tearDown() = runTest {
        notificationRepository.deleteAll()
    }

    @Test
    fun `알림을 저장할 수 있다`() = runTest {
        // given
        val expected = Notification(
            type = NotificationType.REVIEW,
            content = "content",
            note = "note",
            receiverId = UUID.randomUUID()
        )

        // when
        notificationCommandAdapter.save(expected)

        // then
        val notifications = notificationRepository.findAll().toList()
        val actual = notifications[0]

        assertThat(notifications.size).isOne()
        assertThat(actual.type).isEqualTo(expected.type)
        assertThat(actual.content).isEqualTo(expected.content)
        assertThat(actual.note).isEqualTo(expected.note)
        assertThat(actual.receiverId).isEqualTo(expected.receiverId)
    }
}