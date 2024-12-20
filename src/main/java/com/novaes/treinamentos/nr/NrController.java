package com.novaes.treinamentos.nr;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.novaes.treinamentos.office.OfficeService;
import com.novaes.treinamentos.questions.QuestionService;
import com.novaes.treinamentos.usernr.UserNrService;

@Controller
@RequestMapping("/Nr")
public class NrController {
	
	
	
	private final NrService nrService;
	private final QuestionService questionService;
	private final OfficeService officeService;
	private final UserNrService userNrService;
	private static final String NRHOMEPAGE = "redirect:/Nr";
	
	public NrController(NrService nrService,QuestionService questionService,UserNrService userNrService,OfficeService officeService) {
		this.nrService=nrService;
		this.questionService=questionService;
		this.userNrService=userNrService;
		this.officeService=officeService;
	}

	
	@GetMapping
	public String allNrPage(Model model) {
		model.addAttribute("listNr", nrService.getAllNr());
		return "pages/manager/nrList";
	}
	
	@GetMapping("/{idNr}")
	public String nrInfo(@PathVariable Long idNr,Model model) {
		NR nr = nrService.findNrById(idNr);
		model.addAttribute("InfoNr", nr);
		
		String iconPath = "/img/nr/NR" + String.format("%02d", nr.getNumber()) + ".png";		
		model.addAttribute("iconPath", iconPath);
		
		return "pages/manager/nrInfo";
	}
	
	@GetMapping("/{nrNumber}/question")	
	public String questionByNR(@PathVariable int nrNumber,Model model) {
		model.addAttribute("listQuestions", questionService.getQuestionsByNRNumber(nrNumber));
		return "pages/manager/question";
	}
	
	@PostMapping
	public String addRequirimentsWithNr(@RequestParam(value="requiriment", required = true) String requiriment,
										@RequestParam(value="idNr", required = true) Long idNr)	{
		nrService.addRequirimentInNr(idNr , requiriment);
		return NRHOMEPAGE;
	}
	
	@PostMapping("/newNr")
	public String newNr(@RequestParam(value = "number", required = true) Integer number,
	                    @RequestParam(value = "title", required = true) String title,
	                    @RequestParam(value = "description", required = true) String description,
	                    @RequestParam(value = "listRequiriments", required = true) String listRequiriments,
	                    @RequestParam(value = "videoURL", required = true) String videoURL,
	                    @RequestParam(value = "workload", required = true) String workload) {
	    if (!nrService.validateIfSomethingIsNull(number, title, description, listRequiriments, workload)) {
	        NR nr = new NR();
	        nr.setNumber(number);
	        nr.setTitle(title);
	        nr.setDescription(description);
	        String embedUrl = nrService.transformToEmbedUrl(videoURL);
	        nr.setVideoUrl(embedUrl);

	        List<String> requirementsList = Arrays.asList(listRequiriments.split("\\r?\\n"));
	        nr.setListRequiriments(requirementsList);

	        nr.setWorkload(workload);
	        nrService.addNewNr(nr);
	    } else {
	    	throw new IllegalArgumentException("Some value is null");
	    }

	    return NRHOMEPAGE;
	}

	
	@PostMapping("/updateNr/{idNr}")
	public String updateNr(@PathVariable Long idNr,
						   @RequestParam(value = "numero",required = true) Integer number,
						   @RequestParam(value = "title" , required = true) String title,
						   @RequestParam(value = "description" , required = true) String description,
						   @RequestParam(value = "listRequiriments" , required = true) List<String> listRequiriments,
						   @RequestParam(value = "workload" , required = true) String workload) {
		NR nr = new NR();
		nr.setNumber(number);
		nr.setTitle(title);
		nr.setDescription(description);
		nr.setListRequiriments(listRequiriments);
		nr.setWorkload(workload);
		
		nrService.updateNr(idNr, nr);
		return NRHOMEPAGE;
	}
	
	@PostMapping("/deleteNr")
	public String deleteNr(@RequestParam Long idNr) {
		userNrService.deleteUserNRByNrId(idNr);
		officeService.removeNrFromAllOffices(idNr);
		
		questionService.deleteAllQuestionsByNr(idNr);
		nrService.deleteNrById(idNr);
		return NRHOMEPAGE;
	}
	
	
	
	
}
