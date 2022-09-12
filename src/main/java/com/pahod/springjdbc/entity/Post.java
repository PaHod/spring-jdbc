package com.pahod.springjdbc.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Map;

@Data
@Builder
public class Post {
    //Posts (id, userId, text, timestamp);
    int id;
    int userId;
    String text;
    Timestamp postTimestamp;

    public static Post build(Map<String, Object> keys) {
        return Post.builder()
                .id((int) keys.get("id"))
                .userId((int) keys.get("user_id"))
                .text((String) keys.get("text"))
                .postTimestamp((Timestamp) keys.get("post_time"))
                .build();

    }
}
