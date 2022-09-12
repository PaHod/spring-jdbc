package com.pahod.springjdbc.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.Map;

@Data
@Builder
public class User {
    //Users (id, name, surname, birthdate);
    int id;
    String firstName;
    String lastName;
    Date birthdate;

    public static User build(Map<String, Object> keys) {
        return User.builder()
                .id((int) keys.get("id"))
                .firstName((String) keys.get("first_name"))
                .lastName((String) keys.get("last_name"))
                .birthdate((Date) keys.get("birthdate"))
                .build();

    }
}
