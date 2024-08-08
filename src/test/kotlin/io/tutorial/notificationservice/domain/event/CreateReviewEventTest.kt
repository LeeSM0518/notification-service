package io.tutorial.notificationservice.domain.event

import java.time.Instant
import java.util.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CreateReviewEventTest {

    @Test
    fun `이벤트로부터 content와 note를 조회할 수 있다`() {
        // given
        val event = CreateReviewEvent(
            reviewId = UUID.randomUUID(),
            reviewerName = "reviewerName",
            postId = UUID.randomUUID(),
            postTitle = "postTitle",
            writtenDate = Instant.now(),
            receiverId = UUID.randomUUID(),
        )

        // when
        val content = event.createContent()
        val note = event.createNote()

        println("content : $content")
        println("note : $note")

        // then
        assertThat(content).isEqualTo("[${event.reviewerName}] 님이 [${event.postTitle}]에 리뷰를 남겼습니다.")
    }
}