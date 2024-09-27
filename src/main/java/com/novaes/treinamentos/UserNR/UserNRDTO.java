package com.novaes.treinamentos.UserNR;

public class UserNRDTO {
	
	public UserNRDTO() {}
	
	public UserNRDTO(Long id,String nameUser,int numberNR,boolean status) {
		this.id=id;
		this.nameUser=nameUser;
		this.numberNR=numberNR;
		this.status=status;
	}
	
	private Long id;
	private String nameUser;
	private int numberNR;
	private boolean status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public int getNumberNR() {
		return numberNR;
	}
	public void setNumberNR(int numberNR) {
		this.numberNR = numberNR;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

}
