package com.example.mybbms;

public class User {
    public String Temperature;
    public String username,email;
    public User(String temperature)
    {
        this.Temperature=temperature;
    }
    public User(String name, String email)
    {
        this.username = name;
        this.email=email;
          }
}

