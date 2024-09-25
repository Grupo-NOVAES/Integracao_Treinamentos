package com.novaes.treinamentos.user;

import jakarta.persistence.Column;

public class UserDTO {
	
	private Long id;
	private String lastname;
	private String name;
	private String login;
	private String password;
	private boolean enabled;
	private Role role;
	
	public UserDTO() {}
	
	public UserDTO(Long id,String lastname,String name,String login,String password,boolean enabled,Role role) {
		this.id=id;
		this.lastname=lastname;
		this.name=name;
		this.login=login;
		this.password=password;
		this.enabled=enabled;
		this.role=role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
	

}
