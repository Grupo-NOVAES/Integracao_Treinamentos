package com.novaes.treinamentos.questions;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Questions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String enunciation;
	
	private List<String> listAlternative;
	
	private String correctAnwser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnunciation() {
		return enunciation;
	}

	public void setEnunciation(String enunciation) {
		this.enunciation = enunciation;
	}

	public List<String> getListAlternative() {
		return listAlternative;
	}

	public void setListAlternative(List<String> listAlternative) {
		this.listAlternative = listAlternative;
	}

	public String getCorrectAnwser() {
		return correctAnwser;
	}

	public void setCorrectAnwser(String correctAnwser) {
		this.correctAnwser = correctAnwser;
	}
	
	

}
