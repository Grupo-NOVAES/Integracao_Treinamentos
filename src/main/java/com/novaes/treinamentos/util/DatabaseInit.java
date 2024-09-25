package com.novaes.treinamentos.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.novaes.treinamentos.user.Role;
import com.novaes.treinamentos.user.User;
import com.novaes.treinamentos.user.UserRepository;

@Component
public class DatabaseInit  implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
			client.setPassword(passwordEncoder.encode("123456"));
			client.setRole(Role.USER);
			userRepository.save(client);
		}
	}
}
