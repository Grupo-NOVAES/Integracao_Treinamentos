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
	
	public List<Questions> getQuestionsByNRNumber(int nrNumber){
		return questionsRepository.getByNr(nrNumber);
	}
	
	public Questions getQuestionById(Long idQuestion) {
		return questionsRepository.findById(idQuestion).orElseThrow(QuestionNotFoundException::new);
	}
	
	
}
