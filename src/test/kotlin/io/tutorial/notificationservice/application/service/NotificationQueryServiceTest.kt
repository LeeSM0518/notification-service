package io.tutorial.notificationservice.application.service

import io.tutorial.notificationservice.adapter.`in`.dto.GetNotificationsRequest
import io.tutorial.notificationservice.adapter.out.persistence.NotificationEntity
import io.tutorial.notificationservice.adapter.out.persistence.NotificationRepository
import io.tutorial.notificationservice.config.IntegrationTest
import io.tutorial.notificationservice.domain.NotificationType
import java.time.Instant
import java.util.*
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
internal class NotificationQueryServiceTest @Autowired constructor(
    private val notificationQueryService: NotificationQueryService,
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
    fun `회원 식별자로 읽지 않은 알림의 개수를 조회할 수 있다`() = runTest {
        // given
        val memberId = expected.receiverId

        // when
        val count = notificationQueryService.getCountOfUncheckedBy(memberId)

        // then
        assertThat(count).isEqualTo(1)
    }

    @Test
    fun `알림을 페이지로 조회할 수 있다`() = runTest {
        // given
        val request = GetNotificationsRequest(expected.receiverId, 0, 10)

        // when
        val notifications = notificationQueryService.getNotifications(request)

        // then
        val notificationResponses = notifications.content
        val response = notificationResponses[0]
        assertThat(response.id).isEqualTo(expected.id)
        assertThat(response.note).isEqualTo(expected.note)
        assertThat(response.type).isEqualTo(expected.type)
        assertThat(response.content).isEqualTo(expected.content)
        assertThat(response.checked).isEqualTo(expected.checked)
        assertThat(response.receiverId).isEqualTo(expected.receiverId)
        assertThat(response.notifiedDate).isEqualTo(expected.notifiedDate)
        assertThat(notifications.totalPages).isOne()
        assertThat(notifications.totalElements).isOne()
    }
}