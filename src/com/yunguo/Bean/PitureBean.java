package com.yunguo.Bean;


public class PitureBean {
	private static PitureBean pitureBean;
	
	private String imgfile_path = ""; //��ͼƬ�ļ���ַ
	private String back_imgfile_path = ""; //��ͼƬ�ļ���ַ
	private	String romoteName = ""; //���ļ���
	private	String back_romoteName = ""; //���ļ���
	private	String IdCardNo = "";  //���֤��
	private String UserImage_path = "";//ͷ��·��
	private String UserImage_name = "";//�ļ���
	
	private PitureBean(){
		
	}
	public static PitureBean savafile(){
		
		if(pitureBean == null){
			pitureBean = new PitureBean();
		}
		return pitureBean;
	}
	
	public void setObjectNull(){
		pitureBean = null;
	}
	
	public String getUserImage_path() {
		return UserImage_path;
	}
	public void setUserImage_path(String userImage_path) {
		UserImage_path = userImage_path;
	}
	public String getUserImage_name() {
		return UserImage_name;
	}
	public void setUserImage_name(String userImage_name) {
		UserImage_name = userImage_name;
	}
	public String getImgfile_path() {
		return imgfile_path;
	}
	public void setImgfile_path(String imgfile_path) {
		this.imgfile_path = imgfile_path;
	}
	public String getBack_imgfile_path() {
		return back_imgfile_path;
	}
	public void setBack_imgfile_path(String back_imgfile_path) {
		this.back_imgfile_path = back_imgfile_path;
	}
	public String getRomoteName() {
		return romoteName;
	}
	public void setRomoteName(String romoteName) {
		this.romoteName = romoteName;
	}
	public String getBack_romoteName() {
		return back_romoteName;
	}
	public void setBack_romoteName(String back_romoteName) {
		this.back_romoteName = back_romoteName;
	}
	public String getIdCardNo() {
		return IdCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		IdCardNo = idCardNo;
	}
	
}
