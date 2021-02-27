package com.app.autcards.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping
public class HomeController {
    @GetMapping
    public String homePage(HttpServletResponse res, HttpServletRequest req) {
        return "home";
    }
}
