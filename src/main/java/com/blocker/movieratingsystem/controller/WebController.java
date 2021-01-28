package com.blocker.movieratingsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class WebController {

    @GetMapping
    public String index() {
        log.info("Call index page.");
        return "index";
    }
}
