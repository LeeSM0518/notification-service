package io.tutorial.notificationservice.adapter.out

import io.tutorial.notificationservice.config.IntegrationTest
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
internal class NotificationCommandAdapterTest @Autowired constructor(
    private val notificationCommandAdapter: NotificationCommandAdapter,
) {
    @Test
    fun `알림을 저장할 수 있다`() = runTest {

    }
}