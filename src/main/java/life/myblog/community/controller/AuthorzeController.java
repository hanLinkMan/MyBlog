package life.myblog.community.controller;

import life.myblog.community.dto.AccessTokenDTO;
import life.myblog.community.dto.GithubUser;
import life.myblog.community.provider.GitProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author hlq
 * @create 2019-07-28 23:31
 */
@Controller
public class AuthorzeController {

    @Autowired
    private GitProvider gitProvider;
    @GetMapping("/callback")
    public  String callback(@RequestParam(name="code")String code,
                            @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("944f4524d2f2880f1727");
        accessTokenDTO.setClient_secret("0c6c853e7e95794415716022759c8a63e24202f8");
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = gitProvider.getAccessToken(accessTokenDTO);
        GithubUser user = gitProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
