package life.myblog.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hlq
 * @create 2019-07-28 10:33
 */
@Controller
public class IndexController {
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/error")
    public String error(){
        return "error";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name") String name, Model model){
        model.addAttribute("name",name);
        return "hello";
    }
}
