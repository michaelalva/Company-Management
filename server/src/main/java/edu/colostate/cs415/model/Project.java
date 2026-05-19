package edu.colostate.cs415.model;

import java.util.HashSet;
import java.util.Set;

import edu.colostate.cs415.dto.ProjectDTO;

public class Project {

	private String name;
	private ProjectSize size;
	private ProjectStatus status;
	private Set<Worker> workers;
	private Set<Qualification> qualifications;

	public Project(String name, Set<Qualification> qualifications, ProjectSize size) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Project name cannot be null or empty");
		}

		if (qualifications == null || qualifications.isEmpty()) {
			throw new IllegalArgumentException("Qualification set cannot be null or empty");
		}

		if (size == null) {
			throw new IllegalArgumentException("Project size cannot be null");
		}

		this.name = name;
		this.qualifications = new HashSet<>(qualifications);
		this.size = size;
		this.status = ProjectStatus.PLANNED;
		this.workers = new HashSet<>();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
       		return true;
		}
		if (other == null || !(other instanceof Project)) {
			return false;
		}
		Project otherProject = (Project) other;
		return this.name.equals(otherProject.name);
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		int workerCount = workers.size();
		return name + ":" + workerCount + ":" + status.name();
		
	}

	public String getName() {
		return this.name;
	}

	public ProjectSize getSize() {
		return this.size;
	}

	public ProjectStatus getStatus() {
		return this.status;
	}

	public void setStatus(ProjectStatus status) {
		if (status == null ) throw new IllegalArgumentException("Status cannot be null");
		this.status = status;
	}

	public void addWorker(Worker worker) {
		if (worker == null) throw new IllegalArgumentException("Worker cannot be null");
		workers.add(worker);
	}

	public void removeWorker(Worker worker) {
		if (worker == null) throw new IllegalArgumentException("Worker cannot be null");
		workers.remove(worker);
	}

	public Set<Worker> getWorkers() {
		return new HashSet<>(workers);
	}

	public void removeAllWorkers() {
		workers.clear();
	}

	public Set<Qualification> getRequiredQualifications() {
		return new HashSet<Qualification>(qualifications);
	}

	public void addQualification(Qualification qualification) {
		if (qualification == null) {
			throw new IllegalArgumentException("Qualification cannot be null");
		}

		if (status != ProjectStatus.PLANNED) {
			throw new IllegalStateException("Cannot add qualifications to an active or completed project");
		}

		qualifications.add(qualification);
	}

	public Set<Qualification> getMissingQualifications() {
		Set<Qualification> acc = new HashSet<>();
		for (Worker w : workers) {
			acc.addAll(w.getQualifications());
		}
		return qualifications.stream()
				.filter(q -> !acc.contains(q))
				.collect(java.util.stream.Collectors.toSet());
	}

	public boolean isHelpful(Worker worker) {
		if (worker == null) {
			throw new IllegalArgumentException("Worker cannot be null");
		}
		Set<Qualification> missing = getMissingQualifications();
		return worker.getQualifications().stream()
				.anyMatch(q -> missing.contains(q));
	}

	public ProjectDTO toDTO() {
		return new ProjectDTO(name,
				size,
				status,
				workers.stream().map(Worker::getName).toArray(String[]::new),
				qualifications.stream().map(Qualification::toString).toArray(String[]::new),
				getMissingQualifications().stream().map(Qualification::toString).toArray(String[]::new));
	}
}
