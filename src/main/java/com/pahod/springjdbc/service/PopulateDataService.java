package com.pahod.springjdbc.service;

import com.pahod.springjdbc.utils.DataGenerator;
import com.pahod.springjdbc.entity.Friendship;
import com.pahod.springjdbc.entity.Like;
import com.pahod.springjdbc.entity.Post;
import com.pahod.springjdbc.entity.User;
import com.pahod.springjdbc.repository.FriendshipRepository;
import com.pahod.springjdbc.repository.LikeRepository;
import com.pahod.springjdbc.repository.PostRepository;
import com.pahod.springjdbc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PopulateDataService {

    @Autowired
    private DataGenerator dataGenerator;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeRepository likeRepository;

    public void populateData() {
        likeRepository.dropTable();
        postRepository.dropTable();
        friendshipRepository.dropTable();
        userRepository.dropTable();

        userRepository.createTable();
        friendshipRepository.createTable();
        postRepository.createTable();
        likeRepository.createTable();


        int usersAmount = 1000;
        int friendshipsAmount = 70000;
        int likesAmount = 300000;
        int postsAmount = 20000;

        populateUsers(usersAmount);
        populateFriendships(usersAmount, friendshipsAmount);
        populatePosts(usersAmount, postsAmount);
        populateLikes(usersAmount, likesAmount, postsAmount);

    }

    private void populateUsers(int usersAmount) {
        List<User> users = dataGenerator.getUsers(usersAmount);
        userRepository.saveAll(users)
                .forEach(System.out::println);
    }

    private void populateFriendships(int usersAmount, int friendshipsAmount) {
        List<Friendship> friendShips = dataGenerator.getFriendShips(friendshipsAmount, usersAmount);
        friendshipRepository.saveAll(friendShips).
                forEach(System.out::println);

    }

    private void populatePosts(int usersAmount, int postsAmount) {
        List<Post> posts = dataGenerator.getPosts(postsAmount, usersAmount);
        postRepository.saveAll(posts)
                .forEach(System.out::println);

    }

    private void populateLikes(int usersAmount, int likesAmount, int postsAmount) {
        List<Like> likes = dataGenerator.getLikes(likesAmount, usersAmount, postsAmount);
        likeRepository.saveAll(likes)
                .forEach(System.out::println);
    }


}
