package com.dling.springboot.controller;

import com.dling.springboot.service.MsgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;

//@Controller
@RestController
//@EnableAutoConfiguration
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private MsgRepository msgRepository;

    // http://localhost:8001/boot/demo/111?id=222
    @GetMapping("/show/{id}")
    String show(@RequestParam(value = "id", required = false) String name, @PathVariable String id){
        System.out.println("show()");
        return "<h1>"+name+"</h1>"+id;
    }

    // http://localhost:8001/boot/demo/home/lingdian/18
    @GetMapping("/home/{name}/{age}")
    String home(@PathVariable("name") String id, @PathVariable("age") String age1, @PathVariable String age){
        System.out.println("home()");
        return "<h1>name:"+id+"<br>age:"+age1+"<br>num:"+age+"</h1>";
    }

    // http://localhost:8001/boot/demo/index
    @RequestMapping("/templates")
    ModelAndView templates(Model model){
        System.out.println("http://localhost:8001/boot/demo/templates");
//        modelAndView.setViewName("templates.html");
//        modelAndView.addObject("index", "首页");
        model.addAttribute("thymeleaf", "thymeleaf");
        model.addAttribute("freemarker", "freemarker");
        return new ModelAndView("templates.html"); // 可以省略后缀 .html
    }

    // http://localhost:8001/boot/demo/freemaker
    @RequestMapping("/freemaker")
    ModelAndView freemaker(Model model){
        System.out.println("http://localhost:8001/boot/demo/freemaker");
        model.addAttribute("isOk", true);
//        model.addAttribute("msgs", new ArrayList<>());
        model.addAttribute("msgs", msgRepository.findAll());
        model.addAttribute("tbMsg", msgRepository.getOne(3));
        model.addAttribute("freemarker", "freemarker");
        model.addAttribute("now", new Date());
        // 使用freemarker不可以省略后缀 .html，且配置中前缀需为空suffix=
        return new ModelAndView("home.html");
    }

}

