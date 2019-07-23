package com.example.edd.user;

import java.util.UUID;

public class User {
    private UserEntity userEntity;

    private User() {
    }

    public User(UUID uuid) {
        this.userEntity = new UserEntity(uuid, UserState.INITIALIZED, "");
    }

    public void activate() {
        if (this.isActive()) {
            throw new IllegalStateException();
        }
        this.userEntity.setUserState(UserState.ACTIVATED);
    }

    public void deactivate() {
        if (this.isDeactivate()) {
            throw new IllegalStateException();
        }
        this.userEntity.setUserState(UserState.DEACTIVATED);
    }

    public void nameChangedUser(String name) {
        if (this.isDeactivate()) {
            throw new IllegalStateException();
        }
        this.userEntity.setUserName(name);
    }

    public boolean isActive() {
        return this.userEntity.getUserState() == UserState.ACTIVATED;
    }

    public boolean isDeactivate() {
        return this.userEntity.getUserState() == UserState.DEACTIVATED;
    }

    public String getUserName() {
        return this.userEntity.getUserName();
    }

    public UUID getUserUuid() {
        return this.userEntity.getUuid();
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }
}
