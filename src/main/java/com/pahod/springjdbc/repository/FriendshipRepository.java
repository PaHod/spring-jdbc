package com.pahod.springjdbc.repository;


import com.pahod.springjdbc.entity.Friendship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pahod.springjdbc.repository.Queries.SQL_CREATE_TABLE_FRIENDSHIPS;
import static com.pahod.springjdbc.repository.Queries.SQL_DROP_TABLE_;

@Repository
public class FriendshipRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendshipRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void dropTable() {
        jdbcTemplate.update(con -> {
            String sqlCreateTableUsers = String.format( SQL_DROP_TABLE_, "friendships");
            return con.prepareStatement(sqlCreateTableUsers);
        });
    }

    public void createTable() {
        jdbcTemplate.update(con -> con.prepareStatement(SQL_CREATE_TABLE_FRIENDSHIPS));
    }

    public Friendship save(Friendship friendship) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(Queries.SQL_INSERT_FRIENDSHIP, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, friendship.getUserId1());
            ps.setInt(2, friendship.getUserId2());
            ps.setTimestamp(3, friendship.getFriendTimestamp());
            return ps;
        }, keyHolder);

        return keyHolder.getKeys() == null ? null : Friendship.build(keyHolder.getKeys());
    }

    public List<Friendship> saveAll(List<Friendship> friendships) {
        return friendships.stream().map(this::save).collect(Collectors.toList());
    }
}
