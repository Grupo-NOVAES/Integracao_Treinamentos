package com.novaes.treinamentos.usernr;

import java.time.LocalDate;
import java.util.Date;

import com.novaes.treinamentos.nr.NR;
import com.novaes.treinamentos.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class UserNR {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private NR nr;
	
	private LocalDate dateOfRealization;
	
	private LocalDate dateValidate;
	
	private Status status;
	
	private boolean isConcluded;

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public NR getNr() {
		return nr;
	}

	public void setNr(NR nr) {
		this.nr = nr;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public LocalDate getDateResponse() {
		return this.dateOfRealization;
	}
	
	public void setDate(LocalDate dateOfRealization) {
		this.dateOfRealization=dateOfRealization;
	}
	
	public LocalDate getDateOfRealization() {
		return dateOfRealization;
	}

	public void setDateOfRealization(LocalDate dateOfRealization) {
		this.dateOfRealization = dateOfRealization;
	}

	public LocalDate getDateValidate() {
		return dateValidate;
	}

	public void setDateValidate(LocalDate dateValidate) {
		this.dateValidate = dateValidate;
	}

	public boolean isConcluded() {
		return isConcluded;
	}

	public void setConcluded(boolean isConcluded) {
		this.isConcluded = isConcluded;
	}
	
	

}
