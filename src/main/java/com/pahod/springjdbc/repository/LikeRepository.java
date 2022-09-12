package com.pahod.springjdbc.repository;


import com.pahod.springjdbc.entity.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.pahod.springjdbc.repository.SqlQueries.*;

@Repository
public class LikeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LikeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void dropTable() {
        jdbcTemplate.update(con -> {
            String sqlCreateTableUsers = String.format(SQL_DROP_TABLE_, "likes");
            return con.prepareStatement(sqlCreateTableUsers);
        });
    }

    public void createTable() {
        jdbcTemplate.update(con -> con.prepareStatement(SQL_CREATE_TABLE_LIKES));

        //create index to speed up request Task1Repository.getRequiredData()
        //Execution Time will drop from: 43063.166 ms to 196.949 ms
        jdbcTemplate.update(con -> con.prepareStatement(SQL_CREATE_INDEX_FOR_LIKES));
    }

    public Like save(Like like) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SqlQueries.SQL_INSERT_LIKE, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, like.getPostId());
            ps.setInt(2, like.getUserId());
            ps.setTimestamp(3, like.getLikeTimestamp());
            return ps;
        }, keyHolder);

        return Like.build(Objects.requireNonNull(keyHolder.getKeys()));
    }

    public List<Like> saveAll(List<Like> likes) {
        return likes.stream().map(this::save).collect(Collectors.toList());
    }
}
