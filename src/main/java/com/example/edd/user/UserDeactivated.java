package com.example.edd.user;

import com.example.edd.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class UserDeactivated implements DomainEvent {
    private Instant when;

    private UserDeactivated() {
    }

    @Override
    public Instant occuredAt() {
        return this.when;
    }
}
