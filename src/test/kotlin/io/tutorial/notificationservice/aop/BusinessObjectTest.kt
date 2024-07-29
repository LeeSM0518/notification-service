package io.tutorial.notificationservice.aop

import io.tutorial.notificationservice.config.IntegrationTest
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired

@IntegrationTest
class BusinessObjectTest @Autowired constructor(
    private val businessObject: BusinessObject,
) {
    @Test
    fun `AOP가 정상 동작한다`(): Unit = runBlocking {
        businessObject.serve()
    }
}