package com.example.edd.user;

import java.util.function.Consumer;

public class Print implements Consumer<UserNameChanged> {
    @Override
    public void accept(UserNameChanged userNameChanged) {
        System.out.println("My name: " + userNameChanged.getNewNickName());
    }
}
