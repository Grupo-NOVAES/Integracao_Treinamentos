package com.novaes.treinamentos.nr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NrRepository extends JpaRepository<NR, Long>{
	
	@Query("SELECT n FROM NR n WHERE n.number = :nrNumber")
	public NR findNrByNumber(int nrNumber);
	
	public boolean existsByNumber(int nrNumber);

}
