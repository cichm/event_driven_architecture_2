package com.example.edd.user;

import com.example.edd.cqrs.Subscriber;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserService {

    private User user;

    public void configure(Subscriber subscriber) {
        subscriber.subscribe(UserActivated.class, this::activateUser);
        subscriber.subscribe(UserDeactivated.class, this::deactivateUser);
        subscriber.subscribe(UserNameChanged.class, this::nameChangedUser);
    }

    private void activateUser(UserActivated activated) {
        this.user.activate();
    }

    private void deactivateUser(UserDeactivated deactivated) {
        this.user.deactivate();
    }

    private void nameChangedUser(UserNameChanged nameChanged) {
        this.user.nameChangedUser(nameChanged.getNewNickName());
    }

    public String getUserNickName() {
        return this.user.getUserName();
    }
}
