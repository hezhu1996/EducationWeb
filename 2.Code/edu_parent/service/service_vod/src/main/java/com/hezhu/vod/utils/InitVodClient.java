package com.hezhu.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

public class InitVodClient {
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws Exception{
        String regiodId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regiodId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
