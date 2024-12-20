package com.novaes.treinamentos.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.enabled = true AND u.role = :role")
    List<User> findAllActiveUsersByRole(@Param("role") Role role);
	
	@Query("SELECT u FROM User u WHERE u.office.id = :officeId")
    List<User> findUsersByOfficeId(@Param("officeId") Long officeId);
	
	User findByLogin(String login);
	
	List<User> findByRole(Role role);
	
	boolean existsBycpf(String cpf);

    boolean existsByrg(String rg);
    
    boolean existsByLogin(String login);
	
	
	@Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.office.id = :officeId")
	public void deleteUserByOfficeId(Long officeId);
	
}
