package edu.colostate.cs415.model;


import java.util.HashSet;
import java.util.Set;

public class Company {


    private String name;
    private Set<Worker> employees; 
    private Set<Worker> available;
    private Set<Worker> assigned;
    private Set<Project> projects;
    private Set<Qualification> qualifications;


    public Company(String name) {
        if (name == null || name.trim().isEmpty()) {
           throw new IllegalArgumentException("Company name");
        }

        this.name = name;
        this.employees = new HashSet<>();
        this.available = new HashSet<>();
        this.assigned = new HashSet<>();
        this.projects = new HashSet<>();
        this.qualifications = new HashSet<>();   
   }


    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || !(other instanceof Company)) return false;
        return name.equals(((Company) other).name);
    }


    @Override
    public String toString() {
        return name + ":" + available.size() + ":" + projects.size();
    }


    public String getName() {
        return name;
    }


    public Set<Worker> getEmployedWorkers() {
        return new HashSet<>(employees);
    }


    public Set<Worker> getAvailableWorkers() {     
        return new HashSet<>(available);
    }


    public Set<Worker> getUnavailableWorkers() {
        Set<Worker> unavailable = new HashSet<>(employees);
        unavailable.removeAll(available);
        return unavailable;
    }



    public Set<Worker> getAssignedWorkers() {
        return new HashSet<>(assigned);
    }


    public Set<Worker> getUnassignedWorkers() {
        Set<Worker> unassigned = new HashSet<>(employees);
        unassigned.removeAll(assigned);
        return unassigned;
    }


    public Set<Project> getProjects() {
        return new HashSet<>(projects);
    }


    public Set<Qualification> getQualifications() {
        return new HashSet<>(qualifications);
    }
  
    public Worker createWorker(String name, Set<Qualification> qs, double salary) {
        if (name == null || name.trim().isEmpty() || qs == null || qs.isEmpty() || salary < 0 || !qualifications.containsAll(qs)) {
            return null;
        }
        Worker worker = new Worker(name.trim(), qs, salary);
        for (Qualification q : qs) {
            q.addWorker(worker);
        }
        employees.add(worker);
        available.add(worker);
        return worker;
    }

    public Qualification createQualification(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
    
        String cleaned = description.trim();
    
        for (Qualification q : qualifications) {
            if (q.toString().equalsIgnoreCase(cleaned)) {
                return null;
            }
        }
    
        Qualification q = new Qualification(cleaned);
        qualifications.add(q);
        return q;
    }
   
    public Project createProject(String name, Set<Qualification> qs, ProjectSize size) {
        if (name == null || name.trim().isEmpty() || qs == null || qs.isEmpty() || size == null) {
            throw new IllegalArgumentException();
        }
        Project project = new Project(name.trim(), qs, size);
        projects.add(project);
        return project;
    }

    public void start(Project project) {
        if (project == null || !projects.contains(project)) {
            throw new IllegalArgumentException();
        }
        ProjectStatus status = project.getStatus();
        if ((status == ProjectStatus.PLANNED || status == ProjectStatus.SUSPENDED)) {
            if (project.getMissingQualifications().isEmpty()) {
                project.setStatus(ProjectStatus.ACTIVE);
            }
        }
    }


    public void finish(Project project) {
        if (project == null || !projects.contains(project)) {
            throw new IllegalArgumentException();
        }
        if (project.getStatus() == ProjectStatus.ACTIVE) {
            for (Worker w : project.getWorkers()) {
                w.removeProject(project);
                if (w.isAvailable()) available.add(w);
                if (w.getProjects().isEmpty()) assigned.remove(w);
            }
            project.removeAllWorkers();
            project.setStatus(ProjectStatus.FINISHED);
        }
    }   

    public void assign(Worker worker, Project project) {
        if (worker == null || project == null || !employees.contains(worker) || !projects.contains(project)) {
            throw new IllegalArgumentException();
        }
        if (project.getWorkers().contains(worker) || worker.getProjects().contains(project)) return;
        if (project.getStatus() == ProjectStatus.ACTIVE || project.getStatus() == ProjectStatus.FINISHED) return;
        if (!available.contains(worker) || worker.willOverload(project) || !project.isHelpful(worker)) return;

        worker.addProject(project);
        project.addWorker(worker);
        assigned.add(worker);
        if (!worker.isAvailable()) {
            available.remove(worker);
        }
    }   

    public void unassign(Worker worker, Project project) {
        if (worker == null || project == null || !employees.contains(worker) || !projects.contains(project)) {
            throw new IllegalArgumentException();
        }
        if (!project.getWorkers().contains(worker)) return;

        worker.removeProject(project);
        project.removeWorker(worker);

        if (worker.isAvailable()) available.add(worker);
        if (worker.getProjects().isEmpty()) assigned.remove(worker);

        if (project.getStatus() == ProjectStatus.ACTIVE && !project.getMissingQualifications().isEmpty()) {
            project.setStatus(ProjectStatus.SUSPENDED);
        }
    }   

    public void unassignAll(Worker worker) {
        if (worker == null || !employees.contains(worker)) {
            throw new IllegalArgumentException();
        }
        Set<Project> projectsToUnassign = new HashSet<>(worker.getProjects());
        for (Project p : projectsToUnassign) {
            if (projects.contains(p)) {
                unassign(worker, p);
            }
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}


