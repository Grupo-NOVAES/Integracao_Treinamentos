package com.novaes.treinamentos.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.enabled = true AND u.role = :role")
    List<User> findAllActiveUsersByRole(@Param("role") Role role);
	
	User findByLogin(String login);
	
	List<User> findByRole(Role role);
	
}
