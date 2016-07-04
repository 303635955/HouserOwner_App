package com.yunguo.Bean;

import java.util.List;
import java.util.Map;

public class HouseBean {
	private List<Map<String, String>> listItems;
	private String HouseName;
	
	public List<Map<String, String>> getListItems() {
		return listItems;
	}
	public void setListItems(List<Map<String, String>> listItems) {
		this.listItems = listItems;
	}
	private String HouseAdress;
	
	public String getHouseName() {
		return HouseName;
	}
	public void setHouseName(String houseName) {
		HouseName = houseName;
	}
	public String getHouseAdress() {
		return HouseAdress;
	}
	public void setHouseAdress(String houseAdress) {
		HouseAdress = houseAdress;
	}
}
