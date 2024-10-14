package com.novaes.treinamentos.user;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.novaes.treinamentos.office.Office;
import com.novaes.treinamentos.office.OfficeService;
import com.novaes.treinamentos.usernr.UserNrService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	private final UserNrService userNrService;
	
	private final OfficeService officeService;
	
	private static final String USERHOMEPAGE = "redirect:/user";
	private static final String USERINFOPAGE = "redirect:/user/infoClient/";
	
	public UserController(UserService userService,UserNrService userNrService,OfficeService officeService) {
		this.userService=userService;
		this.userNrService=userNrService;
		this.officeService=officeService;
	}
	
	@GetMapping
	public String allClientPage(Model model) {
		System.out.println("redirecionamento para /user");
		model.addAttribute("listUser", userService.getAllClients());
		model.addAttribute("listOffice",officeService.getAllOffice());
		return "pages/manager/user";
	}
	
	
	@GetMapping("/infoClient/{idUser}")
	public String getInfoCurseCompleteClient(@PathVariable Long idUser,Model model) {
		User user = userService.getUserById(idUser);
		if(user.getActivationDate() != null) {
			ZonedDateTime dateTime = user.getActivationDate().withZoneSameInstant(ZoneId.of("America/Sao_Paulo")).plusMinutes(5);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
			String formattedDate = dateTime.format(formatter);
			model.addAttribute("dateTimeActivation", formattedDate);
		}
			
		model.addAttribute("infoUser",user);
		model.addAttribute("listNrUser" , userNrService.getListNrUserByUser(idUser));
		return "pages/manager/userdata";
	}
	
	@PostMapping
	public String addNewClient(@RequestParam(value = "name" , required = true) String name,
			 @RequestParam(value = "lastname" , required = true) String lastname,
			 @RequestParam(value = "login" , required = true) String login,
			 @RequestParam(value = "password" , required = true) String password,
			 @RequestParam(value = "confirmPassword" , required = true) String confirmPassword,
			 @RequestParam(value = "office" , required = true) String office,
			 @RequestParam(value = "phoneNumber" , required = true) String phoneNumber) {
		
	    if(password.equals(confirmPassword)) {
	    	Office officeFound = officeService.findOfficeByName(office);
	    	User user = userService.createUser(name,lastname,phoneNumber,login,password,Role.USER,officeFound);
		    if(!ObjectUtils.isEmpty(user)) {
		    	userService.addUser(user);
		    	userNrService.vinculedUserToNr(user, user.getOffice());
		    }
	    }
	    return USERHOMEPAGE;
	}

	
	@PostMapping("/updateUser")
	public String updateClient(UserDTO user , Long id) {
		
		userService.updateUser(user, id);
		return USERHOMEPAGE;
	}
	
	@PostMapping("/activeUser/{idUser}")
	public String activeUser(@PathVariable Long idUser) {
	    userService.activateUser(idUser);
	    return USERINFOPAGE+idUser;
	}


	
	
	@PostMapping("/deleteUser")
	public String deleteClient(@RequestParam(value="userId",required = true) Long id) {
		userService.deleteUser(id);
		return USERHOMEPAGE;
	}
	
	

	

}
