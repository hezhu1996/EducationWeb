package com.hezhu.educms.controller;

import com.hezhu.commonutils.R;
import com.hezhu.educms.entity.CrmBanner;
import com.hezhu.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/educms/bannerfront")
//@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    //查询所有banner
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        //调用service
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list", list);
    }
}
