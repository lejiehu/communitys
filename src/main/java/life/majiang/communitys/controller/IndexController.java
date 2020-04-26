package life.majiang.communitys.controller;

import life.majiang.communitys.dto.User;
import life.majiang.communitys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        //访问首页  获取cookies
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())){
                //获取cookie中value值  带入数据查询
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                if (user !=null){
                    //向session中写入user
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        return "index";
    }
}
