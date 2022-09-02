package cn.itcast.hotel.controller;

import cn.itcast.hotel.pojo.PageResult;
import cn.itcast.hotel.pojo.RequestParams;
import cn.itcast.hotel.service.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class HotelController {

    @Autowired
    private IHotelService service;

    @PostMapping("/list")
    public PageResult search(@RequestBody RequestParams requestParams){
            return service.search(requestParams);
    }

    @PostMapping("/filters")
    public Map<String, List<String>> filters(@RequestBody RequestParams params){
        return service.filters(params);
    }

    @GetMapping("/suggestion")
    public List<String> getSuggestions(String key){
        return service.getSuggestions(key);
    }
}
