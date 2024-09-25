package com.novaes.treinamentos.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping
	public ModelAndView AllClientPage() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/pages/manager/user");
		return modelAndView;
	}
	
	
	@GetMapping("/infoClient")
	public String getInfoCurseCompleteClient() {
		return "infoUser";
	}
	
	@PostMapping
	public String addNewClient(@RequestParam("user") User user) {
		userService.addUser(user);
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
