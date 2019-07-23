package com.example.edd.user;

import com.example.edd.DomainEvent;
import lombok.AllArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
public class UserDeactivated implements DomainEvent {
    private Instant when;

    @Override
    public Instant occuredAt() {
        return this.when;
    }
}
