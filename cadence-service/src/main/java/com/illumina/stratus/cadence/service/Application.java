package com.illumina.stratus.cadence.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Running cadence service.");
        SpringApplication.run(Application.class, args);
    }
}