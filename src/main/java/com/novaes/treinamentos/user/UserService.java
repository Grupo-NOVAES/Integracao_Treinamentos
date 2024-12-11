package com.novaes.treinamentos.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	
	public boolean getTypeUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        return userDetails.getAuthorities().stream()
        		.anyMatch(authority -> authority.getAuthority()
        				.equals("ROLE_ADMIN"));
	}
	
	public User getUserLogged() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return (User) authentication.getPrincipal();
	}
	
	protected void addUser(User user) {
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
		userNrRepository.deleteUserNrByUserId(idUser);
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
	
	@Scheduled(fixedRate = 6000000)
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
