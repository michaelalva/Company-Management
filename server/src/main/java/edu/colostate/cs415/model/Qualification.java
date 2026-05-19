package edu.colostate.cs415.model;

import java.util.HashSet;
import java.util.Set;

import edu.colostate.cs415.dto.QualificationDTO;

public class Qualification {

	private String description;
	private Set<Worker> workers;

	public Qualification(String description) {
		if (description == null || description.trim().isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.description = description;
		this.workers = new HashSet<>();
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !(o instanceof Qualification)) {
			return false;
		}
		Qualification other = (Qualification) o;
		return this.description.equals(other.description);
	}

	@Override
	public int hashCode() {
		return description.hashCode();
	}

	@Override
	public String toString() {
		return description;
	}

	public Set<Worker> getWorkers() {
		return new HashSet<>(workers);
	}

	public void addWorker(Worker worker) {
		if (worker == null) {
			throw new IllegalArgumentException();
		}
		workers.add(worker);
	}

	public void removeWorker(Worker worker) {
		if (worker == null) {
			throw new IllegalArgumentException();
		}
		workers.remove(worker);
	}

	public QualificationDTO toDTO() {

		int size = workers.size();
		String[] workerNames = new String[size];
	
		if (workers == null) {
    		workerNames = new String[0];
		}
		
		int index = 0;
	
		for (Worker worker : workers) {
			String name = worker.getName();
			workerNames[index] = name;
			index = index + 1;
		}
	
		QualificationDTO dto = new QualificationDTO(description, workerNames);
		return dto;
	}
}
