package io.tutorial.notificationservice.adapter.`in`.http

import java.time.Duration
import java.time.LocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import org.springframework.http.codec.ServerSentEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class NotificationRouter {

    @GetMapping("/notification")
    suspend fun streamNotification(): Flow<ServerSentEvent<String>> =
        Flux.interval(Duration.ofSeconds(1))
            .map { sequence ->
                ServerSentEvent
                    .builder<String>()
                    .id(sequence.toString())
                    .event("periodic-event")
                    .data("\"SSE\" : \"${LocalDate.now()}\"")
                    .build()
            }
            .asFlow()
}