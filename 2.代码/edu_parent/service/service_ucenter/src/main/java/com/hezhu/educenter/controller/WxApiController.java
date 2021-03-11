package com.hezhu.educenter.controller;

import com.google.gson.Gson;
import com.hezhu.commonutils.JwtUtils;
import com.hezhu.educenter.entity.UcenterMember;
import com.hezhu.educenter.service.UcenterMemberService;
import com.hezhu.educenter.utils.ConstantWxUtils;
import com.hezhu.educenter.utils.HttpClientUtils;
import com.hezhu.servicebase.exceptionhandler.HeZhuException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.HashMap;

@RequestMapping("/api/ucenter/wx")
@Controller //不需要返回数据，只请求数据
@CrossOrigin
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    //1.生产微信扫描二维码
    @GetMapping("login")
    public String getWxCode() {
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        System.out.println("ConstantWxUtils.WX_OPEN_REDIRECT_URL: " + ConstantWxUtils.WX_OPEN_REDIRECT_URL);

        // 回调地址
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8"); //url编码
        } catch (Exception e) {

        }
        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu");

        //重定向到：请求微信地址
        return "redirect:" + qrcodeUrl;
    }

    //2.获取扫码人信息，添加数据
    @GetMapping("callback")
    public String callback(String code, String state) {
        try {
            //1.得到授权临时票据code和state

            //2.向认证服务器发送请求换取access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接：id秘钥和code值
            String accessTokenUrl = String.format(baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code);

            //3.请求这个拼接好的地址，得到返回两个值access_token和openid
            //使用httpclient发送请求，得到返回结果
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println(accessTokenInfo);

            //4.从accessTokenInfo获取access_token和openId
            //将字符串转换为map集合
            Gson gson = new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");



            //9.自动注册：加入数据库
            //9.1 判断数据表中是否存在相同数据信息：根据openId判断
            UcenterMember member = memberService.getOpenIdMember(openid);

            if (member == null) {
                //* 如果没有用户，则请求地址，得到微信名称等信息。如果有数据(openid)，则直接登录

                //5.拿着access_token和openid，再去请求微信提供固定的地址，获取到扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //6.拼接两个参数
                String userInfoUrl = String.format(baseUserInfoUrl,
                        access_token,
                        openid);
                //7.发送请求
                String userInfo = HttpClientUtils.get(userInfoUrl);

                //8.将字符串转化为json对象：存储了扫码人的信息
                HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
                String nickname = (String) userInfoMap.get("nickname"); //昵称
                String headimgurl = (String) userInfoMap.get("headimgurl"); //头像


                //表中没有数据
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);

                memberService.save(member);
            }
            //9.2 如果数据库中已经存在，啥也不需要做

            //10.使用jwt根据member对象生成token字符串
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

            //11.返回首页面
            //通过路径传递token
            return "redirect:http://localhost:3000?token="+jwtToken;

        } catch (Exception e) {
            throw new HeZhuException(20001, "微信登录失败");
        }

    }
}
