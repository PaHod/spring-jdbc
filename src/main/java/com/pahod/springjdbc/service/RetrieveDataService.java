package com.pahod.springjdbc.service;

import com.pahod.springjdbc.repository.Task1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RetrieveDataService {

    @Autowired
    private Task1Repository repository;

    /**
     * Program should print out:
     * all names (only distinct) of users who has:
     * more than 100 friends
     * and 100 likes in March 2025.
     */
    public void getData() {
        System.out.println("Retrieve names from database.");
        List<String> names = repository.getRequiredData();
        names.forEach(System.out::println);
        System.out.printf("In total retrieved %d names.\n", names.size());
    }
}
