package life.myblog.community.controller;

import life.myblog.community.dto.QuestionDto;
import life.myblog.community.mapper.QuestionMapper;
import life.myblog.community.mapper.UserMapper;
import life.myblog.community.model.Question;
import life.myblog.community.model.User;
import life.myblog.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author hlq
 * @create 2019-07-28 10:33
 */
@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/index")
    public String index(HttpServletRequest request,Model model){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for(Cookie cookie :cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        List<QuestionDto> questionList = questionService.list();
        model.addAttribute("questions",questionList);
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
