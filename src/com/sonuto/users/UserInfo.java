/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonuto.users;

/**
 *
 * @author Alamgir
 */
public class UserInfo {

    private String first_name;
    private String last_name;
    private String email;
    private String userName;
    private int userId;
    
    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public int userId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }
    
    public String getUserName() {
		return userName;
	}
    public int getUserId() {
		return userId;
	}
    public void setUserName(String userName) {
		this.userName = userName;
	}
    public String getEmail() {
		return email;
	}
    public void setEmail(String email) {
		this.email = email;
	}
}
