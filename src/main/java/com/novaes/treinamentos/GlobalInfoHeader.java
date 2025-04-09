package com.novaes.treinamentos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.novaes.treinamentos.alertUserNr.AlertUserNRService;

@ControllerAdvice
public class GlobalInfoHeader {
	
	@Autowired
    private AlertUserNRService alertUserNRService;
	
	@ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("listAlert", alertUserNRService.getAllAlert());
    }

}
