package com.example.edd.user;

import com.example.edd.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class UserActivated implements DomainEvent {
    private Instant when;

    private UserActivated() {
    }

    @Override
    public Instant occuredAt() {
        return this.when;
    }
}
