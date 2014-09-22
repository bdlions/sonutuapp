/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonuto.users;

/**
 *
 * @author Alamgir
 */
public class BusinessMan extends UserInfo implements IBusinessMan{
    public String email;
    
    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    
}
