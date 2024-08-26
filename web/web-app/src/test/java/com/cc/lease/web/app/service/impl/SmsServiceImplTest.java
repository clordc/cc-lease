package com.cc.lease.web.app.service.impl;

import com.cc.lease.web.app.service.SmsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SmsServiceImplTest {

    @Autowired
    private SmsService service;
    @Test
    void sendcode() {
        service.sendcode("18702864922","1234");
    }
}