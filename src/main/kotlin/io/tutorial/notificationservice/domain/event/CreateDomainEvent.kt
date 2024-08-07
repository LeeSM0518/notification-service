package io.tutorial.notificationservice.domain.event

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
sealed interface CreateDomainEvent {
    fun createContent(): String
    fun createNote(): String = Json.encodeToString(kotlinx.serialization.serializer(), this)
}