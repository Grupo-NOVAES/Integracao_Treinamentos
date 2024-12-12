package com.novaes.treinamentos.questions;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface QuestionsRepository extends JpaRepository<Questions, Long>{

	@Query("SELECT q FROM Questions q WHERE q.nr.number = :nrNumber")
	List<Questions> getByNr(@Param("nrNumber") int nrNumber);
	
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Questions q WHERE q.nr.id = :nrId")
	public void deleteAllQuestionByNr(Long nrId);
	
	@Query("SELECT q.id FROM Questions q WHERE q.nr.id = :nrId")
	List<Long> findQuestionIdsByNrId(@Param("nrId") Long nrId);


}
