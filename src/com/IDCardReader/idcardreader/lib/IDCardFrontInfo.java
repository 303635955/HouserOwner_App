package com.IDCardReader.idcardreader.lib;

public class IDCardFrontInfo {
	//���֤������
	private String idNum;		//���֤��ʶ����
	private char[] idName;      //�����Ϣ
	private char[] idAddress;   //��ַ��Ϣ
	private char[] idNation;    //������Ϣ
	private char idSex;         //�Ա���Ϣ
	
	public IDCardFrontInfo(int preWidth, int preHeight)
	{
		idNum = "";
		idName = new char[16];
		idAddress = new char[128];
		idNation = new char[8];
		idSex = '��';
	}
	
	public void CleanInfo()
	{
		idNum = "";
		idName = new char[16];
		idAddress = new char[128];
		idNation = new char[8];
		
		idSex = '��';
	}
	
	public String getIdNumStr() {
		return idNum;
	}
	
	public String getIdNameStr() {
		return String.valueOf(idName);
	}

	public String getIdNationStr() {
		return String.valueOf(idNation);
	}

	public String getIdSexStr() {
		return String.valueOf(idSex);
	}
	
	public String getIdAddressStr() {
		return String.valueOf(idAddress);
	}
	
	public String getIdBirthStr() {
		return idNum.substring(6, 14);
	}
};
