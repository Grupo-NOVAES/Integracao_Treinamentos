package com.novaes.treinamentos.usernr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.novaes.treinamentos.nr.NR;

public interface UserNRRepository extends JpaRepository<UserNR, Long>{
	
	@Query("SELECT u.nr FROM UserNR u WHERE u.user.id = :userId")
	public List<NR> findAllNrByUserId(Long userId);
	
	@Query("SELECT u FROM UserNR u WHERE u.user.id = :userId")
	public List<UserNR> findAllNrUserByUserId (Long userId);

}
