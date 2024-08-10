package io.tutorial.notificationservice.application.service

import io.tutorial.notificationservice.adapter.`in`.dto.CreateNotificationRequest
import io.tutorial.notificationservice.adapter.out.persistence.NotificationRepository
import io.tutorial.notificationservice.config.IntegrationTest
import io.tutorial.notificationservice.domain.NotificationType
import java.util.*
import kotlin.test.AfterTest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
internal class NotificationCommandServiceTest @Autowired constructor(
    private val notificationCommandService: NotificationCommandService,
    private val notificationRepository: NotificationRepository,
) {
    @AfterTest
    fun tearDown() = runTest {
        notificationRepository.deleteAll()
    }

    @Test
    fun `알림을 생성할 수 있다`() = runTest {
        // given
        val expected = CreateNotificationRequest(
            content = "content",
            note = "note",
            receiverId = UUID.randomUUID(),
            type = NotificationType.REVIEW
        )

        // when
        notificationCommandService.create(expected)

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