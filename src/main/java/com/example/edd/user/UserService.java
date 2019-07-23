package com.example.edd.user;

import com.example.edd.DomainEvent;
import com.example.edd.cqrs.Subscriber;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

    private UserService activateUser(UserActivated activated) {
        this.user.activate();
        this.state.add(activated);
        return this;
    }

    private UserService deactivateUser(UserDeactivated deactivated) {
        this.user.deactivate();
        this.state.add(deactivated);
        return this;
    }

    private UserService nameChangedUser(UserNameChanged nameChanged) {
        this.user.nameChangedUser(nameChanged.getNewNickName());
        this.state.add(nameChanged);
        return this;
    }

    public String getUserNickName() {
        return this.user.getUserName();
    }

    public UserEntity byTime() {
        User userFuture = new User(this.user.getUserUuid());
        this.state.forEach(u -> {
            if (u instanceof UserActivated) {
                userFuture.activate();
            }
            else if (u instanceof UserDeactivated) {
                userFuture.deactivate();
            }
            else if (u instanceof UserNameChanged) {
                userFuture.nameChangedUser(((UserNameChanged) u).getNewNickName());
            }
        });
        return userFuture.getUserEntity();
    }

    public UserEntity byTime(Instant instant) {
        List<DomainEvent> domainEvents = new ArrayList<>();
        for (DomainEvent d: this.state) {
            if (d.occuredAt().isBefore(instant)) {
                domainEvents.add(d);
            }
        }

        User userFuture = new User(this.user.getUserUuid());
        domainEvents.forEach(u -> {
            if (u instanceof UserActivated) {
                userFuture.activate();
            }
            else if (u instanceof UserDeactivated) {
                userFuture.deactivate();
            }
            else if (u instanceof UserNameChanged) {
                userFuture.nameChangedUser(((UserNameChanged) u).getNewNickName());
            }
        });
        return userFuture.getUserEntity();
    }
}
