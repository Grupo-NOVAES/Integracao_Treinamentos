package com.novaes.treinamentos.UserNR;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.novaes.treinamentos.NR.NR;

public interface UserNRRepository extends JpaRepository<UserNR, Long>{
	
	@Query("SELECT u.nr FROM UserNR u WHERE u.user.id = :userId")
	public List<NR> findAllNrByUserId(Long userId);

}
