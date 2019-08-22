package life.myblog.community.controller;

import life.myblog.community.dto.AccessTokenDTO;
import life.myblog.community.dto.GithubUser;
import life.myblog.community.provider.GitProvider;
import life.myblog.community.model.User;
import life.myblog.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author hlq
 * @create 2019-07-28 23:31
 */
@Controller
public class AuthorzeController {

    @Autowired
    private GitProvider gitProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUrl;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public  String callback(@RequestParam(name="code")String code,
                            @RequestParam(name="state")String state,
                            HttpServletRequest request,
                            HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = gitProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = gitProvider.getUser(accessToken);
        if (githubUser != null && githubUser.getId() != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));

            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.CreateOrUpdate(user);
//            userMapper.insert(user);
            //写入cookie
            response.addCookie(new Cookie("token",token));
            //登陆成功，写cookie和session
//            request.getSession().setAttribute("user",githubUser);

            return "redirect:index";
        }else {
            //登陆失败，重新登陆
            return "redirect:index";


        }
    }
    @GetMapping("/logout")
    public  String logout(HttpServletRequest request,
                          HttpServletResponse response){
        request.getSession().removeAttribute("user");
        request.getSession().setAttribute("user",null);
        Cookie cookie = new Cookie("token",null);
        response.addCookie(cookie);
    return "redirect:/index";

    }
}
