package io.tutorial.notificationservice.domain.event

import io.tutorial.notificationservice.domain.event.serializer.InstantSerializer
import io.tutorial.notificationservice.domain.event.serializer.UUIDSerializer
import java.time.Instant
import java.util.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateReviewEvent(
    @Serializable(with = UUIDSerializer::class)
    val reviewId: UUID,
    val reviewerName: String,
    @Serializable(with = UUIDSerializer::class)
    val postId: UUID,
    val postTitle: String,
    @Serializable(with = InstantSerializer::class)
    val writtenDate: Instant,
) : CreateDomainEvent {
    override fun createContent(): String = "[$reviewerName] 님이 [$postTitle]에 리뷰를 남겼습니다."
}