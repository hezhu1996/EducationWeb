package com.hezhu.servicemsm.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.naming.utils.StringUtils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService{
    //发送短信方法
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4G2BQR5TjmromEcXKwd2", "l9XrtYAY1eV9fES7pIpxy9TqK0Vmko");
        IAcsClient client = new DefaultAcsClient(profile);

        //设置相关参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "我的叮咚在线教育网站");
        request.putQueryParameter("TemplateCode", "SMS_212694260"); //模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        }
        catch (ServerException e) {
            e.printStackTrace();
        }
        catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
