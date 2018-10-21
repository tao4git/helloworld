package com.yint.helloworld.contorller;


import com.yint.helloworld.common.UserLoalCache;
import com.yint.helloworld.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {


    @RequestMapping({"/","/hello"})
    @ResponseBody
    public String hello(){
        User user = UserLoalCache.getUser("yint");
        System.out.println("test:"+user);
        if(null == user){
            user = new User();
            user.setUname("yint");
            user.setStatus("0");
            UserLoalCache.saveUser(user);
        }
        return "hello,world，李浩然"+user;
    }
}
