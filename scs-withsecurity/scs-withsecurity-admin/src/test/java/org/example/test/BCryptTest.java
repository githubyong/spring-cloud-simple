package org.example.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BCryptTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(UUID.randomUUID().toString().replace("-","").substring(0,20));
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);

        long t0 = System.currentTimeMillis();
        for (String str : list) {
            encoder.encode(str);
        }
        long t1 = System.currentTimeMillis();
        System.out.println(t1-t0);
    }
}
