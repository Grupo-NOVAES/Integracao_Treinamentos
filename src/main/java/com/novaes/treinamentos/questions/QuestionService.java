package com.novaes.treinamentos.questions;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class QuestionService {

	private final QuestionsRepository questionsRepository;
	
	public QuestionService(QuestionsRepository questionsRepository) {
		this.questionsRepository=questionsRepository;
	}
	
	public void AddQuestion(Questions question){
		questionsRepository.save(question);
	}
	
	public void deleteQuestion(Long idQuestion) {
		questionsRepository.deleteById(idQuestion);
	}
	
	
}
