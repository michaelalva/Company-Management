package edu.colostate.cs415.model;

import java.util.HashSet;
import java.util.Set;

import edu.colostate.cs415.dto.WorkerDTO;

public class Worker {

	public static final int MAX_WORKLOAD = 12;

	private String name;
	private double salary;
	private Set<Project> projects;
	private Set<Qualification> qualifications;

	public Worker(String name, Set<Qualification> qualifications, double salary) {
		if (name == null || name.trim().isEmpty() || qualifications == null || qualifications.isEmpty() || salary < 0) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.salary = salary;
		this.projects = new HashSet<>();
		this.qualifications = new HashSet<>(qualifications);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || !(other instanceof Worker)) {
			return false;
		}
		Worker otherWorker = (Worker) other;
		return this.name.equals(otherWorker.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String toString() {
		return name + ":" + projects.size() + ":" + qualifications.size() + ":" + (int)salary;
	}
	

	public String getName() {
		return name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		if (salary < 0) {
			throw new IllegalArgumentException("Salary cannot be negative");
		}
		this.salary = salary;
	}

	public Set<Qualification> getQualifications() {
		return new HashSet<>(qualifications);
	}

	public void addQualification(Qualification qualification) {
		if (qualification == null) {
			throw new IllegalArgumentException("Qualification cannot be null");
		}
		this.qualifications.add(qualification);
	}

	public Set<Project> getProjects() {
		return new HashSet<>(projects);
	}

	public void addProject(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project cannot be null");
		}
		projects.add(project);
	}

	public void removeProject(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project cannot be null");
		}
		projects.remove(project);
	}

	public int getWorkload() {
		int workload = 0;
		for (Project p : projects) {
			if (p.getStatus() != ProjectStatus.FINISHED) {
				if (p.getSize() == ProjectSize.BIG) workload += 3;
				else if (p.getSize() == ProjectSize.MEDIUM) workload += 2;
				else workload += 1;
			}
		}
		return workload;
	}

	public boolean willOverload(Project project) {
		if (project == null) {
			throw new IllegalArgumentException("Project cannot be null");
		}
		int currentWorkload = getWorkload();
		if (projects.contains(project)) {
			return currentWorkload > MAX_WORKLOAD;
		}
		int addedLoad = 0;
		if (project.getSize() == ProjectSize.BIG) addedLoad = 3;
		else if (project.getSize() == ProjectSize.MEDIUM) addedLoad = 2;
		else addedLoad = 1;
		return (currentWorkload + addedLoad) > MAX_WORKLOAD;
	}


	public boolean isAvailable() {
		return getWorkload() < MAX_WORKLOAD;
	}

	public WorkerDTO toDTO() {
		return new WorkerDTO(
			name,
			salary,
			getWorkload(),
			projects.stream().map(Project::getName).toArray(String[]::new),
			qualifications.stream().map(Qualification::toString).toArray(String[]::new)
		);
	}
}
