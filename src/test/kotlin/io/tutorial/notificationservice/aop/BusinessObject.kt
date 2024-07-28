package io.tutorial.notificationservice.aop

import java.lang.Thread.sleep
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class BusinessObject {

    @LogExecutionTime
    fun serve() = runBlocking {
        sleep(1000)
    }
}