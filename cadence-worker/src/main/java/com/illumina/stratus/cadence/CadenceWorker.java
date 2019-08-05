package com.illumina.stratus.cadence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Workers with registered activities
 */
@SpringBootApplication
public class CadenceWorker {

    public static void main(String[] args) {
        SpringApplication.run(CadenceWorker.class, args);
    }


}
