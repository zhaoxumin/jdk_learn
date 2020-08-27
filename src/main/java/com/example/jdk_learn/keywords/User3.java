package com.example.jdk_learn.keywords;

import lombok.*;

import java.io.Serializable;

/**
 * @author xumin zhao
 * @version 1.0
 * @description User3
 * @date 2020/8/27 15:45
 **/
@Data
@Builder
public class User3 implements Serializable {

    private static final long serialVersionUID = 989543898485507283L;

    private  transient  static Integer age=18 ;

    private String name;

    public static Integer getAge() {
        return age;
    }

    public static void setAge(Integer age) {
        User3.age = age;
    }
}
