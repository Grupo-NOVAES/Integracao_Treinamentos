package com.novaes.treinamentos.alertUserNr;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.novaes.treinamentos.usernr.Status;

import jakarta.transaction.Transactional;

public interface AlertUserNrRepository extends JpaRepository<AlertUserNR, Long>{

	@Modifying
    @Transactional
    @Query("DELETE FROM AlertUserNR a WHERE a.nameUser = :username AND a.nrNumber = :nrNumber AND a.status = :status")
    void deleteAlert(@Param("username") String username, @Param("nrNumber") int nrNumber, @Param("status") Status status);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM AlertUserNR a WHERE a.nameUser = :username AND a.nrNumber = :nrNumber")
    void deleteAlert(@Param("username") String username, @Param("nrNumber") int nrNumber);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM AlertUserNR a WHERE a.idUser = :idUser")
    void deleteAlert(@Param("idUser") Long idUser);
}
