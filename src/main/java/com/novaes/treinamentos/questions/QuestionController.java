package com.novaes.treinamentos.questions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.novaes.treinamentos.nr.NR;
import com.novaes.treinamentos.nr.NrService;
import com.novaes.treinamentos.responses.ResponsesService;
import com.novaes.treinamentos.user.User;
import com.novaes.treinamentos.user.UserService;
import com.novaes.treinamentos.usernr.UserNR;
import com.novaes.treinamentos.usernr.UserNrService;

@Controller
@RequestMapping("/question")
public class QuestionController {
	
	private final QuestionService questionService;
	private final NrService nrService;
	private final UserNrService userNrService;
	private final UserService userService;
	private final ResponsesService responsesService;
	private static final String NRHOMEPAGE = "redirect:/Nr";
	private static final String NR_NUMBER_PARAM = "nrNumber";
	private static final String USER_ID_PARAM = "userId";
	
	public QuestionController(QuestionService questionService,NrService nrService,UserNrService userNrService,UserService userService,ResponsesService responsesService) {
		this.questionService=questionService;
		this.nrService=nrService;
		this.userNrService=userNrService;
		this.userService=userService;
		this.responsesService=responsesService;
	}
	
	
	@GetMapping("/player/{nrNumber}")
	public String playerVideo(@PathVariable int nrNumber,Model model) {
		model.addAttribute(NR_NUMBER_PARAM, nrNumber);
		model.addAttribute("videoURL", nrService.findNrByNumber(nrNumber).getVideoUrl());
		return "pages/client/playerVideo";
	}

	@GetMapping("/{nrNumber}")
	public String questionByNR(@PathVariable int nrNumber, Model model) {
		User user =  userService.getUserLogged();
	    model.addAttribute(NR_NUMBER_PARAM, nrNumber);
	    model.addAttribute(USER_ID_PARAM, userService.getUserLogged().getId());
	    model.addAttribute("username",user.getName()+" "+user.getLastname());
	    model.addAttribute("listQuestions", questionService.getQuestionsByNRNumber(nrNumber));
	    
	    return "pages/client/question";
	}

	
	@GetMapping("/newQuestion/{nrNumber}")
	public String addNewQuestionPage(Model model,@PathVariable int nrNumber) {
		model.addAttribute("nrData" , nrService.findNrByNumber(nrNumber));
		return "pages/manager/newQuestion";
	}
	
	@PostMapping("/newQuestion")
	public String addNewQuestion(@RequestParam(value = "enunciation", required = true) String enunciation,
	                             @RequestParam(value = "anwser1", required = true) String anwser1,
	                             @RequestParam(value = "anwser2", required = true) String anwser2,
	                             @RequestParam(value = "anwser3", required = true) String anwser3,
	                             @RequestParam(value = "anwser4", required = true) String anwser4,
	                             @RequestParam(value = "correctAnwser", required = true) int correctAnwser,
	                             @RequestParam(value = "nrId", required = true) Long nrId) {
	    int nrNumber = 0;
	    Questions question = new Questions();
	    question.setEnunciation(enunciation);
	    
	    NR nr = nrService.findNrById(nrId);
	    question.setNr(nr);
	    nrNumber = nr.getNumber();        
	    
	    ArrayList<String> listAnwser = new ArrayList<>();
	    listAnwser.add(anwser1);
	    listAnwser.add(anwser2);
	    listAnwser.add(anwser3);
	    listAnwser.add(anwser4);
	    question.setListAlternative(listAnwser);
	    
	    if (correctAnwser >= 1 && correctAnwser <= 4) {
	        String correctAnswerText = listAnwser.get(correctAnwser - 1);
	        question.setCorrectAnwser(correctAnswerText); 
	    } else {
	        throw new IllegalArgumentException("Número da resposta correta inválido: " + correctAnwser);
	    }

	    questionService.addQuestion(question);
	    
	    return "redirect:/Nr/" + nrNumber + "/question";
	}

	@RequestMapping("/submitResponses")
	public String submitResponses(@RequestParam Map<String, String> responses, Model model, RedirectAttributes redirectAttributes) {
	    int correctCount = 0;
	    int totalQuestions = 0;
	    List<String[]> feedbackList = new ArrayList<>();

	    Long userId = Long.parseLong(responses.get(USER_ID_PARAM));
	    Integer nrNumber = Integer.parseInt(responses.get(NR_NUMBER_PARAM));

	    UserNR userNr = userNrService.findByUserIdAndNrNumber(userId, nrNumber);

	    responses.entrySet().removeIf(entry -> !entry.getKey().startsWith("question-"));
	    if (responses.size() > 0) {
	        for (Map.Entry<String, String> entry : responses.entrySet()) {
	            String questionIdStr = entry.getKey().replace("question-", "");

	            try {
	                Long questionId = Long.parseLong(questionIdStr);

	                Questions question = questionService.getQuestionById(questionId);
	                responsesService.addNewResponse(questionId, userId, entry.getValue());

	                if (question.getCorrectAnwser().equals(entry.getValue())) {
	                    correctCount++;
	                }

	                feedbackList.add(new String[]{
	                    question.getEnunciation(),
	                    entry.getValue(),
	                    question.getCorrectAnwser()
	                });

	                totalQuestions++;
	            } catch (NumberFormatException e) {
	                throw new IllegalArgumentException("Some value is not correct type");
	            }
	        }
	    }


	    double score = (double) correctCount / totalQuestions;
	    System.out.println("Pontuação: " + score);

	    if (score >= 0.6) {

	        userNr.setStatus(true);
	        userNrService.updateUserNR(userNr);
	        model.addAttribute("sendEmail", true); 
	    } else {
	        model.addAttribute("sendEmail", false); 
	        redirectAttributes.addFlashAttribute("errorMessage", "Você não atingiu a pontuação mínima de 60%. Por favor, refaça o questionário.");
	        return "redirect:/question/"+nrNumber;
	    }

	    model.addAttribute("correctCount", correctCount);
	    model.addAttribute("totalQuestions", totalQuestions);
	    model.addAttribute("feedbackList", feedbackList);
	    model.addAttribute(USER_ID_PARAM, userService.getUserLogged().getId());
	    model.addAttribute("username",userService.getUserById(userId).getName()+" "+userService.getUserById(userId).getLastname());
	    model.addAttribute(NR_NUMBER_PARAM, nrNumber);
	    
	    return "pages/thanks";
	}




	
	@PostMapping("/deleteQuestion")
	public String deleteQuestion(@RequestParam Long idQuestion) {
		responsesService.deleteByIdQuestion(idQuestion);
		questionService.deleteQuestion(idQuestion);
		return NRHOMEPAGE;	
	}
	

	
	
	
}
