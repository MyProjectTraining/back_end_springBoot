package com.example.back_end_java.entity.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLogin {
    private String email;
    private String token;
}
