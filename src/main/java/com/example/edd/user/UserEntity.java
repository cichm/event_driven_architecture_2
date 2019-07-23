package com.example.edd.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
class UserEntity {
    private UUID uuid;
    private UserState userState;
    private String userName = "";
}
