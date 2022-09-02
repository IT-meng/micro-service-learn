package cn.itcast.hotel.pojo;

import lombok.Data;

@Data
public class RequestParams {
    private String key;//搜索关键字
    private Integer page;//当前页码
    private Integer size;//每页显示条数
    private String sortBy;//排序字段
    private String city;
    private String brand;
    private String starName;
    private Integer minPrice;
    private Integer maxPrice;
    private String location;
}
