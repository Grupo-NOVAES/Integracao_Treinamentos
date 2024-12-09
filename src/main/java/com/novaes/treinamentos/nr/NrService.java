package com.novaes.treinamentos.nr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novaes.treinamentos.office.Office;
import com.novaes.treinamentos.office.OfficeRepository;
import com.novaes.treinamentos.usernr.UserNRRepository;

import jakarta.transaction.Transactional;

@Service
public class NrService {
	
	@Autowired
	private NrRepository nrRepository;
	
	@Autowired
	private OfficeRepository officeRepository;
	
	@Autowired
	private UserNRRepository userNrRepository;

	
	public void addNewNr(NR nr) {
		nrRepository.save(nr);
	}
	
	public List<NR> getAllNr(){
		return nrRepository.findAll();
	}
	
	public NR findNrById(Long idNr) {
		return nrRepository.findById(idNr).orElseThrow(NrNotFoundException::new);
	}
	
	public NR findNrByNumber(int nrNumber) {
		return nrRepository.findNrByNumber(nrNumber);
	}
	
	public List<NR> findNrByOffice(Long officeId) {
        return officeRepository.findNrsByOfficeId(officeId);
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
	
	@Transactional
    public void deleteNrById(Long idNr) {
        NR nr = nrRepository.findById(idNr)
                .orElseThrow(NrNotFoundException::new);

        List<Office> offices = officeRepository.findByIdNr(idNr);

        for (Office office : offices) {
            if (office.getListNR().contains(nr)) {
                office.getListNR().remove(nr);
                officeRepository.save(office); 
            }
        }
        
        userNrRepository.deleteUserNrByNrId(idNr);
        nrRepository.delete(nr);
    }
	
	protected boolean validateIfSomethingIsNull(Integer number,String title,String description,String listRequiriments, String workload) {
		boolean somethingIsNull=false;
		if(number == null || number == 0) {
			somethingIsNull=true;
		}
		if(title == null || title.equals("")) {
			somethingIsNull=true;
		}
		if(description == null|| description.equals("")) {
			somethingIsNull=true;
		}
		if(listRequiriments == null || listRequiriments.equals("")) {
			somethingIsNull=true;
		}
		if(workload == null|| workload.equals("")) {
			somethingIsNull=true;
		}
		return somethingIsNull;
	}
	
	
	
}
