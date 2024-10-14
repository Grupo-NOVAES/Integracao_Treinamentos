package com.novaes.treinamentos.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import com.novaes.treinamentos.office.Office;
import com.novaes.treinamentos.usernr.UserNRRepository;

@Service
public class UserService {

	
	private final UserRepository userRepository;
	
	private final UserNRRepository userNrRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository,UserNRRepository userNrRepository) {
		this.userRepository=userRepository;
		this.userNrRepository=userNrRepository;
	}
	
	protected void addUser(User user) {
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		userRepository.save(user);
	}
	
	protected User createUser(String name , String lastname,String phoneNumber,String login,String password,Role role,Office office) {
		User user = new User();
		if(!name.equals("")) {
			user.setName(name);
		}
		if(!lastname.equals("")) {
			user.setLastname(lastname);
		}
		if(!phoneNumber.equals("")) {
			user.setPhoneNumber(phoneNumber);
		}
		if(!login.equals("")) {
			user.setLogin(login);
		}
		user.setRole(role);
		if(!password.equals("")) {
			user.setPassword(passwordEncoder.encode(password));
		}
		if(!ObjectUtils.isEmpty(office)) {
			user.setOffice(office);
		}
		
		return user;
	}
	
	protected void updateUser(UserDTO user, Long idUser) {
		User userFounded = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
		if(user.getName() != null) {
			userFounded.setName(user.getName());
		}
	}
	
	protected User getUserById(Long idUser) {
		return userRepository.findById(idUser).orElseThrow(UserNotFoundException :: new);
	}
	protected User getUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}
	
	protected List<User> getAllClients() {
		return userRepository.findByRole(Role.USER);
	}
	
	protected void deleteUser(Long idUser) {
		userRepository.deleteById(idUser);
	}
	
	protected void activateUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
        
        if (user.getRole() == Role.USER) {
            user.activate();
            userRepository.save(user);
        } else {
            throw new OnlyClientUsersCanBeActivatedException();
        }
    }
	
	@Scheduled(fixedRate = 60000)
    protected void deactivateExpiredAccounts() {
        List<User> clients = userRepository.findByRole(Role.USER); 
        for (User user : clients) {
            if (user.isEnabled() && !user.isActive()) { 
                user.deactivate();
                userRepository.save(user);
            }
        }
    }
	
	

}
