package com.spring.qa.autoGb.lesson2.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/hello")
public class HelloView {

    @RequestMapping(method = RequestMethod.GET)
    public String printHello() {
        return "helloWorld";
    }
}