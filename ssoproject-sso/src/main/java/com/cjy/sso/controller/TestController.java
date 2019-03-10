package com.cjy.sso.controller;


import com.cjy.sso.pojo.User;
import com.cjy.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value="test")
@Controller
public class TestController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value="index")
    public String index(){
        System.out.println("============index================");
        return "测试访问 index 页面";
    }

    @ResponseBody
    @RequestMapping(value="queryUserName")
    public User queryUserName(){
        User user = userService.findUserByUserName("chenjy");
        System.out.println("==========userInfo:"+user.toString());
        return user;
    }

    @ResponseBody
    @RequestMapping(value="queryUserById")
    public User queryUserById(){
        User user = userService.findUserById(1);
        System.out.println("==========userInfo:"+user.toString());
        return user;
    }

    @RequestMapping(value="clearCache")
    public String clearCache(){
        userService.cleanCache("chenjy");
        return "qingchu huancun  chonggong ";
    }

    /**
     *
     * 一下方法测试收到get set缓存
     */


    @ResponseBody
    @RequestMapping(value="get")
    public String get(String key){
        String val = (String)redisTemplate.opsForValue().get(key);
        System.out.println("===============val:"+val);
        return val;
    }

    @RequestMapping(value="set")
    public void set(String key,String value){
        redisTemplate.opsForValue().set(key, value);
        System.out.println("===============set val");
    }
}
