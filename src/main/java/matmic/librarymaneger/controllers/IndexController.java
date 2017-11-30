package matmic.librarymaneger.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

//    @RequestMapping({"", "/", "/login"})
//    public String loginPage(){
//        return "login";
//    }

    @RequestMapping("/index")
    public String getIndexPage(){
        return "index";
    }
}
