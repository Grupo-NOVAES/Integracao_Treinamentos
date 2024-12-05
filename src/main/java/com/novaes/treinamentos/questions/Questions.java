package com.novaes.treinamentos.questions;

import java.util.List;

import com.novaes.treinamentos.nr.NR;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Questions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int questionNumber;

	private String enunciation;
	
	@ManyToOne
	private NR nr;
	
	@Column(length = 1280000000)
    @Lob
	private List<String> listAlternative;
	
	private String answserUser;
	
	private String correctAnswer;
	
	public String getAnwserUser() {
		return answserUser;
	}

	public void setAnwserUser(String answserUser) {
		this.answserUser = answserUser;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NR getNr() {
		return nr;
	}

	public void setNr(NR nr) {
		this.nr = nr;
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
		return correctAnswer;
	}

	public void setCorrectAnwser(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	public int getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}
	
	

}
