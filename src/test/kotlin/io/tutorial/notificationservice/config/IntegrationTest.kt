package io.tutorial.notificationservice.config

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@Target(AnnotationTarget.CLASS)
@SpringBootTest
@ContextConfiguration(initializers = [PostgresqlInitializer::class])
annotation class IntegrationTest
