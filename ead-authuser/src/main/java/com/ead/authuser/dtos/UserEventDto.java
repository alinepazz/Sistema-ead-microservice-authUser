package com.ead.authuser.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class UserEventDto {

    private UUID userId;
    private String userName;
    private String email;
    private String fullName;
    private String userType;
    private String userStatus;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;
    private String actionType;
}
