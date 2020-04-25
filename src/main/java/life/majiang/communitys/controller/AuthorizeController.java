package life.majiang.communitys.controller;

import life.majiang.communitys.dto.AccesstokenDTO;
import life.majiang.communitys.dto.GithubUser;
import life.majiang.communitys.dto.User;
import life.majiang.communitys.mapper.UserMapper;
import life.majiang.communitys.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callBack(@RequestParam String code,
                           @RequestParam String state,
                           HttpServletRequest request) {
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        //Client_id  setClient_secret  setRedirect_uri  注册的AuthorApp时都有
        accesstokenDTO.setClient_id(clientId);
        accesstokenDTO.setClient_secret(clientSecret);
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_uri(redirectUri);
        accesstokenDTO.setState(state);

        //调用okhttp提供的方法  去发送一个请求  并且请求accesstoken接口  携带code
        String accessToken = githubProvider.getAccessToken(accesstokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            User user = new User();
            user.setName(githubUser.getName());
            user.setToken(UUID.randomUUID().toString());
//            //Long类型转成String类型
            user.setAccountId(String.valueOf(githubUser.getId()));
//            //系统当前毫秒值
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            System.out.println("user = " + user);
            userMapper.insert(user);
            request.getSession().setAttribute("user", githubUser);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
