package com.novaes.treinamentos.user;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.novaes.treinamentos.office.Office;
import com.novaes.treinamentos.responses.ResponsesRepository;
import com.novaes.treinamentos.usernr.Status;
import com.novaes.treinamentos.usernr.UserNR;
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

	        System.out.println("CPF: " + cpf);
	        System.out.println("Primeiro Dígito Calculado: " + primeiroDigitoVerificador);
	        System.out.println("Segundo Dígito Calculado: " + segundoDigitoVerificador);

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
	    } else {
	        return cpfNumber.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d)", "$1.$2.$3-$4");
	    }
	}
	
	public String formatedRG(String rgNumber) {
	    if (rgNumber == null || rgNumber.length() != 9) {
	        return rgNumber;
	    } else {
	        return rgNumber.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d)", "$1.$2.$3-$4");
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
	
	public User createUser(UserDTO usetDto, String phoneNumber, String cpf, String rg, Role role, Office office) {
		if (cpf == null || cpf.isEmpty() || rg == null || rg.isEmpty() || usetDto.getName() == null || usetDto.getName().isEmpty() || usetDto.getLastname() == null || usetDto.getLastname().isEmpty() || usetDto.getLogin() == null || usetDto.getLogin().isEmpty() || usetDto.getPassword() == null || usetDto.getPassword().isEmpty()) {
			throw new IllegalArgumentException("Todos os campos são obrigatórios!");
		}

		User user = new User();
		user.setName(usetDto.getName());
		user.setLastname(usetDto.getLastname());
		user.setPhoneNumber(phoneNumber);
		user.setLogin(usetDto.getLogin());
		user.setCPF(cpf);
		user.setRG(rg);
		user.setRole(role);
		user.setPassword(passwordEncoder.encode(usetDto.getPassword()));
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
	
	
	
	public Resource selectModelNR(int nrNumber) {
		
		return new ClassPathResource("models/"+"CertificateNR"+nrNumber+".pptx");
	}
	
	public String getDateFormated(Long idUser,int nrNumber) {
		LocalDate data =  userNrRepository.findByUserIdAndNrNumber(idUser, nrNumber).getDateResponse();
		Locale local = new Locale("pt","BR");
		DateFormat formato = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy",local);
		return formato.format(data);
		
	}
	
	public ResponseEntity<?> downloadCertificate(Long idUser,int nrNumber) throws Exception{
		UserNR userNR = userNrRepository.findByUserIdAndNrNumber(idUser,nrNumber);
		
        if(!(userNR.getStatus() == Status.Vencida)) {
        	User user = getUserById(idUser);
    		
            Map<String, String> placeholders = new HashMap<>();
            placeholders.put("{{NOME}}", user.getName()+" "+user.getLastname());
            placeholders.put("{{RG}}", user.getRG());
            placeholders.put("{{CPF}}", user.getCPF());
            placeholders.put("{{DATA}}", getDateFormated(idUser,nrNumber));
            
        	return generateCertificate(placeholders, selectModelNR(nrNumber));
        }else {
        	return ResponseEntity
        			.status(HttpStatus.BAD_REQUEST)
        			.body("This user did not complete this NR or expired");
        }
	}
	
	public ResponseEntity<?> generateCertificate(Map<String, String> placeholders, Resource resource) throws Exception {
	    if (!resource.exists()) {
	        throw new ModelNotFoundException(resource);
	    }

	    try (InputStream inputStream = resource.getInputStream();
	         XMLSlideShow pptx = new XMLSlideShow(inputStream);
	         ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
	        
	        pptx.getSlides().forEach(slide -> processSlide(slide, placeholders));
	        
	        pptx.write(outputStream);

	        HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());

	        return ResponseEntity.ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                .body(new ByteArrayResource(outputStream.toByteArray()));
	    }
	}

	private void processSlide(XSLFSlide slide, Map<String, String> placeholders) {
	    slide.getShapes().forEach(shape -> {
	        if (shape instanceof XSLFTextShape textShape) {
	            replacePlaceholders(textShape.getTextParagraphs(), placeholders);
	        } else if (shape instanceof XSLFTable table) {
	            table.getRows()
	                .forEach(row -> row.getCells()
	                .forEach(cell -> replacePlaceholders(cell.getTextParagraphs(), placeholders)));
	        }
	    });
	}

	private void replacePlaceholders(List<XSLFTextParagraph> paragraphs, Map<String, String> placeholders) {
	    paragraphs.forEach(paragraph -> 
	        paragraph.getTextRuns().forEach(textRun -> 
	            placeholders.forEach((key, value) -> 
	                textRun.setText(textRun.getRawText().replace(key, value))
	            )
	        )
	    );
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
