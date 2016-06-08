package com.example.OfficeControl.vo;

public class UserInfo {
	public String userName=null;
	public String userPassword=null;
	public int deleteButtonRes;
	public UserInfo( String userName, String userPassword, int deleteButtonRes) {
		super();
		this.userName = userName;
		this.deleteButtonRes = deleteButtonRes;
		this.userPassword=userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public int getDeleteButtonRes() {
		return deleteButtonRes;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setDeleteButtonRes(int deleteButtonRes) {
		this.deleteButtonRes = deleteButtonRes;
	}
}
