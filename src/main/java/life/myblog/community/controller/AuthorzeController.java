package life.myblog.community.controller;

import life.myblog.community.dto.AccessTokenDTO;
import life.myblog.community.dto.GithubUser;
import life.myblog.community.provider.GitProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUrl;

    @GetMapping("/callback")
    public  String callback(@RequestParam(name="code")String code,
                            @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = gitProvider.getAccessToken(accessTokenDTO);
        GithubUser user = gitProvider.getUser(accessToken);
        return "index";
    }
}
