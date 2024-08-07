package io.tutorial.notificationservice.adapter.`in`.event

//@Controller
//class NotificationEventListener(
//    private val createNotificationsUseCase: CreateNotificationsUseCase,
//) {
//
//    @EventListener
//    suspend fun consumeCreateDomainEvent(createDomainEvent: CreateDomainEvent) {
//        val notificationDto: NotificationDto = createDomainEvent.toDto()
//        createNotificationsUseCase.create(notificationDto)
//    }
//}