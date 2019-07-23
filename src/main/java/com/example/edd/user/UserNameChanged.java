package com.example.edd.user;

import com.example.edd.DomainEvent;
import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class UserNameChanged implements DomainEvent {
    private String newNickName;
    private Instant when;

    private UserNameChanged() {
    }

    String getNewNickName() {
        return newNickName;
    }

    @Override
    public Instant occuredAt() {
        return this.when;
    }
}
