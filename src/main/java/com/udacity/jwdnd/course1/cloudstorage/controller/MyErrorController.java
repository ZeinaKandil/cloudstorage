package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController  {

    @RequestMapping("/error")
    public String handleError(Model model) {
        //do something like logging
        model.addAttribute("key", "value");

        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}