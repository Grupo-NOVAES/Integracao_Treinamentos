package com.novaes.treinamentos.office;

import java.util.List;

import com.novaes.treinamentos.nr.NR;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Office {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String specialization;
	
	@ManyToMany
    @JoinTable(
        name = "office_nr",
        joinColumns = @JoinColumn(name = "office_id"),
        inverseJoinColumns = @JoinColumn(name = "nr_id")
    )
	private List<NR> listNR;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public List<NR> getListNR() {
		return listNR;
	}

	public void setListNR(List<NR> listNR) {
		this.listNR = listNR;
	}
	
	public void addNrToList(NR nr) {
		this.listNR.add(nr);
	}
	
	public void deleteNrFromList(Long idNr) {
	    if (this.listNR != null) {
	        this.listNR.removeIf(nr -> nr.getId().equals(idNr));
	    }
	}

	
}
