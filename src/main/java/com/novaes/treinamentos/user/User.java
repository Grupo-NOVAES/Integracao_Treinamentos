package com.novaes.treinamentos.user;

import static java.util.Collections.singleton;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.novaes.treinamentos.office.Office;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String lastname;

    @Column(unique = true, nullable = false)
    private String login;

    private String password;
    
    private String phoneNumber;

    public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	private boolean enabled;

    @Column(nullable = false)
    private Role role;

    private ZonedDateTime activationDate;
    
    @ManyToOne
    private Office office;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return singleton(role);
    }

    @Override
    public String getUsername() {
        return name + " " + lastname; 
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; 
    }

    @Override
    public boolean isEnabled() {
        return enabled; 
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void activate() {
        this.enabled = true; 
        this.activationDate =  ZonedDateTime.now();
    }

    public void deactivate() {
        this.enabled = false; 
        this.activationDate = null; 
    }

    public boolean isActive() {
        if (activationDate == null) {
            return false;
        }
        ZonedDateTime expirationTime = activationDate.plusMinutes(60);
        return ZonedDateTime.now().isBefore(expirationTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public ZonedDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(ZonedDateTime activationDate) {
        this.activationDate = activationDate;
    }
}
