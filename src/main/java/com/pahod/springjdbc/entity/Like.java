package com.pahod.springjdbc.entity;


import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Map;

@Data
@Builder
public class Like {
    //    Likes (postid, userid, timestamp).
    int postId;
    int userId;
    Timestamp likeTimestamp;

    public static Like build(Map<String, Object> keys) {
        return Like.builder()
                .postId((int) keys.get("post_id"))
                .userId((int) keys.get("user_id"))
                .likeTimestamp((Timestamp) keys.get("like_timestamp"))
                .build();
    }
}
