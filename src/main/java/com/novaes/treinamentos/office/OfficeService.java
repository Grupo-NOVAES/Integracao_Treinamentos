package com.novaes.treinamentos.office;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novaes.treinamentos.nr.NR;
import com.novaes.treinamentos.nr.NrNotFoundException;
import com.novaes.treinamentos.nr.NrRepository;
import com.novaes.treinamentos.user.User;
import com.novaes.treinamentos.user.UserRepository;
import com.novaes.treinamentos.user.UserService;
import com.novaes.treinamentos.usernr.UserNR;
import com.novaes.treinamentos.usernr.UserNrService;

@Service
public class OfficeService {
	
	@Autowired
	private OfficeRepository officeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NrRepository nrRepository;
	
	private final UserNrService userNrService;
	
	private final UserService userService;
	
	public OfficeService(UserNrService userNrService,UserService userService) {
		this.userNrService=userNrService;
		this.userService=userService;
	}
	
	public Office findOfficeById(Long idOffice) {
		return officeRepository.findById(idOffice).orElseThrow(OfficeNotFoundException::new);
	}
	public Office findOfficeByName(String officeName) {
		return officeRepository.findByName(officeName);
	}
	public List<Office> getAllOffice() {
		return officeRepository.findAll();
	}
	public List<Office> getOfficesByNrId(Long nrId) {
        return officeRepository.findByNrId(nrId);
    }

	public void linkNrToOffice(Long idOffice, Long idNr) {
	    List<User> users = userService.getUsersByOfficeId(idOffice);

	    NR nr = nrRepository.findById(idNr).orElseThrow(NrNotFoundException::new);
	    Office office = officeRepository.findById(idOffice)
	        .orElseThrow(OfficeNotFoundException::new);

	    office.addNrToList(nr);

	    users.forEach(user -> {
	    	userNrService.vinculeOneNrToUser(user,nr);
	    });

	    officeRepository.save(office);
	}

	
	protected void removeNrToOffice(Long idOffice, Long idNr) {
	    Office office = officeRepository.findById(idOffice)
	        .orElseThrow(OfficeNotFoundException::new);

	    office.deleteNrFromList(idNr);
	    officeRepository.save(office);

	    List<User> users = userRepository.findUsersByOfficeId(idOffice);

	    users.forEach(user -> {
	        userNrService.deleteUserNRByNrId(idNr);
	    });
	}
	
	public void addNewOffice(String specialization) {
		Office office = new Office();
		office.setSpecialization(specialization);
		officeRepository.save(office);
	}
	
	public void deleteOffice(Long idOffice) {
		userRepository.deleteUserByOfficeId(idOffice);
		officeRepository.deleteById(idOffice);
	}
	
	public void removeNrFromAllOffices(Long nrId) {
        List<Office> offices = getOfficesByNrId(nrId);
        for (Office office : offices) {
            office.deleteNrFromList(nrId); 
            officeRepository.save(office); 
        }
    }
	
	
}
