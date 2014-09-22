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

    private String firstName;
    private String lastName;
    private String userName;
    private int userId;
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
    
}
