package com.example.edd.user;

import com.example.edd.DomainEvent;
import com.example.edd.cqrs.Subscriber;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    private User user;
    private List<DomainEvent> state;

    public void configure(Subscriber subscriber) {
        subscriber.subscribe(UserActivated.class, this::activateUser);
        subscriber.subscribe(UserDeactivated.class, this::deactivateUser);
        subscriber.subscribe(UserNameChanged.class, this::nameChangedUser);
    }

    private void activateUser(UserActivated activated) {
        this.user.activate();
        this.state.add(activated);
    }

    private void deactivateUser(UserDeactivated deactivated) {
        this.user.deactivate();
        this.state.add(deactivated);
    }

    private void nameChangedUser(UserNameChanged nameChanged) {
        this.user.nameChangedUser(nameChanged.getNewNickName());
        this.state.add(nameChanged);
    }

    public UserEntity byTime() {
        User userFuture = recreate(this.state, new User(this.user.getUserUuid()));
        return userFuture.getUserEntity();
    }

    public UserEntity byTime(Instant instant) {
        List<DomainEvent> domainEvents = this.state.stream()
                .filter(d -> !d.occuredAt().isAfter(instant))
                .collect(Collectors.toList());

        User userFuture = recreate(domainEvents, new User(this.user.getUserUuid()));
        return userFuture.getUserEntity();
    }

    private User recreate(List<DomainEvent> domainEvents, User user) {

        domainEvents.forEach(u -> {
            if (u.getClass().getName().contains(UserActivated.class.getName())) {
                user.activate();
            } else if (u.getClass().getName().contains(UserDeactivated.class.getName())) {
                user.deactivate();
            } else if (u.getClass().getName().contains(UserNameChanged.class.getName())) {
                user.nameChangedUser(((UserNameChanged) u).getNewNickName());
            }
        });
        return user;
    }
}
