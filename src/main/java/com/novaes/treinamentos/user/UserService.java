package com.novaes.treinamentos.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	
	private final UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	protected void addUser(User user) {
		String encodePassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		userRepository.save(user);
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
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getRole() == Role.USER) {
            user.activate();
            userRepository.save(user);
        } else {
            throw new RuntimeException("Only CLIENT users can be activated");
        }
    }
	
	@Scheduled(fixedRate = 60000)
    protected void deactivateExpiredAccounts() {
        List<User> clients = userRepository.findByRole(Role.USER); 
        System.out.println("verificando usuarios ativados...");
        for (User user : clients) {
            if (user.isEnabled() && !user.isActive()) { 
            	System.out.println(user.toString());
                user.deactivate();
                userRepository.save(user);
            }
        }
    }

}
