package com.novaes.treinamentos.usernr;

public enum Status {
	
	Valida("Valida"),
	Alerta("Alerta"),
	Vencida("Vencida");
	
	String Status;
	
	Status(String status){
		this.Status=status;	}

}
