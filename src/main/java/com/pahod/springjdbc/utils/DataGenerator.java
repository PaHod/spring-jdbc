package com.pahod.springjdbc.utils;

import com.pahod.springjdbc.entity.Friendship;
import com.pahod.springjdbc.entity.Like;
import com.pahod.springjdbc.entity.Post;
import com.pahod.springjdbc.entity.User;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.pahod.springjdbc.utils.LastNamesDictionary.LAST_NAMES;
import static com.pahod.springjdbc.utils.NamesDictionary.FIRST_NAMES;

@Component
public class DataGenerator {

    private final long TEN_YEARS = 315569259747L;
    private final long birthdateLimit = System.currentTimeMillis() - TEN_YEARS;
    private final Random random = new Random();

    public List<User> getUsers(int usersAmount) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < usersAmount; i++) {
            User user = generateUser();
            users.add(user);
        }
        return users;
    }

    private User generateUser() {
        return User.builder()
                .firstName(getRandomName(FIRST_NAMES))
                .lastName(getRandomName(LAST_NAMES))
                .birthdate(getBirthdate())
                .build();
    }

    private Date getBirthdate() {
        return new Date(ThreadLocalRandom.current().nextLong(birthdateLimit));
    }

    private String getRandomName(String[] names) {
        return names[random.nextInt(names.length)];
    }

    public List<Friendship> getFriendShips(int friendshipsAmount, int usersAmount) {
        Map<Integer, List<Integer>> usedIDs = new HashMap<>();
        List<Friendship> friendships = new ArrayList<>();
        for (int j = 0; j < friendshipsAmount; j++) {
            int userId1 = getRandomId(usersAmount);
            int userId2 = getDifferentRandomId(usersAmount, userId1, usedIDs);
            friendships.add(Friendship.builder()
                    .userId1(userId1)
                    .userId2(userId2)
                    .friendTimestamp(getRandomTime("2015-01-01 00:00:00", "2026-12-31 23:59:59"))
                    .build());
        }
        return friendships;
    }

    private Timestamp getRandomTime(String startDate, String endDate) {
        long startTime = 0;
        long endTime = Long.MAX_VALUE;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startTime = format.parse(startDate).getTime();
            endTime = format.parse(endDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long l = ThreadLocalRandom.current().nextLong(endTime - startTime) + startTime;
        return new Timestamp(l);
    }

    private int getDifferentRandomId(int usersAmount, int userId1, Map<Integer, List<Integer>> usedIDs) {
        int userId2 = getRandomId(usersAmount);
        while (userId1 == userId2
                || usedIDs.containsKey(userId1) && usedIDs.get(userId1).contains(userId2)
                || usedIDs.containsKey(userId2) && usedIDs.get(userId2).contains(userId1)) {
            userId2 = getRandomId(usersAmount);
        }
        List<Integer> existingPairs = usedIDs.computeIfAbsent(userId1, k -> new ArrayList<>());
        existingPairs.add(userId2);
        return userId2;
    }

    private int getRandomId(int usersAmount) {
        return random.nextInt(usersAmount) + 1;
    }

    public List<Like> getLikes(int likesAmount, int usersAmount, int postsAmount) {
        List<Like> likes = new ArrayList<>();
        for (int j = 0; j < likesAmount; j++) {
            likes.add(Like.builder()
                    .postId(getRandomId(postsAmount))
                    .userId(getRandomId(usersAmount))
                    .likeTimestamp(getRandomTime("2025-01-01 00:00:00", "2026-12-31 23:59:59"))
                    .build());
        }
        return likes;
    }

    public List<Post> getPosts(int postsAmount, int usersAmount) {
        List<Post> posts = new ArrayList<>();
        for (int j = 0; j < postsAmount; j++) {
            posts.add(Post.builder()
                    .userId(getRandomId(usersAmount))
                    .text("lorem ipsum dolor sit amet")
                    .postTimestamp(getRandomTime("2015-01-01 00:00:00", "2026-12-31 23:59:59"))
                    .build());
        }
        return posts;
    }
}
