package com.novaes.treinamentos.alertUserNr;

import java.util.List;

import org.springframework.stereotype.Service;

import com.novaes.treinamentos.usernr.Status;

@Service
public class AlertUserNRService {
	
	private AlertUserNrRepository alertRepository;
	
	AlertUserNRService(AlertUserNrRepository alertRepository) {
		this.alertRepository=alertRepository;
	}
	
	public void createNewAlert(String nameUser,int nrNumber,Status status) {
		AlertUserNR alert = new AlertUserNR();
		alert.setNameUser(nameUser);
		alert.setNrNumber(nrNumber);
		alert.setStatus(status);
		alertRepository.save(alert);
	}
	
	public List<AlertUserNR> getAllAlert() {
		return alertRepository.findAll();
	}
	
	public void deleteAlertById(Long id) {
		alertRepository.deleteById(id);
	}
	
	public void deleteAllAlert() {
		alertRepository.deleteAll();
	}

	public void deleteAlert(String username, int nrNumber, Status alerta) {
		alertRepository.deleteAlert(username,nrNumber,alerta);
		
	}
	
	public void deleteAlert(String username, int nrNumber) {
		System.out.println(username+" - "+nrNumber);
		alertRepository.deleteAlert(username,nrNumber);
		
	}

}
