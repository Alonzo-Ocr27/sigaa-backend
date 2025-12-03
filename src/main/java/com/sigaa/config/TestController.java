package com.sigaa.config;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin("*")
public class TestController {

    @GetMapping("/ping")
    public String ping() {
        return "SIGAA backend OK";
    }
}