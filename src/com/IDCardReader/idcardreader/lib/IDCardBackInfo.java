package com.IDCardReader.idcardreader.lib;

public class IDCardBackInfo {
	//ǩ������
	private int officeLen;		
	private char[] office;
	
	//��Ч����
	private String date1;
	private String date2;
	
	public IDCardBackInfo()
	{
		officeLen = 0;
		office = new char[32];
		
		date1 = "";
		date2 = "";
	}
	
	public void CleanInfo()
	{
		officeLen = 0;
		office = new char[32];
		
		date1 = "";
		date2 = "";
	}
    
    public String getOfficeStr() {
		return String.valueOf(office);
	}

	public String getDateStr() {
		return date1 + "-" + date2;
	}
}
