package com.example.retrofitcodinginflow;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("body")
    private String text;

    private int userId;
    private Integer id;
    private String title;

    public Post(String text, int userId, String title) {
        this.text = text;
        this.userId = userId;
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
