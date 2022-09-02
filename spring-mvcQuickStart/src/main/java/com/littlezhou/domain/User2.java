package com.littlezhou.domain;

public class User2 {
    int age;
    String name;
    User user;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "User2{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", user=" + user +
                '}';
    }
}
