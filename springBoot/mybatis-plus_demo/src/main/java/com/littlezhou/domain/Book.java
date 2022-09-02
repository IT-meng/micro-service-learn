package com.littlezhou.domain;

import com.baomidou.mybatisplus.annotation.*;

@TableName("tbl_book")
public class Book {
    @TableId(type = IdType.ASSIGN_ID)//控制id生成策略(雪花算法)
    private Integer id;

    @TableField(select = false)//指明此字段不参与查询,密码字段一般就不参与查询
    private String type;

//    @TableField(exist = false)//指明此字段数据库中不存在
    private String name;

//    @TableField(value = "pwd")//配置和数据库中字段的对应关系
    private String description;

    //可做全局配置
//    @TableLogic(value = "0", delval = "1")
    private Integer deleted;


    @Version//指定用于实现乐观锁的字段
    private Integer version;

    public Book(Integer id, String type, String name, String description) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public Book() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
