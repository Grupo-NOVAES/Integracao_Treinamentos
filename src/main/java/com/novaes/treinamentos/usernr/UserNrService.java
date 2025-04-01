package com.novaes.treinamentos.usernr;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.novaes.treinamentos.alertUserNr.AlertUserNR;
import com.novaes.treinamentos.alertUserNr.AlertUserNRService;
import com.novaes.treinamentos.alertUserNr.AlertUserNrRepository;
import com.novaes.treinamentos.nr.NR;
import com.novaes.treinamentos.office.Office;
import com.novaes.treinamentos.user.User;

@Service
public class UserNrService {
	
	private final UserNRRepository userNrRepository;
	
	private final AlertUserNRService alertUserNRService;
	
	public UserNrService(UserNRRepository userNrRepository,AlertUserNRService alertUserNRService) {
		this.userNrRepository=userNrRepository;
		this.alertUserNRService=alertUserNRService;
	}
	
	public void vinculedUserToNr(User user , Office office) {
		office.getListNR().forEach(nr -> {
            UserNR userNr = new UserNR();
            userNr.setUser(user);
            userNr.setNr(nr);
            userNr.setStatus(Status.Valida);

            userNrRepository.save(userNr);
        });
	}
	
	public void vinculeOneNrToUser(User user , NR nr) {
		UserNR userNR = new UserNR();
		userNR.setNr(nr);
		userNR.setUser(user);
		userNR.setStatus(Status.Valida);
		userNrRepository.save(userNR);
	}
	
	public UserNR findByUserIdAndNrNumber(Long userId, int nrNumber) {
        return userNrRepository.findByUserIdAndNrNumber(userId, nrNumber);
    }

    public void updateUserNR(UserNR userNR) {
        userNrRepository.save(userNR);
    }
	
	public List<UserNR> getListNrUserByUser (Long userId){
		return userNrRepository.findAllNrUserByUserId(userId);
	}
	
	public List<NR> getListNrByUser(Long userId){
		return userNrRepository.findAllNrByUserId(userId);
	}
	
	public List<UserNR> getListNrUserByUserWithQuestions(Long userId) {
        List<UserNR> userNRList = getListNrUserByUser(userId);

        return userNRList.stream()
                .filter(userNR -> userNR.getNr().getListQuestions() != null && !userNR.getNr().getListQuestions().isEmpty())
                .toList();
    }
	
	public void deleteUserNRByNrId(Long nrId) {
		userNrRepository.deleteUserNrByNrId(nrId);
	}
	
	@Scheduled(cron = "0 0 0 1 * ?")
	public List<UserNR> VerifyIfSomeNRiSInvalidWithOutTime() {
		List<UserNR> userNrList = new ArrayList<>();
		userNrRepository.findAll().forEach(user -> {
			if(user.getDateValidate() != null) {
				if(user.getDateValidate().isBefore(LocalDate.now().minusMonths(1))) {
					System.out.println("Mudando status para em Alerta!");
					user.setStatus(Status.Alerta);
				}else if(user.getDateValidate().isBefore(LocalDate.now())) {
					System.out.println("Mudando status para Vencido!");
					user.setStatus(Status.Vencida);
				}
				
				alertUserNRService.createNewAlert(user.getUser().getUsername(), user.getNr().getNumber(), user.getStatus());
				userNrList.add(user);
			}
		});
		
		return userNrList;
	}

}
