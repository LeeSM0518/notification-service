package io.tutorial.notificationservice.adapter.out.persistence

import java.util.*
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : CoroutineCrudRepository<NotificationEntity, UUID>
