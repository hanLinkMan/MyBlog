package life.myblog.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author hlq
 * @create 2019-07-29 23:05
 */
@Controller
public class PublishController {
    @GetMapping("publish")
    public String publish(){
        return "publish";
    };
}
