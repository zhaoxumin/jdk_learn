package com.example.jdk_learn.keywords;

import lombok.*;

import java.io.Serializable;

/**
 * @author xumin zhao
 * @version 1.0
 * @description User
 * @date 2020/8/27 14:35
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {

    private static final long serialVersionUID= 989543898485507283L;

    private transient Integer age;

    private String name;

}
