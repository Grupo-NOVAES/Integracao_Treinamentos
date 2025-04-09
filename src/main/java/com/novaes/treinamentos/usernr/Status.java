package com.novaes.treinamentos.usernr;

public enum Status {
	
	Inconcluida("Inconcluida"),
	Valida("Valida"),
	Alerta("Alerta"),
	Vencida("Vencida"),
	Reavaliacao("Reavaliacao");
	
	String Status;
	
	Status(String status){
		this.Status=status;	}

}
