package com.hezhu.servicemsm.controller;


import com.hezhu.commonutils.R;
import com.hezhu.servicemsm.service.MsmService;
import com.hezhu.servicemsm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送短信方法
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone) {

        //1.从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }

        //2.如果redis获取不到，到阿里云发送
        //2.1生产随机验证码，传递给阿里云
        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);

        //2.2调用service发送短信
        boolean isSend = msmService.send(param, phone);
        if (isSend) {
            //2.2.1 发送成功，把发送成功验证码放到redis中
            //2.2.2 设置有效时间
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

            return R.ok();
        }
        else {
            return R.error().message("短信发送失败");
        }
    }
}
