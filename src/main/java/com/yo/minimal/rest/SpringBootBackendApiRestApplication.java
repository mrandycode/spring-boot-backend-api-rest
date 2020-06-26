package com.yo.minimal.rest;

import com.yo.minimal.rest.models.services.interfaces.IUploadFilePhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootBackendApiRestApplication implements CommandLineRunner {

    @Autowired
    IUploadFilePhoto iUploadFilePhoto;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBackendApiRestApplication.class, args);

    }

    public void run(String... arg0)  {

    }
}