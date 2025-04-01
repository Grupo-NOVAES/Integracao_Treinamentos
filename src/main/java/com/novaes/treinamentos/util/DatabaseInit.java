package com.novaes.treinamentos.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.novaes.treinamentos.nr.NR;
import com.novaes.treinamentos.nr.NrRepository;
import com.novaes.treinamentos.office.Office;
import com.novaes.treinamentos.office.OfficeRepository;
import com.novaes.treinamentos.user.Role;
import com.novaes.treinamentos.user.User;
import com.novaes.treinamentos.user.UserRepository;

@Component
public class DatabaseInit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NrRepository nrRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0) {
        	insertAdmin();
        }
        if(nrRepository.count() == 0) {
        	insertNRs();
        }
        if(officeRepository.count() == 0) {
        	insertOffices();
        }
    }

    private void insertAdmin() {
        User userFound = userRepository.findByLogin("admin@gmail.com");
        if (userFound == null) {
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

    private void insertNRs() {
        Map<Integer, String> nrData = new HashMap<>();
        nrData.put(6, "NR 6 - Equipamentos de Proteção Individual (EPIs)");
        nrData.put(10, "NR 10 - Segurança em Instalações e Serviços em Eletricidade");
        nrData.put(11, "NR 11 - Transporte, Movimentação, Armazenagem e Manuseio de Materiais");
        nrData.put(12, "NR 12 - Segurança no Trabalho em Máquinas e Equipamentos");
        nrData.put(15, "NR 15 - Atividades e Operações Insalubres");
        nrData.put(18, "NR 18 - Condições e Meio Ambiente de Trabalho na Indústria da Construção");
        nrData.put(26, "NR 26 - Sinalização de Segurança");
        nrData.put(33, "NR 33 - Segurança e Saúde nos Trabalhos em Espaços Confinados");
        nrData.put(35, "NR 35 - Trabalho em Altura");

        for (Map.Entry<Integer, String> entry : nrData.entrySet()) {
            int nrNumber = entry.getKey();
            String nrTitle = entry.getValue();

            if (!nrRepository.existsByNumber(nrNumber)) {
                NR nr = new NR();
                nr.setNumber(nrNumber);
                nr.setTitle(nrTitle);
                nr.setDescription("Descrição detalhada da " + nrTitle);
                nr.setListRequiriments(Arrays.asList("Requisito 1", "Requisito 2", "Requisito 3"));
                nrRepository.save(nr);
            }
        }
    }

    private void insertOffices() {
        Map<String, List<Integer>> officeNrMap = new HashMap<>();

        officeNrMap.put("ajudante de saneamento", Arrays.asList(6, 11, 12, 15, 18, 33, 35));
        officeNrMap.put("ajudante de pedreiro", Arrays.asList(6, 11, 12, 15, 18, 33, 35));
        officeNrMap.put("instalador hidráulico", Arrays.asList(11, 12, 15, 18, 33, 35));
        officeNrMap.put("ajudante de eletricista", Arrays.asList(10, 11, 12, 15, 18, 33, 35));
        officeNrMap.put("eletricista", Arrays.asList(10, 11, 12, 15, 18, 33, 35));
        officeNrMap.put("encarregado de manutenção", Arrays.asList(11, 12, 15, 18, 33, 35));
        officeNrMap.put("gerente", Arrays.asList(6, 11, 12, 15, 18, 33, 35));
        officeNrMap.put("gerente I", Arrays.asList(11, 12, 15, 18, 33, 35));
        officeNrMap.put("encanador", Arrays.asList(11, 12, 15, 18, 33, 35));
        officeNrMap.put("encanador I", Arrays.asList(11, 12, 15, 18, 33, 35));
        officeNrMap.put("pedreiro", Arrays.asList(11, 12, 15, 18, 33, 35));
        officeNrMap.put("analista ambiental", Arrays.asList(11, 12, 15, 18, 33, 35));
        officeNrMap.put("analista técnico", Arrays.asList(11, 12, 15, 18, 33, 35));
        officeNrMap.put("assistente técnico", Arrays.asList(11, 12, 15, 18, 33, 35));
        officeNrMap.put("assistente técnico I", Arrays.asList(11, 12, 15, 18, 33, 35));
        officeNrMap.put("assistente técnico II", Arrays.asList(12, 15, 18, 33, 35));
        officeNrMap.put("técnico em química", Arrays.asList(26, 12, 15, 18, 33, 35));
        officeNrMap.put("motorista de caminhão", Arrays.asList(12, 15, 18, 33, 35));
        officeNrMap.put("operador de retroescavadeira", Arrays.asList(12, 15, 18, 33, 35));
        officeNrMap.put("encarregado de obras", Arrays.asList(12, 15, 18, 33, 35));
        officeNrMap.put("mecânico", Arrays.asList(12, 15, 18, 33, 35));
        officeNrMap.put("assessor técnico ambiental", Arrays.asList(6));
        officeNrMap.put("assistente técnico júnior", Arrays.asList(6));

        for (Map.Entry<String, List<Integer>> entry : officeNrMap.entrySet()) {
            String officeName = entry.getKey();
            List<Integer> nrNumbers = entry.getValue();

            Office office = officeRepository.findByName(officeName);
            if (office == null) {
                office = new Office();
                office.setSpecialization(officeName);
            }
                        
            for (Integer nrNumber : nrNumbers) {
                NR nr = nrRepository.findNrByNumber(nrNumber);
                if (nr != null && !office.getListNR().contains(nr)) {
                    office.getListNR().add(nr);
                }
            }
            officeRepository.save(office);
        }
    }
}

