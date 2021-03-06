package com.hezhu.edu.client.impl;

import com.hezhu.commonutils.R;
import com.hezhu.edu.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodFileDegradeFeignClient implements VodClient {
    //Hytrix:出错后会执行
    @Override
    public R removeAliyunVideo(String videoId) {
        return R.error().message("time out");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("time out");
    }
}
