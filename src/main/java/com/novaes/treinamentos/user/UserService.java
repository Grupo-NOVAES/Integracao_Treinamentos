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

import com.novaes.treinamentos.office.Office;
import com.novaes.treinamentos.responses.ResponsesRepository;
import com.novaes.treinamentos.usernr.UserNRRepository;

@Service
public class UserService {

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserNRRepository userNrRepository;
	
	@Autowired
	private ResponsesRepository responsesRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
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
	
	public boolean verifyCPF(String cpf) {
	    cpf = cpf.replaceAll("\\D", "");
	    
	    if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
	        return false;
	    }

	    try {
	        int soma = 0;
	        for (int i = 0; i < 9; i++) {
	            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
	        }
	        int primeiroDigitoVerificador = 11 - (soma % 11);
	        if (primeiroDigitoVerificador >= 10) {
	            primeiroDigitoVerificador = 0;
	        }

	        soma = 0;
	        for (int i = 0; i < 10; i++) {
	            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
	        }
	        int segundoDigitoVerificador = 11 - (soma % 11);
	        if (segundoDigitoVerificador >= 10) {
	            segundoDigitoVerificador = 0;
	        }

	        return primeiroDigitoVerificador == Character.getNumericValue(cpf.charAt(9)) &&
	               segundoDigitoVerificador == Character.getNumericValue(cpf.charAt(10));
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	

	public String unformatedCPF(String cpf) {
		if (cpf == null) {
			return null;
		}else {
			return cpf.replaceAll("\\D", "");
		}
	}
	
	public String unformatedRG(String rg) {
		if (rg == null) {
			return null;
		}else {
			return rg.replaceAll("\\D", "");
		}
	}
	
	public String formatedCPF(String cpfNumber) {
		if (cpfNumber == null || cpfNumber.length() != 11) {
			return cpfNumber;
		}else {
			return cpfNumber.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
		}
	}
	
	public String formatedRG(String rgNumber) {
		if (rgNumber == null || rgNumber.length() != 9) {
			return rgNumber;
		} else {
			return rgNumber.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{1})", "$1.$2.$3-$4");
		}
	}
	
	public List<User> getUsersByOfficeId(Long officeId) {
        return userRepository.findUsersByOfficeId(officeId);
    }
	
	public boolean existsByLogin(String login) {
	    return userRepository.existsByLogin(login);
	}
	
	protected void addUser(User user) {
		userRepository.save(user);
	}
	
	public User createUser(String name, String lastname, String phoneNumber, String cpf, String rg, String login, String password, Role role, Office office) {
		if (cpf == null || cpf.isEmpty() || rg == null || rg.isEmpty() || name == null || name.isEmpty() || lastname == null || lastname.isEmpty() || login == null || login.isEmpty() || password == null || password.isEmpty()) {
			throw new IllegalArgumentException("Todos os campos são obrigatórios!");
		}
		
		if (userRepository.existsBycpf(cpf) || verifyCPF(cpf)) {
			throw new ThisCPFAlreadyExistException();
		}
		
		if (userRepository.existsByrg(rg)) {
			throw new ThisRGAlreadyExistException();
		}
		
		User user = new User();
		user.setName(name);
		user.setLastname(lastname);
		user.setPhoneNumber(phoneNumber);
		user.setLogin(login);
		user.setCPF(cpf);
		user.setRG(rg);
		user.setRole(role);
		user.setPassword(passwordEncoder.encode(password));
		user.setOffice(office);

		return user;
	}

	
	protected void updateUser(UserDTO user, Long idUser) {
		User userFounded = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
		if(user.getName() != null) {
			userFounded.setName(user.getName());
		}
	}
	
	public User getUserById(Long idUser) {
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
		responsesRepository.deleteAllResponsesByUserId(idUser);
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
	
	@Scheduled(fixedRate = 3600000)
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
