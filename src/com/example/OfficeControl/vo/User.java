package com.example.OfficeControl.vo;

import android.app.Application;

/**
 * Created by gm on 2016/6/1.
 */
public class User extends Application {
    private String username;
    private int id;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
