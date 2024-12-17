package com.novaes.treinamentos.responses;

import java.util.List;

import org.springframework.stereotype.Service;

import com.novaes.treinamentos.questions.QuestionNotFoundException;
import com.novaes.treinamentos.questions.Questions;
import com.novaes.treinamentos.questions.QuestionsRepository;
import com.novaes.treinamentos.user.User;
import com.novaes.treinamentos.user.UserNotFoundException;
import com.novaes.treinamentos.user.UserRepository;

@Service
public class ResponsesService {
	
	private ResponsesRepository responsesRepository;
	private UserRepository userRepository;
	private QuestionsRepository questionRepository;
	
	ResponsesService(ResponsesRepository responsesRepository,UserRepository userRepository,QuestionsRepository questionRepository){
		this.responsesRepository=responsesRepository;
		this.userRepository=userRepository;
		this.questionRepository=questionRepository;
	}
	
	public List<Responses> listResposesByUserAndNR(Long userId,int nrNumber){
		return responsesRepository.findResposesByUserAndNrNumber(userId,nrNumber);
	}

	public List<Responses> listResponsesByQuestion(Long idQuestion){
		return responsesRepository.findResponsesByQuestionId(idQuestion);
	}
	
	public List<Responses> listResponsesByUserAndQuestion(Long userId, Long idQuestion){
		return responsesRepository.findResponsesByUserAndQuestion(userId,idQuestion);
	}
	
	public void addNewResponse(Long idQuestion, Long idUser, String optionAnswered) {        
	    responsesRepository.deleteByUserIdAndQuestionId(idUser, idQuestion);

	    Responses newResponse = new Responses();
	    newResponse.setOptionAnswered(optionAnswered);
	    newResponse.setUser(userRepository.findById(idUser).orElseThrow(UserNotFoundException::new));
	    newResponse.setQuestion(questionRepository.findById(idQuestion).orElseThrow(QuestionNotFoundException::new));

	    responsesRepository.save(newResponse);
	}



	
	public void deleteRespnsesById(Long idResponse) {
		responsesRepository.deleteById(idResponse);
	}
	
	public void deleteResponsesByUser(Long idUser) {
		responsesRepository.deleteByUserId(idUser);
	}
	
	public void deleteByIdQuestion(Long idQuestion) {
		responsesRepository.deleteByQuestionsId(idQuestion);
	}
}
