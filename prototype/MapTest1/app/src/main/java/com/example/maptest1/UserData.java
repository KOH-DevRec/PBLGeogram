package com.example.maptest1;

public class UserData {
    private String name;         //ユーザ名
    private String pass;         //パスワード
    private String email;         //メールアドレス

    public UserData(){}

    public UserData(String name,String pass,String email){
        this.name = name;
        this.pass = pass;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }
}
