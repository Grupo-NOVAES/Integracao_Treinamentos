package com.novaes.treinamentos.questions;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.novaes.treinamentos.nr.NrService;

@Controller
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
	private final NrService nrService;
	
	public QuestionController(QuestionService questionService,NrService nrService) {
		this.questionService=questionService;
		this.nrService=nrService;
	}
	

	@GetMapping("/{nrNumber}")	
	public String questionByNR(@PathVariable int nrNumber,Model model) {
		model.addAttribute("listQuestions", questionService.getQuestionsByNRNumber(nrNumber));
		return "pages/manager/question";
	}
	
	@GetMapping("/newQuestion")
	public String addNewQuestionPage(Model model) {
		return "pages/manager/newQuestion";
	}
	
	@PostMapping("/newQuestion")
	public String addNewQuestion(@RequestParam(value = "enunciation" , required = true) String enunciation,
								 @RequestParam(value = "anwser1", required = true) String anwser1,
								 @RequestParam(value = "anwser2", required = true) String anwser2,
								 @RequestParam(value = "anwser3", required = true) String anwser3,
								 @RequestParam(value = "anwser4", required = true) String anwser4,
								 @RequestParam(value = "correctAnwser", required = true) String correctAnwser,
								 @RequestParam(value = "nrId", required = true) Long  nrId) {
		
		Questions question = new Questions();
		question.setEnunciation(enunciation);
		question.setNr(nrService.findNrById(nrId));
		
		ArrayList<String> listAnwser = new ArrayList<>();
		listAnwser.add(anwser1);
		listAnwser.add(anwser2);
		listAnwser.add(anwser3);
		listAnwser.add(anwser4);
		question.setListAlternative(listAnwser);
		
		question.setCorrectAnwser(correctAnwser);
		
		questionService.addQuestion(question);
		
		return "redirect:/question/"+nrId;
	}
	
	@PostMapping("/deleteQuestion/{idQuestion}")
	public String deleteQuestion(@PathVariable Long idQuestion) {
		int nrNumber = questionService.getQuestionById(idQuestion).getNr().getNumber();
		questionService.deleteQuestion(idQuestion);
		return "redirect:/question/"+nrNumber;
	}
	

	
	
	
}
