package life.majiang.communitys.controller;

import life.majiang.communitys.dto.AccesstokenDTO;
import life.majiang.communitys.dto.GithubUser;
import life.majiang.communitys.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;


    @GetMapping("/callback")
    public String callBack(@RequestParam String code,
                           @RequestParam String state) {
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        //Client_id  setClient_secret  setRedirect_uri  注册的AuthorApp时都有
        accesstokenDTO.setClient_id("742789cdbb1231771ec1");
        accesstokenDTO.setClient_secret("5bb9b4f8cd0b2ad5ec78de16faca5f90d94de9d6");
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accesstokenDTO.setState(state);

        //调用okhttp提供的方法  去发送一个请求  并且请求accesstoken接口  携带code
        String accessToken = githubProvider.getAccessToken(accesstokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
