package com.novaes.treinamentos.office;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.novaes.treinamentos.nr.NR;

public interface OfficeRepository extends JpaRepository<Office, Long>{
	
	@Query("SELECT o FROM Office o LEFT JOIN FETCH o.listNR WHERE o.specialization = :name")
    Office findByName(@Param("name") String name);
	
	@Query("SELECT o FROM Office o JOIN o.listNR nr WHERE nr.id = :idNr")
	public List<Office> findByIdNr(Long idNr);
	
	@Query("SELECT o.listNR FROM Office o WHERE o.id = :officeId")
    List<NR> findNrsByOfficeId(@Param("officeId") Long officeId);

}
