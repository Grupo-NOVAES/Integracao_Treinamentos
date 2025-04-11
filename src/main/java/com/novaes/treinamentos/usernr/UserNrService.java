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
import com.novaes.treinamentos.responses.ResponsesService;
import com.novaes.treinamentos.user.User;

@Service
public class UserNrService {
	
	private final UserNRRepository userNrRepository;
	
	private final AlertUserNRService alertUserNRService;
	
	private final ResponsesService responsesService;
	
	public UserNrService(UserNRRepository userNrRepository,AlertUserNRService alertUserNRService,ResponsesService responsesService) {
		this.userNrRepository=userNrRepository;
		this.alertUserNRService=alertUserNRService;
		this.responsesService=responsesService;
	}
	
	public void vinculedUserToNr(User user , Office office) {
		office.getListNR().forEach(nr -> {
            UserNR userNr = new UserNR();
            userNr.setUser(user);
            userNr.setNr(nr);
            userNr.setStatus(Status.Inconcluida);

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
	
	
	public void nrReassessment(Long idUser,int nrNumber) {
		UserNR userNr = userNrRepository.findByUserIdAndNrNumber(idUser, nrNumber);
		userNr.setDate(null);
		userNr.setDateValidate(null);
		userNr.setStatus(Status.Reavaliacao);
		alertUserNRService.deleteAlert(userNr.getUser().getUsername(), nrNumber);
		responsesService.deleteResponsesByUser(idUser);
	}
	
	
	
	@Scheduled(cron = "*/10 * * * * *")
	//@Scheduled(cron = "0 0 0 1 * ?")
	public void verificarStatusUserNRComNotificacoes() {
	    LocalDate hoje = LocalDate.now();
	    List<UserNR> todosUserNR = userNrRepository.findUserNrWithDateValidate();

	    for (UserNR user : todosUserNR) {
	    	System.out.println("name: "+user.getUser().getName());
	        LocalDate validade = user.getDateValidate();
	        Status statusAtual = user.getStatus();

	        if (validade == null) continue;

	        if (validade.isAfter(hoje) && validade.isBefore(hoje.plusMonths(1))) {
	            if (statusAtual == Status.Valida) {
	                user.setStatus(Status.Alerta);
	                alertUserNRService.createNewAlert(
	                    user.getUser().getUsername(),user.getUser().getId(), user.getNr().getNumber(), Status.Alerta
	                );
	                userNrRepository.save(user);
	            }
	        }

	        else if (validade.isBefore(hoje)) {
	            if (statusAtual == Status.Alerta) {
	                user.setStatus(Status.Vencida);
	                alertUserNRService.deleteAlert(
	                    user.getUser().getUsername(), user.getNr().getNumber(), Status.Alerta
	                );
	                alertUserNRService.createNewAlert(
	                    user.getUser().getUsername(),user.getUser().getId(), user.getNr().getNumber(), Status.Vencida
	                );
	                userNrRepository.save(user);
	            }
	            else if (statusAtual == Status.Valida) {
	                user.setStatus(Status.Vencida);
	                alertUserNRService.createNewAlert(
	                    user.getUser().getUsername(),user.getUser().getId(), user.getNr().getNumber(), Status.Vencida
	                );
	                userNrRepository.save(user);
	            }
	        }

	        else if (validade.isAfter(hoje.plusMonths(1))) {
	            if (statusAtual == Status.Alerta || statusAtual == Status.Vencida) {
	                user.setStatus(Status.Valida);
	                alertUserNRService.deleteAlert(
	                    user.getUser().getUsername(), user.getNr().getNumber(), statusAtual
	                );
	                userNrRepository.save(user);
	            }
	        }
	    }
	}



}
