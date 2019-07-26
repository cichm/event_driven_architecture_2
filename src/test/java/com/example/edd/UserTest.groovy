package com.example.edd

import com.example.edd.user.User
import spock.lang.Specification

class UserTest extends Specification {
    User user = new User(UUID.randomUUID())

    def 'The deactivated user cannot change nickname.'() {
        given:
        user.deactivate()
        when:
        user.nameChangedUser("Barry")
        then:
        thrown(IllegalStateException)
    }

    def 'The activated user can change nickname.'() {
        given:
        user.activate()
        when:
        user.nameChangedUser("Barry")
        then:
        user.getUserName() == "Barry"
    }

    def 'The new user can be activated.'() {
        when:
        user.activate()
        then:
        user.isActive()
    }

    def 'The activated can be deactivated.'() {
        given:
        user.activate()
        when:
        user.deactivate()
        then:
        user.isDeactivate()
    }

    def 'The activated user cannot be activated.'() {
        given:
        user.activate()
        when:
        user.activate()
        then:
        thrown(IllegalStateException)
    }

    def 'The deactivated user cannot be deactivated.'() {
        given:
        user.deactivate()
        when:
        user.deactivate()
        then:
        thrown(IllegalStateException)
    }
}
