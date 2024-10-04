package com.novaes.treinamentos.office;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.novaes.treinamentos.nr.NrService;

@Controller
@RequestMapping("/office")
public class OfficeController {
	
	private OfficeService officeService;
	private NrService nrService;
	private static final String OFFICEHOMEPAGE = "redirect:/office";
	
	public OfficeController(OfficeService officeService,NrService nrService) {
		this.officeService=officeService;
		this.nrService=nrService;
	}
	
	@GetMapping
	public String allOffice(Model model) {
		model.addAttribute("listOffice", officeService.getAllOffice());
		return "pages/manager/office";
	}
	
	@GetMapping("/{idOffice}")
	public String officeById(@PathVariable Long idOffice,Model model) {
		model.addAttribute("officeData", officeService.findOfficeById(idOffice));
		return "pages/manager/officeInfo";
	}
	
	@PostMapping("/linkNr")
	public String linkNrToOffice(@RequestParam Long idOffice,@RequestParam Long idNr) {
		officeService.linkNrToOffice(idOffice, nrService.findNrById(idNr));
		return OFFICEHOMEPAGE;
	}
	
	@PostMapping("/newOffice")
	public String addNewOffice(@RequestParam(value = "specialization" , required = true) String specialization) {
		officeService.addNewOffice(specialization);
		return OFFICEHOMEPAGE;
	}
	
	@PostMapping("/deleteOffice/{idOffice}")
	public String deleteOffice(@PathVariable Long idOffice) {
		officeService.deleteOffice(idOffice);
		return OFFICEHOMEPAGE;
	}

}
