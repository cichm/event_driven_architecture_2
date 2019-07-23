package com.example.edd.cqrs;

import com.example.edd.DomainEvent;
import com.example.edd.user.UserService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class Subscriber {
    private final UserService userService;

    private Map<Class, List<Consumer>> eventHandlers = new ConcurrentHashMap<>();

    public <T extends DomainEvent> Runnable subscribe(Class<T> eventType, Consumer<T> work) {
        eventHandlers.computeIfAbsent(eventType, aClass -> new ArrayList<>()).add(work);
        return () -> eventHandlers.computeIfAbsent(eventType, aClass -> new ArrayList<>()).remove(work);
    }

    public void handleEvent(DomainEvent event) {
        List<Consumer> runnables = eventHandlers.get(event.getClass());
        if (runnables == null) {
            System.out.println("MISSED SUBSCRIPTION");
        } else {
            runnables.forEach(consumer -> consumer.accept(event));
        }
    }
}
