package com.luissoy.atmbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/v1/pings")
public class PingController {

    @GetMapping
    public String getPing() {
        return "pong";
    }
}
