package com.novaes.treinamentos.Nr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NrService {
	
	@Autowired
	private NrRepository nrRepository;

	
	public void addNewNr(NR nr) {
		nrRepository.save(nr);
	}
	
	public List<NR> getAllNr(){
		return nrRepository.findAll();
	}
	
	public NR findNrById(Long idNr) {
		return nrRepository.findById(idNr).orElseThrow(NrNotFoundException::new);
	}
	
	public void updateNr(Long idNr,NR nr) {
		NR nrFounded = nrRepository.findById(idNr).orElseThrow(NrNotFoundException::new);
		nrFounded.setTitle(nr.getTitle());
		nrFounded.setDescription(nr.getDescription());
		nrFounded.setListQuestions(nr.getListQuestions());
		nrFounded.setNumber(nr.getNumber());
		nrFounded.setWorkload(nr.getWorkload());
		nrRepository.save(nrFounded);
	}
	
	public void addRequirimentInNr(Long idNr , String requiriment) {
		NR nrFounded = nrRepository.findById(idNr).orElseThrow(NrNotFoundException::new);
		nrFounded.addRequirimentInList(requiriment);
		nrRepository.save(nrFounded);
	}
	
	public void deleteNr(Long idNr) {
		nrRepository.deleteById(idNr);
	}
	
	protected boolean validateIfSomethingIsNull(Integer number,String title,String description,List<String> listRequiriments, String workload) {
		boolean somethingIsNull=false;
		if(number == null || number == 0) {
			somethingIsNull=true;
		}
		if(title == null || title == "") {
			somethingIsNull=true;
		}
		if(description == null|| description == "") {
			somethingIsNull=true;
		}
		if(listRequiriments.isEmpty()) {
			somethingIsNull=true;
		}
		if(workload == null|| workload == "") {
			somethingIsNull=true;
		}
		return somethingIsNull;
	}
	
	
	
}
