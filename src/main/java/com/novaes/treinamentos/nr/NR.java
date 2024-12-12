package com.novaes.treinamentos.nr;

import java.util.List;

import com.novaes.treinamentos.questions.Questions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class NR {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int number;
	
	private String title;
	
	@Column(length = 1280000000)
    @Lob
	private String description;
	
	private String videoURL;
	
	@Column(length = 1280000000)
    @Lob
	private List<String> listRequiriments;
	
	private String workload;
	
	@OneToMany(mappedBy = "nr")
	private List<Questions> listQuestions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getListRequiriments() {
		return listRequiriments;
	}

	public void setListRequiriments(List<String> listRequiriments) {
		this.listRequiriments = listRequiriments;
	}
	
	public void addRequirimentInList(String requiriment) {
		this.listRequiriments.add(requiriment);
	}

	public String getWorkload() {
		return workload;
	}

	public void setWorkload(String workload) {
		this.workload = workload;
	}

	public List<Questions> getListQuestions() {
		return listQuestions;
	}

	public void setListQuestions(List<Questions> listQuestions) {
		this.listQuestions = listQuestions;
	}
	
	public String getVideoUrl() {
		return this.videoURL;
	}
	
	public void setVideoUrl(String videoURL) {
		this.videoURL=videoURL;
	}
	
	
	
}
