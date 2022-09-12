package com.pahod.springjdbc.repository;


import com.pahod.springjdbc.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.pahod.springjdbc.repository.SqlQueries.SQL_CREATE_TABLE_POSTS;
import static com.pahod.springjdbc.repository.SqlQueries.SQL_DROP_TABLE_;

@Repository
public class PostRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void dropTable() {
        jdbcTemplate.update(con -> {
            String sqlCreateTableUsers = String.format(SQL_DROP_TABLE_, "posts");
            return con.prepareStatement(sqlCreateTableUsers);
        });
    }

    public void createTable() {
        jdbcTemplate.update(con -> con.prepareStatement(SQL_CREATE_TABLE_POSTS));
    }

    public Post save(Post post) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SqlQueries.SQL_INSERT_POST, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getText());
            ps.setTimestamp(3, post.getPostTimestamp());
            return ps;
        }, keyHolder);

        return Post.build(Objects.requireNonNull(keyHolder.getKeys()));
    }

    public List<Post> saveAll(List<Post> posts) {
        return posts.stream().map(this::save).collect(Collectors.toList());
    }
}
