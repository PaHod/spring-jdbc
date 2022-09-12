package com.pahod.springjdbc.repository;


import com.pahod.springjdbc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.pahod.springjdbc.repository.Queries.SQL_CREATE_TABLE_USERS;
import static com.pahod.springjdbc.repository.Queries.SQL_DROP_TABLE_;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void dropTable() {
        jdbcTemplate.update(con -> {
            String sqlCreateTableUsers = String.format( SQL_DROP_TABLE_, "users");
            return con.prepareStatement(sqlCreateTableUsers);
        });
    }

    public void createTable() {
        jdbcTemplate.update(con -> con.prepareStatement(SQL_CREATE_TABLE_USERS));
    }

    public User save(User user) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(Queries.SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setDate(3, user.getBirthdate());
            return ps;
        }, keyHolder);

        return User.build(Objects.requireNonNull(keyHolder.getKeys()));
    }

    public List<User> saveAll(List<User> users) {
        return users.stream().map(this::save).collect(Collectors.toList());
    }
}
