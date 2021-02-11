package com.hezhu.edu.controller;

import com.hezhu.commonutils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {
    //login
    @PostMapping("login")
    public R login() {
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles","admin").data("name","admin").data("avatar","http://www.yzm4.com/image/newimage/make2.php?name=He%20Zhu&zt=0&dx=0&gender=1&id=25994&sy=1");
    }
}
