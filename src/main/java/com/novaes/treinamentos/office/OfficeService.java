package com.novaes.treinamentos.office;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novaes.treinamentos.Nr.NR;

@Service
public class OfficeService {
	
	@Autowired
	private OfficeRepository officeRepository;
	
	public Office findOfficeById(Long idOffice) {
		return officeRepository.findById(idOffice).orElseThrow(OfficeNotFoundException::new);
	}
	
	public List<Office> getAllOffice() {
		return officeRepository.findAll();
	}

	public void linkNrToOffice(Long idOffice,NR nr) {
		Office office = officeRepository.findById(idOffice)
			.orElseThrow(OfficeNotFoundException::new);
		office.addNrToList(nr);
		officeRepository.save(office);
		
	}
	
	public void addNewOffice(String specialization) {
		Office office = new Office();
		office.setSpecialization(specialization);
		officeRepository.save(office);
	}
	
	public void deleteOffice(Long idOffice) {
		officeRepository.deleteById(idOffice);
	}
}
