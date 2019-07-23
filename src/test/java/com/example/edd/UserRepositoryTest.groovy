package com.example.edd

import com.example.edd.cqrs.Subscriber
import com.example.edd.user.Print
import com.example.edd.user.UserActivated
import com.example.edd.user.User
import com.example.edd.user.UserNameChanged
import com.example.edd.user.UserService
import spock.lang.Specification

import java.time.Instant

class UserRepositoryTest extends Specification {

    private UserService service;
    private Subscriber subscriber;

    def 'should be able to save and load user'() {
        given:
        UUID userId = UUID.randomUUID();
        and:
        String userName = "Tomasz";
        and:
        this.service = new UserService(new User(userId));
        and:
        this.subscriber = new Subscriber(new UserService());
        when:
        this.service.configure(subscriber);
        and:
        this.subscriber.subscribe(UserNameChanged.class, new Print())
        and:
        this.subscriber.handleEvent(new UserActivated());
        and:
        this.subscriber.handleEvent(new UserNameChanged(userName, Instant.now()));
        then:
        this.service.getUserNickName() == userName;
    }
}
