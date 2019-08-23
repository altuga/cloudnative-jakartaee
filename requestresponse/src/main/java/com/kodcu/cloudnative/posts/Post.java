package com.kodcu.cloudnative.posts;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by corneliadavis on 9/4/17.
 */
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Date date;
    private Long userId;
    private String title;
    private String body;



    protected Post() {}

    public Post(Long userId, String title, String body) {
        this.date = new Date();
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    @JsonbCreator
    public Post(@JsonbProperty("id") Long id,
                @JsonbProperty("userId") Long userId,
                @JsonbProperty("title") String title,
                @JsonbProperty("body")  String body) {
        this.id = id;
        this.date = new Date();
        this.userId = userId;
        this.title = title;
        this.body = body;
    }


    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
