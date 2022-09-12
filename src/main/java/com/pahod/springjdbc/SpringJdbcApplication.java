package com.pahod.springjdbc;

import com.pahod.springjdbc.service.PopulateDataService;
import com.pahod.springjdbc.service.RetrieveDataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringJdbcApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringJdbcApplication.class, args);

        PopulateDataService populateDataService = context.getBean(PopulateDataService.class);

        populateDataService.populateData();

        RetrieveDataService retrieveDataService = context.getBean(RetrieveDataService.class);
        retrieveDataService.getData();


    }

}
