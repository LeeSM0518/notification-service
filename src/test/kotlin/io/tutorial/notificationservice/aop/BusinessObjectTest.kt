package io.tutorial.notificationservice.aop

import io.tutorial.notificationservice.config.IntegrationTest
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
class BusinessObjectTest @Autowired constructor(
    private val businessObject: BusinessObject,
) {
    @Test
    fun `AOP가 정상 동작한다`() = runTest {
        businessObject.serve()
    }
}