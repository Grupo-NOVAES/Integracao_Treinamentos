package com.novaes.treinamentos.office;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novaes.treinamentos.nr.NR;
import com.novaes.treinamentos.nr.NrNotFoundException;
import com.novaes.treinamentos.nr.NrRepository;
import com.novaes.treinamentos.user.UserRepository;

@Service
public class OfficeService {
	
	@Autowired
	private OfficeRepository officeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private NrRepository nrRepository;
	
	public Office findOfficeById(Long idOffice) {
		return officeRepository.findById(idOffice).orElseThrow(OfficeNotFoundException::new);
	}
	public Office findOfficeByName(String officeName) {
		return officeRepository.findByName(officeName);
	}
	public List<Office> getAllOffice() {
		return officeRepository.findAll();
	}

	public void linkNrToOffice(Long idOffice,Long idNr) {
		NR nr = nrRepository.findById(idNr).orElseThrow(NrNotFoundException::new);
		Office office = officeRepository.findById(idOffice)
			.orElseThrow(OfficeNotFoundException::new);
		office.addNrToList(nr);
		officeRepository.save(office);
		
	}
	
	protected void removeNrToOffice(Long idOffice,Long idNr) {
		NR nr = nrRepository.findById(idNr).orElseThrow(NrNotFoundException::new);
		Office office = officeRepository.findById(idOffice)
			.orElseThrow(OfficeNotFoundException::new);
		office.removeNrToList(nr);
		officeRepository.save(office);
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
	
}
