package com.example.edd

import com.example.edd.user.User
import spock.lang.Specification

class UserTest extends Specification {
    User user = new User(UUID.randomUUID())

    def 'deactivated user cannot change nickname'() {
        given:
        user.deactivate()
        when:
        user.nameChangedUser("Barry")
        then:
        thrown(IllegalStateException)
    }

    def 'activated user can change nickname'() {
        given:
        user.activate()
        when:
        user.nameChangedUser("Barry")
        then:
        user.getUserName() == "Barry"
    }

    def 'new user can be activated'() {
        when:
        user.activate()
        then:
        user.isActive()
    }

    def 'activated can be deactivated'() {
        given:
        user.activate()
        when:
        user.deactivate()
        then:
        user.isDeactivate()
    }

    def 'activated user cannot be activated'() {
        given:
        user.activate()
        when:
        user.activate()
        then:
        thrown(IllegalStateException)
    }

    def 'deactivated user cannot be deactivated'() {
        given:
        user.deactivate()
        when:
        user.deactivate()
        then:
        thrown(IllegalStateException)
    }
}
