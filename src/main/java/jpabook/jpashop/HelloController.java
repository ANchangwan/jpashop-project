package jpabook.jpashop;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    //data를 받아서 넘길 수 있음
    public String hello(Model model) {
        model.addAttribute("data", "hello");
        return "hello";
    }

    @GetMapping("hello/world")
    public String helloWorld(Model model) {
        model.addAttribute("data", "hello world");
        return "hello world";
    }
}
