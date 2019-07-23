package com.example.edd;

import java.time.Instant;

public interface DomainEvent {
    Instant occuredAt();
}
