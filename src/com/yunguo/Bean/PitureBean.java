package com.yunguo.Bean;


public class PitureBean {
	private static PitureBean pitureBean;
	
	private String imgfile_path = ""; //正图片文件地址
	private String back_imgfile_path = ""; //反图片文件地址
	private	String romoteName = ""; //正文件名
	private	String back_romoteName = ""; //反文件名
	private	String IdCardNo = "";  //身份证号
	private String UserImage_path = "";//头像路径
	private String UserImage_name = "";//文件名
	
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
