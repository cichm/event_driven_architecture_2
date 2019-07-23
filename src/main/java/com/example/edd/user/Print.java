package com.example.edd.user;

import java.util.function.Consumer;

public class Print implements Consumer {
    @Override
    public void accept(Object o) {
        System.out.println("My name: " + ((UserNameChanged) o).getNewNickName());
    }
}
