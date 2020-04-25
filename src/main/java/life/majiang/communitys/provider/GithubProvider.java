package life.majiang.communitys.provider;


import com.alibaba.fastjson.JSON;
import life.majiang.communitys.dto.AccesstokenDTO;
import life.majiang.communitys.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    //获取accessToken的方法
    public String getAccessToken(AccesstokenDTO accesstokenDTO) {
        //设置媒体类型  json格式 字符集utf-8
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        //此处将 dto类型 转为JSON格式数据
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accesstokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //根据获取的字符串 切割 获取 token
            String token = string.split("&")[0].split("=")[1];
            System.out.println("token = " + token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取User信息的方法
    public GithubUser getUser(String accessToken) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            //返回的JSON数据
            String string = response.body().string();
            //将json数据 解析为 传入的第二个参数类型的 对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
