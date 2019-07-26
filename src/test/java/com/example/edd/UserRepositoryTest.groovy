package com.example.edd

import com.example.edd.cqrs.Subscriber
import com.example.edd.user.Print
import com.example.edd.user.UserActivated
import com.example.edd.user.User
import com.example.edd.user.UserNameChanged
import com.example.edd.user.UserService
import org.awaitility.Awaitility
import spock.lang.Shared
import spock.lang.Specification

import java.time.Instant
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class UserRepositoryTest extends Specification {

    @Shared
    private UserService service
    @Shared
    private Subscriber subscriber
    @Shared
    UUID userId
    @Shared
    String userName
    @Shared
    String userName2

    def setupSpec() {
        userId = UUID.randomUUID()
        userName = "Tomasz"
        userName2 = "Mariusz"
        service = new UserService(new User(userId), new ArrayList<DomainEvent>())
        subscriber = new Subscriber(Executors.newFixedThreadPool(1))
    }

    def 'should be able to save and load user'() {
        given:
        when:
        this.service.configure(subscriber)
        and:
        this.subscriber.subscribe(UserNameChanged.class, new Print())
        and:
        this.subscriber.handleEvent(new UserActivated(Instant.now()))
        and:
        this.subscriber.handleEvent(new UserNameChanged(userName, Instant.now()))
        and:
        this.subscriber.handleEvent(new UserNameChanged(userName2, Instant.now()))

        then:
        Awaitility.await().atMost(5, TimeUnit.SECONDS)
                .until({this.service.byTime().getUserName() == userName2})
    }

    def 'should be able to load from a historic timestamp'() {
        given:
        when:
        this.service.configure(subscriber)
        and:
        this.subscriber.subscribe(UserNameChanged.class, new Print())
        and:
        this.subscriber.handleEvent(new UserActivated(Instant.now()))
        and:
        this.subscriber.handleEvent(new UserNameChanged(userName, Instant.now()))
        and:
        def firstSaveTime = System.currentTimeMillis()
        and:
        this.subscriber.handleEvent(new UserNameChanged(userName2, Instant.now()))

        then:
        Awaitility.await().atMost(5, TimeUnit.SECONDS)
                .until({
                    this.service.byTime(
                            Instant.now().minusMillis(System.currentTimeMillis() - firstSaveTime)
                    ).getUserName() == userName
                })
    }
}
