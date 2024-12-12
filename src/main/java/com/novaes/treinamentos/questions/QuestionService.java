package com.novaes.treinamentos.questions;

import java.util.List;

import org.springframework.stereotype.Service;

import com.novaes.treinamentos.responses.ResponsesService;

@Service
public class QuestionService {

	private final QuestionsRepository questionsRepository;
	private final ResponsesService responsesService;
	
	public QuestionService(QuestionsRepository questionsRepository,ResponsesService responsesService) {
		this.questionsRepository=questionsRepository;
		this.responsesService=responsesService;
	}
	
	public void addQuestion(Questions question){
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
	
	public void deleteAllQuestionsByNr(Long nrId) {
	    List<Long> questionIds = questionsRepository.findQuestionIdsByNrId(nrId);
	    questionIds.forEach(questionId -> responsesService.deleteByIdQuestion(questionId));
	    questionsRepository.deleteAllQuestionByNr(nrId);
	}
	
	
}
