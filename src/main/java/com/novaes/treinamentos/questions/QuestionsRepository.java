package com.novaes.treinamentos.questions;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionsRepository extends JpaRepository<Questions, Long>{

	@Query("SELECT q FROM Questions q WHERE q.nr.number = :nrNumber")
	List<Questions> getByNr(@Param("nrNumber") int nrNumber);


}
