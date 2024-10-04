package com.novaes.treinamentos.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.novaes.treinamentos.UserNR.UserNrService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	private final UserNrService userNrService;
	
	public UserController(UserService userService,UserNrService userNrService) {
		this.userService=userService;
		this.userNrService=userNrService;
	}
	
	@GetMapping
	public String AllClientPage(Model model) {
		model.addAttribute("listUser", userService.getAllClients());
		return "pages/manager/user";
	}
	
	
	@GetMapping("/infoClient/{idUser}")
	public String getInfoCurseCompleteClient(@PathVariable Long idUser,Model model) {
		model.addAttribute("infoUser", userService.getUserById(idUser));
		model.addAttribute("listNrUser" , userNrService.getListNrByUser(idUser));
		return "pages/manager/userdata";
	}
	
	@PostMapping
	public String addNewClient(@RequestParam("user") User user) {
		userService.addUser(user);
		if(user.getOffice() != null) {
			userNrService.vinculedUserToNr(user, user.getOffice());
		}
		return "redirect:/user";
	}
	
	@PostMapping("/updateUser")
	public String updateClient(UserDTO user , Long id) {
		
		userService.updateUser(user, id);
		return "redirect:/user";
	}
	
	@PostMapping("/activeUser/{idUser}")
	public ModelAndView activeUser(@PathVariable Long idUser) {
	    userService.activateUser(idUser);
	    ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/pages/manager/user");
		return modelAndView;
	}

	
	
	@PostMapping("/deleteUser")
	public String deleteClient(Long id) {
		userService.deleteUser(id);
		return "redirect:/user";
	}
	

	

}
