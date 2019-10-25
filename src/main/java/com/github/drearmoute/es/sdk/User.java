package com.github.drearmoute.es.sdk;

import java.io.Serializable;

import lombok.Data;

@Data
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3052693922123419490L;
    private Long id;
    private String name;
    private String fullName;
    private String password;
    private Integer age;

}
