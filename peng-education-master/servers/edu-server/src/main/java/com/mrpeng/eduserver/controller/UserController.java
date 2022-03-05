package com.mrpeng.eduserver.controller;

import com.mrpeng.pojo.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("eduserver/user")
//@CrossOrigin
public class UserController {

    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=286605818,4063183168&fm=11&gp=0.jpg");
    }
}
