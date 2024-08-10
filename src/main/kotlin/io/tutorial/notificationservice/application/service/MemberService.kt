package io.tutorial.notificationservice.application.service

import io.tutorial.notificationservice.application.port.`in`.GetMemberUseCase
import java.util.*
import org.springframework.stereotype.Service

@Service
class MemberService : GetMemberUseCase {

    /**
     * 회원 식별자 조회
     *
     * authorization은 보통 accessToken으로 구성되어 JWT로부터 memberId를 조회하도록 구성해야 하지만
     * 임시로 구현하는 것이므로 곧바로 UUID로 변환하도록 구성함.
     */
    override fun getMemberIdBy(authorization: String): UUID = UUID.fromString(authorization)
}