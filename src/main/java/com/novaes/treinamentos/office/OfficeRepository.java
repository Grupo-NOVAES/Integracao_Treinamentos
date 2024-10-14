package com.novaes.treinamentos.office;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OfficeRepository extends JpaRepository<Office, Long>{
	
	@Query("SELECT o From Office o Where o.specialization = :officeName")
	public Office findByName(String officeName);

}
