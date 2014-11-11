package com.sportzweb.JSONObjectModel;

public class ServiceItem {
	private int service_id;
	private int service_category_id;
	private String title;
	private String address;
	private String phone;
	private String distance;
	
	public int getService_id() {
		return service_id;
	}
	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	public int getService_category_id() {
		return service_category_id;
	}
	public void setService_category_id(int service_category_id) {
		this.service_category_id = service_category_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
}
