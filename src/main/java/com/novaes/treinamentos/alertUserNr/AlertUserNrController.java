package com.novaes.treinamentos.alertUserNr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alert")
public class AlertUserNrController {
	
	@Autowired
	private AlertUserNRService alertUserNRService;
	
	
	@GetMapping("/all")
	public List<AlertUserNR> listAll(){
		return alertUserNRService.getAllAlert();
	}

}
