package com.yunguo.Bean;

import java.util.HashMap;
import java.util.Map;

public class SetUpRent {
	private static SetUpRent setUpRent;
	
	private String HouseId;
	
	private Map<String , String > Rentmap ;
	private Map<String , String > Housemap ;
	private Map<String , String > Attionmap ;
	
	private SetUpRent(){
		
	}
	public static SetUpRent getSetUpRent(){
		if(setUpRent ==null){
			setUpRent = new SetUpRent();
		}
		return setUpRent;
	}
	public String getHouseId() {
		return HouseId;
	}
	public void setHouseId(String houseId) {
		HouseId = houseId;
	}
	
	public Map<String, String> getRentmap() {
		return Rentmap;
	}
	public void setRentmap(Map<String, String> rentmap) {
		Rentmap = rentmap;
	}
	public Map<String, String> getHousemap() {
		return Housemap;
	}
	public void setHousemap(Map<String, String> housemap) {
		Housemap = housemap;
	}
	public Map<String, String> getAttionmap() {
		return Attionmap;
	}
	public void setAttionmap(Map<String, String> attionmap) {
		Attionmap = attionmap;
	}
}
