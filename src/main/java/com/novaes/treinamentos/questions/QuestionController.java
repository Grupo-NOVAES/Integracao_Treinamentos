package com.novaes.treinamentos.questions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
	
	public QuestionController(QuestionService questionService) {
		this.questionService=questionService;
	}
	

	//tela que listará as questoes por NR
	@GetMapping("/{nrNumber}")
	public String QuestionByNR(@PathVariable int nrNumber) {
		
		return "question";
	}
	
	@GetMapping("/newQuestion")
	public String addNewQuestionPage() {
		return "newQuestion";
	}
	
	@PostMapping("/newQuestion/{numberNR}")
	public String addNewQuestion(@RequestParam(value = "enunciation" , required = true) String enunciation,
								 @RequestParam(value = "anwser1", required = true) String anwser1,
								 @RequestParam(value = "anwser2", required = true) String anwser2,
								 @RequestParam(value = "anwser3", required = true) String anwser3,
								 @RequestParam(value = "anwser4", required = true) String anwser4,
								 @RequestParam(value = "correctAnwser", required = true) String correctAnwser,
								 @PathVariable Long  numberNR) {
		
		Questions question = new Questions();
		question.setEnunciation(enunciation);
		//question.setNr(NRService.findById(numberNR));
		
		ArrayList<String> listAnwser = new ArrayList<>();
		listAnwser.add(anwser1);
		listAnwser.add(anwser2);
		listAnwser.add(anwser3);
		listAnwser.add(anwser4);
		question.setListAlternative(listAnwser);
		
		question.setCorrectAnwser(correctAnwser);
		
		questionService.AddQuestion(question);
		
		return "redirect:/question/"+numberNR;
	}
	
	@PostMapping("/deleteQuestion/{idQuestion}")
	public void deleteQuestion(@PathVariable Long idQuestion) {
		questionService.deleteQuestion(idQuestion);
	}
	

	
	
	
}
