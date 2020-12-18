package com.traffic.authentication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class AuthenticationApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        //636C69656E745F313030310A
        System.out.println(new BCryptPasswordEncoder().encode("636C69656E745F313030310A"));
        System.out.println(new BCryptPasswordEncoder().encode("traffic_1001"));

    }
}
