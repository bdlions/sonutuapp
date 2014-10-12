package com.sonuto.users;

public class Country {
	
	private int id;
	private String country_name;
	
	
	public String getCountry_name() {
		return country_name;
	}

	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String toString()
    {
        return country_name;
    }

}
