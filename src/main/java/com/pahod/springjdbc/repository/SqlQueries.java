package com.pahod.springjdbc.repository;

public class SqlQueries {


    public static final String SQL_DROP_TABLE_ =
            " DROP TABLE IF EXISTS %s;";

    public static final String SQL_CREATE_TABLE_USERS =
            " CREATE TABLE IF NOT EXISTS users(" +
                    "  id SERIAL NOT NULL PRIMARY KEY, " +
                    "  first_name varchar(50) NOT NULL, " +
                    "  last_name  varchar(50) NOT NULL, " +
                    "  birthdate date NOT NULL" +
                    " );";

    public static final String SQL_CREATE_TABLE_POSTS =
            " CREATE TABLE IF NOT EXISTS posts(" +
                    "  id SERIAL NOT NULL PRIMARY KEY, " +
                    "  user_id INTEGER NOT NULL REFERENCES users(id) , " +
                    "  text varchar(500) NOT NULL, " +
                    "  post_time TIMESTAMP NOT NULL" +
                    " );";

    public static final String SQL_CREATE_TABLE_LIKES =
            " CREATE TABLE IF NOT EXISTS likes(" +
                    "  post_id INTEGER NOT NULL REFERENCES posts(id) , " +
                    "  user_id INTEGER NOT NULL REFERENCES users(id) , " +
                    "  like_timestamp TIMESTAMP NOT NULL" +
                    " );";

    public static final String SQL_CREATE_INDEX_FOR_LIKES =
            "CREATE INDEX ON likes using hash (user_id);";

    public static final String SQL_CREATE_TABLE_FRIENDSHIPS =
            " CREATE TABLE IF NOT EXISTS friendships(" +
                    " user_id1 INTEGER NOT NULL REFERENCES users(id), " +
                    " user_id2 INTEGER NOT NULL REFERENCES users(id), " +
                    " friend_timestamp TIMESTAMP NOT NULL," +
                    " CONSTRAINT pk_friends PRIMARY KEY (user_id1, user_id2)" +
                    " );";


    public static final String SQL_INSERT_USER =
            " INSERT INTO users( " +
                    "  first_name, " +
                    "  last_name, " +
                    "  birthdate " +
                    " ) VALUES (?,?,?)";


    public static final String SQL_INSERT_POST =
            " INSERT INTO posts( " +
                    "  user_id, " +
                    "  text, " +
                    "  post_time " +
                    " ) VALUES (?,?,?)";

    public static final String SQL_INSERT_LIKE =
            " INSERT INTO likes( " +
                    "  post_id, " +
                    "  user_id, " +
                    "  like_timestamp " +
                    " ) VALUES (?,?,?)";

    public static final String SQL_INSERT_FRIENDSHIP =
            " INSERT INTO friendships( " +
                    "  user_id1, " +
                    "  user_id2, " +
                    "  friend_timestamp " +
                    " ) VALUES (?,?,?)" +
                    " ON CONFLICT ON CONSTRAINT pk_friends DO NOTHING";


}