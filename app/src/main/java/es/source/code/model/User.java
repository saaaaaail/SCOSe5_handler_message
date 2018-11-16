package es.source.code.model;

/**
 * Created by sail on 2018/10/5.
 */

public class User {
    private String userName;
    private String password;
    private Boolean oldUser;

    public User(String userName,String password,Boolean oldUser){
        this.userName=userName;
        this.password=password;
        this.oldUser=oldUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getOldUser() {
        return oldUser;
    }

    public void setOldUser(Boolean oldUser) {
        this.oldUser = oldUser;
    }
}
