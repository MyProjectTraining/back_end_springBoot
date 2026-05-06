package com.example.back_end_java.entity;

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
