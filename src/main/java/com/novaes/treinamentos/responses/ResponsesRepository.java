package com.novaes.treinamentos.responses;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;

public interface ResponsesRepository extends JpaRepository<Responses, Long>{
	
	@Query("SELECT r FROM Responses r WHERE r.user.id = :userId AND r.questions.nr.number = :nrNumber")
	public List<Responses> findResposesByUserAndNrNumber(Long userId, int nrNumber);
	
	@Query("SELECT r FROM Responses r WHERE r.questions.id = :idQuestion")
	public List<Responses> findResponsesByQuestionId(Long idQuestion);
	
	@Query("SELECT r FROM Responses r WHERE r.questions.id = :idQuestion AND r.user.id = :userId")
	public List<Responses>findResponsesByUserAndQuestion(Long userId,Long idQuestion);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Responses r WHERE r.user.id = :idUser")
	public void deleteByUserId(Long idUser);
	
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Responses r WHERE r.questions.id = :idQuestion")
	public void deleteByQuestionsId(Long idQuestion);
	
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Responses r WHERE r.user.id = :userId")
	public void deleteAllResponsesByUserId(Long userId);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Responses r WHERE r.user.id = :userId AND r.questions.id = :questionId")
	public void deleteByUserIdAndQuestionId(Long userId, Long questionId);



}
