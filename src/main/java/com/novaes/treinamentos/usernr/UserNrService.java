package com.novaes.treinamentos.usernr;

import java.util.List;

import org.springframework.stereotype.Service;

import com.novaes.treinamentos.nr.NR;
import com.novaes.treinamentos.office.Office;
import com.novaes.treinamentos.user.User;

@Service
public class UserNrService {
	
	private final UserNRRepository userNrRepository;
	
	public UserNrService(UserNRRepository userNrRepository) {
		this.userNrRepository=userNrRepository;
	}
	
	public void vinculedUserToNr(User user , Office office) {
		for(NR nr : office.getListNR()) {
			UserNR userNr = new UserNR();
			userNr.setUser(user);
			userNr.setNr(nr);
			userNr.setStatus(false);
			
			userNrRepository.save(userNr);
		}
	}
	
	public List<UserNR> getListNrUserByUser (Long userId){
		return userNrRepository.findAllNrUserByUserId(userId);
	}
	
	public List<NR> getListNrByUser(Long userId){
		return userNrRepository.findAllNrByUserId(userId);
	}

}
