package com.novaes.treinamentos.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.novaes.treinamentos.office.OfficeRepository;
import com.novaes.treinamentos.user.Role;
import com.novaes.treinamentos.user.User;
import com.novaes.treinamentos.user.UserRepository;
import com.novaes.treinamentos.usernr.UserNrService;

@Component
public class DatabaseInit  implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OfficeRepository officeRepository;
	
	private UserNrService userNrService;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public DatabaseInit(UserNrService userNrService) {
		this.userNrService=userNrService;
	}

	@Override
	public void run(String... args) throws Exception {
		insertAdmin();
		insertClient();
		
	}

	private void insertAdmin() {
		User userFound = userRepository.findByLogin("admin@gmail.com");
		if(userFound == null) {
			User user = new User();
			user.setName("administrador");
			user.setLastname("master");
			user.setLogin("admin@gmail.com");
			user.setPassword(passwordEncoder.encode("123456"));
			user.setPhoneNumber("(16) 99738-3588");
			user.setRole(Role.ADMIN);
			user.setEnabled(true);
			userRepository.save(user);
		}
		
	}
	
	private void insertClient() {
		User clientFound = userRepository.findByLogin("client@gmail.com");
		if(clientFound == null) {
			User client = new User();
			client.setName("cliente");
			client.setLastname("teste");
			client.setLogin("client@gmail.com");
			client.setPhoneNumber("(16) 99738-3588");
			client.setPassword(passwordEncoder.encode("123456"));
			client.setRole(Role.USER);
			client.setOffice(officeRepository.findByName("Operador de Maquina Cortadora de Asfalto"));
			userRepository.save(client);
			userNrService.vinculedUserToNr(client, client.getOffice());
		}
	}
}
