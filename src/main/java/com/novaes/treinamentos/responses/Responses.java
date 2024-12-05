package com.novaes.treinamentos.responses;

import com.novaes.treinamentos.questions.Questions;
import com.novaes.treinamentos.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Responses {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
	@ManyToOne
    @JoinColumn(name = "question_id")
	private Questions questions;
	
	private String optionAnswered;
	
	public Responses() {}
	
	public Responses(Long id,User user, Questions question,String optionAnswered) {
		this.id=id;
		this.user=user;
		this.questions=question;
		this.optionAnswered=optionAnswered;
	}
	
	public Responses(User user, Questions question,String optionAnswered) {
		this.user=user;
		this.questions=question;
		this.optionAnswered=optionAnswered;
	}

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

	public Questions getQuestion() {
		return questions;
	}

	public void setQuestion(Questions question) {
		this.questions = question;
	}

	public String getOptionAnswered() {
		return optionAnswered;
	}

	public void setOptionAnswered(String optionAnswered) {
		this.optionAnswered = optionAnswered;
	}
	
	
	

}
