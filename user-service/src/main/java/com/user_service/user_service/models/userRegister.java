package com.user_service.user_service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class userRegister {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String username;

}
