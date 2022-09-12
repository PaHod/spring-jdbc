package com.pahod.springjdbc.service;

import com.pahod.springjdbc.repository.FriendshipRepository;
import com.pahod.springjdbc.repository.LikeRepository;
import com.pahod.springjdbc.repository.PostRepository;
import com.pahod.springjdbc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RetrieveDataService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeRepository likeRepository;

    /**
     * Program should print out all names (only distinct) of users who has more than 100 friends and
     * 100 likes in March 2025. Implement all actions (table creation, insert data and reading) with SpringJDBC.
     */
    public void getData() {


    }
}
