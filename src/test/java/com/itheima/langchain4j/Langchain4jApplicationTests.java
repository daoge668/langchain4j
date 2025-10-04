package com.itheima.langchain4j;

import com.itheima.langchain4j.Pojo.Reservation;
import com.itheima.langchain4j.Service.VolunteerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class Langchain4jApplicationTests {
    @Autowired
    private VolunteerService volunteerService;

    @Test
    void contextLoads() {
//        Reservation reservation = new Reservation(2L, "张三", "男", "12345678901", LocalDateTime.now(), "北京", 5);
//        volunteerService.insert(reservation);
        Reservation byPhone = volunteerService.findByPhone("12345678901");
        System.out.println(byPhone);
    }

}
