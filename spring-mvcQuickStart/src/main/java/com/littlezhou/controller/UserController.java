package com.littlezhou.controller;

import com.littlezhou.domain.User;
import com.littlezhou.domain.User2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {


    @RequestMapping("/save")//配置访问路径
    @ResponseBody
    public String save(String name) throws UnsupportedEncodingException {
        System.out.println("user save ...");
        System.out.println(URLDecoder.decode(name));
        return "{'info':spring-mvcQuickStart:user:save'"+name+"}";
    }


    //不同类型的参数的传递
    @RequestMapping("/pojo")
    @ResponseBody
    public String pojo(User user){//参数为引用类型，会往里设置同名的属性值
        System.out.println(user);
        return "{'info':'pojo'}";
    }


    //嵌套POJO
    @RequestMapping("/pojo2")
    @ResponseBody
    public String pojo2(User2 user2){
        System.out.println(user2);
        return "{'info':'pojo2'}";
    }

    @RequestMapping("/array")
    @ResponseBody
    public String array(String[] likes){
        System.out.println(likes[likes.length-1]);
        return "{'info':'array'}";
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(@RequestParam List<String> likes){
        System.out.println(likes);
        return "{'info':'list'}";
    }



    //json数据的传递
    @RequestMapping("/jsonTest")
    @ResponseBody
    public String jsonTest(@RequestBody User2 user){
        System.out.println(user);
       return  "{'info':'jsonTest'}";
    }

    //json对象数组

    @RequestMapping("/json/objectArray")
    @ResponseBody
    public String jsonObjectArray(@RequestBody List<User> list){
        System.out.println(list);
        return "{'info':'objectArray'}";
    }

    //日期类型的参数传递
    @RequestMapping("/date")
    @ResponseBody
    public String argsDate(Date date,
                           @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1,
                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date date2){

        System.out.println(date);
        System.out.println(date1);
        System.out.println(date2);

        return "{'info':'date_args'}";
   }

}
