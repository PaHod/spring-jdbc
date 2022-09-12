package com.pahod.springjdbc.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class Task1Repository {

    /**
     * decreased likes count to 20 to get some results as there was no results for exact 100 likes
     */
    public static final String SQL_GET_DISTINCT_USERS_NAMES_WITH_FRIENDS_LIKES =
            "SELECT DISTINCT(users.first_name) FROM users" +
                    " WHERE 100 <= (select count(*) FROM friendships" +
                    "   WHERE (friendships.user_id1 = users.id)" +
                    "   OR (friendships.user_id2 = users.id))" +
                    " AND 20 <= (select count(*) FROM likes" +
                    "   WHERE (likes.user_id = users.id)" +
                    "   AND likes.like_timestamp BETWEEN '2025-03-01 00:00:00.000' AND '2025-03-31 23:59:59.999');";


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Task1Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getRequiredData() {
        return jdbcTemplate.queryForList(SQL_GET_DISTINCT_USERS_NAMES_WITH_FRIENDS_LIKES, String.class);
    }
}
