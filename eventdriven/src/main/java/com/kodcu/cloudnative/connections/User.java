package com.kodcu.cloudnative.connections;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by corneliadavis on 9/4/17.
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String username;

    protected User() {}

    public User(String name, String username) {
        this.name = name;
        this.username = username;
    }


    @JsonbCreator
    public User(@JsonbProperty("id") Long id ,
                @JsonbProperty("name") String name,
                @JsonbProperty("username") String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
