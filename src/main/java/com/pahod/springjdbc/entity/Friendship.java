package com.pahod.springjdbc.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Map;

@Data
@Builder
public class Friendship {
    //Friendships (userid1, userid2, timestamp);
    int userId1;
    int userId2;
    Timestamp friendTimestamp;

    public static Friendship build(Map<String, Object> keys) {
        return Friendship.builder()
                .userId1((int) keys.get("user_id1"))
                .userId2((int) keys.get("user_id2"))
                .friendTimestamp((Timestamp) keys.get("friend_timestamp"))
                .build();
    }

}
