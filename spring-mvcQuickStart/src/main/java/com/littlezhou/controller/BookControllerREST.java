package com.littlezhou.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BookControllerREST {

    //Get:查询

    //查询所有
    @RequestMapping(value = "/books",method = RequestMethod.GET)
    @ResponseBody
    public String findAll(){
        System.out.println("findAll");
        return "findAll";
    }

    @RequestMapping(value = "/books/{id}" ,method = RequestMethod.GET)
    @ResponseBody
    public String findById(@PathVariable Integer id){
        System.out.println("findById?id="+id);
        return "findById";
    }


    //Post:新增(保存)

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    @ResponseBody
    public String saveAll(){
        System.out.println("saveAll");
        return "saveAll";
    }



    //Put:更新

    @RequestMapping(value = "/books/{id}" , method = RequestMethod.PUT)
    @ResponseBody
    public String update(@PathVariable Integer id){
        System.out.println("updateById");
        return "updateById";
    }

    //Delete:删除

    @RequestMapping(value = "/books",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteAll(){
        System.out.println("deleteAll");
        return "deleteAll";
    }

    @RequestMapping(value = "/books/{id}" ,method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteById(@PathVariable Integer id){
        System.out.println("deleteById");
        return "deleteById";
    }

}
