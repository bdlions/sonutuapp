/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sonuto.users;

/**
 *
 * @author Omar
 */
public class BusinessProfileInfo {

    private String business_name;
    private int business_profile_id;
    
    public String getBusinessProfileName() {
        return business_name;
    }

    public void setBusinessProfileName(String bp_name) {
        this.business_name = business_name;
    }


    public void setUserBusinessProfileId(int id) {
        this.business_profile_id = id;
    }
    
    
    public int getUserBusinessProfileId() {
		return business_profile_id;
	}
    
}
