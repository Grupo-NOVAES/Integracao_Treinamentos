package com.novaes.treinamentos.user;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.novaes.treinamentos.alertUserNr.AlertUserNRService;
import com.novaes.treinamentos.office.Office;
import com.novaes.treinamentos.office.OfficeService;
import com.novaes.treinamentos.questions.QuestionService;
import com.novaes.treinamentos.questions.Questions;
import com.novaes.treinamentos.responses.Responses;
import com.novaes.treinamentos.responses.ResponsesService;
import com.novaes.treinamentos.usernr.UserNR;
import com.novaes.treinamentos.usernr.UserNrService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	private final UserNrService userNrService;
	
	private final OfficeService officeService;
	
	private final QuestionService questionService;
	
	private final ResponsesService responsesService;
	
	private final AlertUserNRService alertUserNRService;
	
	private static final String USERHOMEPAGE = "redirect:/user/home";
	private static final String USERINFOPAGE = "redirect:/user/infoClient/";
	private static final String ERROR_MESSAGE = "errorMessage";
	
	public UserController(UserService userService,UserNrService userNrService,OfficeService officeService,QuestionService questionService,ResponsesService responsesService,AlertUserNRService alertUserNRService) {
		this.userService=userService;
		this.userNrService=userNrService;
		this.officeService=officeService;
		this.questionService=questionService;
		this.responsesService=responsesService;
		this.alertUserNRService=alertUserNRService;
	}
	
	@GetMapping("/home")
	public String homePage(Model model) {
		model.addAttribute("listAlert",alertUserNRService.getAllAlert());
		if(userService.getTypeUser()) {
			model.addAttribute("listUser", userService.getAllClients());
			model.addAttribute("listOffice",officeService.getAllOffice());
			return "pages/manager/user";
		}else {
			User user = userService.getUserLogged();
	        List<UserNR> listNR = userNrService.getListNrUserByUserWithQuestions(user.getId());
			model.addAttribute("listUserNR", listNR);
			return "pages/client/home";
		}
	}
	
	
	@GetMapping("/infoClient/{idUser}")
	public String getInfoCurseCompleteClient(@PathVariable Long idUser,Model model) {
		User user = userService.getUserById(idUser);
		if(user.getActivationDate() != null) {
			ZonedDateTime dateTime = user.getActivationDate().withZoneSameInstant(ZoneId.of("America/Sao_Paulo")).plusMinutes(60);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
			String formattedDate = dateTime.format(formatter);
			model.addAttribute("dateTimeActivation", formattedDate);
		}
			
		model.addAttribute("infoUser",user);
		model.addAttribute("listNrUser" , userNrService.getListNrUserByUser(idUser));
		return "pages/manager/userdata";
	}
	
	@GetMapping("/downloadCertify/{idUser}/{nrNumber}")
    public ResponseEntity<?> downloadCertify(@PathVariable Long idUser,@PathVariable int nrNumber, Model model) throws Exception {
            return userService.downloadCertificate(idUser, nrNumber);
    }
	
	@GetMapping("/infoClient/{idUser}/{nrNumber}")
	public String getResponseByUserAndNr(@PathVariable Long idUser, @PathVariable int nrNumber, Model model) {
	    List<Questions> questions = questionService.getQuestionsByNRNumber(nrNumber);

	    List<Responses> responses = responsesService.listResposesByUserAndNR(idUser, nrNumber);

	    for (Questions question : questions) {
	        Responses response = responses.stream()
	            .filter(r -> r.getQuestion().getId().equals(question.getId()))
	            .findFirst()
	            .orElse(null);

	        question.setAnwserUser(response != null ? response.getOptionAnswered() : null);
	    }

	    model.addAttribute("nrNumber", nrNumber);
	    model.addAttribute("userId", idUser);
	    model.addAttribute("listQuestions", questions);

	    return "pages/manager/responseList";
	}

	@GetMapping("/reassessmentNr/{idUser}/{nrNumber}")
	public String reassessmentNr(@PathVariable Long idUser,@PathVariable Integer nrNumber) {
		userNrService.nrReassessment(idUser, nrNumber);
		return USERINFOPAGE+idUser;
	}
	
	@PostMapping
	public String addNewClient(@RequestParam(value = "name", required = true) String name,
	                           @RequestParam(value = "lastname", required = true) String lastname,
	                           @RequestParam(value = "login", required = true) String login,
	                           @RequestParam(value = "password", required = true) String password,
	                           @RequestParam(value = "confirmPassword", required = true) String confirmPassword,
	                           @RequestParam(value = "office", required = true) String office,
	                           @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
	                           @RequestParam(value = "cpf", required = true) String cpf,
	                           @RequestParam(value = "rg", required = true) String rg,
	                           RedirectAttributes redirectAttributes) {

	    if (!password.equals(confirmPassword)) {
	        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "As senhas não coincidem!");
	        return USERHOMEPAGE;
	    }
	    if (userService.existsByLogin(login)) {
	    	redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Login já cadastrado!");
	        return USERHOMEPAGE;
	    }
	    try {
	        Office officeFound = officeService.findOfficeByName(office);
	        if (officeFound == null) {
	            redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Cargo inválido!");
	            return USERHOMEPAGE;
	        }
	        
	        UserDTO userDto = new UserDTO();
	        userDto.setName(name);
	        userDto.setLastname(lastname);
	        userDto.setLogin(login);
	        userDto.setPassword(password);
	        
	        User user = userService.createUser(userDto, phoneNumber, cpf, rg, Role.USER, officeFound);

	        if (user != null) {
	        	
	            userService.addUser(user);
	            userNrService.vinculedUserToNr(user, user.getOffice());
	            return USERHOMEPAGE;
	        }

	    } catch (ThisCPFAlreadyExistException e) {
	        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "CPF já cadastrado ou invalido!");
	        return USERHOMEPAGE;
	    } catch (ThisRGAlreadyExistException e) {
	        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "RG já cadastrado ou invalido!");
	        return USERHOMEPAGE;
	    } catch (IllegalArgumentException e) {
	        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, e.getMessage());
	        return USERHOMEPAGE;
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute(ERROR_MESSAGE, "Erro inesperado: " + e.getMessage());
	        return USERHOMEPAGE;
	    }

	    return USERHOMEPAGE;
	}




	
	@PostMapping("/updateUser")
	public String updateClient(UserDTO user , Long id) {
		userService.updateUser(user, id);
		return USERHOMEPAGE;
	}
	
	@PostMapping("/activeUser")
	public String activeUser(@RequestParam(value="idUser") Long idUser) {
	    userService.activateUser(idUser);
	    return USERINFOPAGE+idUser;
	}

	
	@PostMapping("/deleteUser")
	public String deleteClient(@RequestParam(value="userId",required = true) Long id) {
		userService.deleteUser(id);
		return USERHOMEPAGE;
	}
}
