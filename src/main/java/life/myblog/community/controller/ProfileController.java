package life.myblog.community.controller;

import life.myblog.community.dto.PageInfomationDto;
import life.myblog.community.mapper.UserMapper;
import life.myblog.community.model.User;
import life.myblog.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author hlq
 * @create 2019-08-12 22:02
 */
@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name="action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:index";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的发帖");
        }else if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PageInfomationDto pageInfomationDto = questionService.list(user.getId(),page,size);
        model.addAttribute("pageInfomation",pageInfomationDto);

        return "profile";
    }
}
