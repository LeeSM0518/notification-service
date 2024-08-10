package io.tutorial.notificationservice.application.port.`in`

import java.util.*

interface GetMemberUseCase {
    fun getMemberIdBy(authorization: String): UUID
}
