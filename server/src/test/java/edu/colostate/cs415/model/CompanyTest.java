package edu.colostate.cs415.model;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;


public class CompanyTest {
    private Company company;
    private Qualification java;
    private Qualification python;

    private Worker worker;
    private Project project;

    @Before
    public void setUp() {
        company = new Company("TestCo");

        java = company.createQualification("Java");
        python = company.createQualification("Python");

        Set<Qualification> workerQs = new HashSet<>();
        workerQs.add(java);

        worker = company.createWorker("Alice", workerQs, 50000);

        Set<Qualification> projectQs = new HashSet<>();
        projectQs.add(java);
        
        project = company.createProject("Proj1", projectQs, ProjectSize.SMALL);
    }

   @Test(expected = IllegalArgumentException.class)
   public void testConstructor_throwsWhenNull() {                     
       new Company(null);       
   }


   @Test
   public void testConstructor_throwsWhenEmpty() {
       try {
           new Company("");
           fail("Expected IllegalArgumentException");
       } catch (IllegalArgumentException e) {}
   }


   @Test
   public void testConstructor_throwsWhenWhitespaceOnly() {
       try {
           new Company(" \n\t ");
           fail("Expected IllegalArgumentException");
       } catch (IllegalArgumentException e) {}
   }


   @Test
   public void testToString_brandNew(){
       Company company = new Company("Java Hills");
       assertEquals("Java Hills:0:0", company.toString());
   }


   @Test
   public void testGetName_normal(){
       Company company = new Company("Java Hills");
       assertEquals("Java Hills", company.getName());
   }
   @Test
   public void testConstructor_validWhiteSpace(){
       Company company = new Company("software testing & analysis");
       assertEquals("software testing & analysis:0:0", company.toString());
   }


   @Test
   public void testGetAvailableWorkers_empty() {
       Company company = new Company("Tech Solutions");
       assertEquals(0, company.getAvailableWorkers().size());
   }

    @Test
    public void testGetAvailableWorkers_oneWorker() {
     Company company = new Company("Tech Solutions");
         Qualification q = company.createQualification("Java");
         Set<Qualification> qs = new HashSet<>();
         qs.add(q);
         Worker w1 = company.createWorker("Alice", qs, 100.0);      
           
         assertTrue(company.getAvailableWorkers().contains(w1));
     }


   @Test
    public void testGetAvailableWorkers_twoWorker() {
        Company company = new Company("Tech Solutions");
        Qualification q = company.createQualification("Java");
        Set<Qualification> qs = new HashSet<>();
        qs.add(q);

        company.createWorker("Alice", qs, 100.0);
        company.createWorker("Bob", qs, 200.0);

        assertEquals(2, company.getEmployedWorkers().size());
    }


   @Test
   public void testGetUnavailableWorkers_empty() {
       Company company = new Company("Tech Solutions");
       assertEquals(0, company.getUnavailableWorkers().size());
   }


   @Test
   public void testGetUnavailableWorkers_oneWorker() {
        Company company = new Company("Tech Solutions");
        Qualification q = company.createQualification("Java");
        Set<Qualification> qs = new HashSet<>();
        qs.add(q);

        // Reaches max workload of worker to flag as unavailable
        Worker w1 = company.createWorker("Alice", qs, 100.0);
        for (int i = 0; i < 4; i++) {
            Project p = company.createProject("P" + i, qs, ProjectSize.BIG);
            company.assign(w1, p);
        }

        assertEquals(1, company.getUnavailableWorkers().size());
   }

   @Test
   public void testGetUnavailableWorkers_oneAvailableOneUnavailable() {
    Company c = new Company("Super Company");
    Qualification q = c.createQualification("typing");
    Set<Qualification> qs = new HashSet<>();
    qs.add(q);

    Worker w1 = c.createWorker("Joe", qs, 100.0);
    Worker w2 = c.createWorker("Bob", qs, 100.0);

    for (int i = 0; i < 4; i++) {
        Project p = c.createProject("P" + i, qs, ProjectSize.BIG);
        c.assign(w1, p);
    }

    Set<Worker> result = c.getUnavailableWorkers();

    assertTrue(result.contains(w1));
    assertFalse(result.contains(w2));
   }


   @Test
   public void testGetEmployedWorkers_empty() {
       Company company = new Company("Tech Solutions");
       assertEquals(0, company.getEmployedWorkers().size());
   }


   @Test
   public void testEquals_equal() {
       Company company1 = new Company("Tech Solutions");
       Company company2 = new Company("Tech Solutions");
       assertTrue(company1.equals(company2));
   }


   @Test
   public void testEquals_sameObject() {
       Company company = new Company("Tech Solutions");
       assertTrue(company.equals(company));
   }


   @Test
   public void testEquals_null() {
       Company company = new Company("Tech Solutions");
       assertFalse(company.equals(null));
   }


   @Test
   public void testEquals_differentType() {
       Company company = new Company("Tech Solutions");
       assertFalse(company.equals("Tech Solutions"));
   }


   @Test
   public void testEquals_notEqual() {
       Company company1 = new Company("Tech Solutions");
       Company company2 = new Company("Outdated Tech");
       assertFalse(company1.equals(company2)); 
   }


   @Test
   public void testGetAvailableWorkers_availableWorker() {
       Company company = new Company("Tech Solutions");
       company.createWorker("Alice", new HashSet<>(), 100.0);
       // assertEquals(1, company.getAvailableWorkers().size());

   }


//    @Test
//    public void testGetAvailableWorkers_unavailableWorker() {
//        Company company = new Company("Tech Solutions");       
//        company.createWorker("Alice", new HashSet<>(), 100.0);
//        while (company.getAvailableWorkers().size() > 0) {
//            Worker worker = company.getAvailableWorkers().iterator().next();
//            company.createProject("Project X", new HashSet<>(), ProjectSize.SMALL).addWorker(worker);
//        }
//        assertEquals(1, company.getUnavailableWorkers().size());
//    }


   @Test
   public void testGetAssignedWorkers_empty() {
       Company company = new Company("Tech Solutions");
       assertEquals(0, company.getAssignedWorkers().size());
   }


   @Test
   public void testGetUnassignedWorkers_empty() {
       Company company = new Company("Tech Solutions");
       assertEquals(0, company.getUnassignedWorkers().size());
   }

   @Test
    public void testGetAssignedWorkers_and_UnassignedWorkers() {
        Company company = new Company("Tech Solutions");

        Qualification q = company.createQualification("Java");
        Set<Qualification> qs = new HashSet<>();
        qs.add(q);

        Worker w1 = company.createWorker("Alice", qs, 100.0);
        Worker w2 = company.createWorker("Bob", qs, 100.0);

        Project p = company.createProject("Project X", qs, ProjectSize.SMALL);

        company.assign(w1, p);

        Set<Worker> assigned = company.getAssignedWorkers();
        Set<Worker> unassigned = company.getUnassignedWorkers();

        assertTrue(assigned.contains(w1));
        assertFalse(assigned.contains(w2));
        assertEquals(1, assigned.size());

        assertTrue(unassigned.contains(w2));
        assertFalse(unassigned.contains(w1));
        assertEquals(1, unassigned.size());
    }

   @Test
   public void testGetProjects_initiallyEmpty() {
       Company company = new Company("Tech Solutions");
       assertEquals(0, company.getProjects().size());
   }


   @Test
   public void testGetQualifications_initiallyEmpty() {
       Company company = new Company("Tech Solutions");
       assertEquals(0, company.getQualifications().size());
   }


   @Test(expected = IllegalArgumentException.class)
   public void testCreateQualification_null() {
       Company company = new Company("Tech");
       company.createQualification(null);
 
   }

   @Test(expected = IllegalArgumentException.class)
    public void testCreateQualification_EmptyDescription() {
        company.createQualification("");
    }


   @Test
   public void testCreateQualification_valid() {
       Company company = new Company("Tech");


       Qualification q = company.createQualification("Java");


       assertTrue(company.getQualifications().contains(q));
       assertEquals("Java", q.toString());
   }
  
   // createWorker() tests

   @Test
   public void testCreateWorker_valid() {
       Company company = new Company("Tech");


   Qualification q = company.createQualification("Java");


   Set<Qualification> qs = new HashSet<>();
   qs.add(q);


   Worker w = company.createWorker("Alice", qs, 0.0);


   assertTrue(company.getEmployedWorkers().contains(w));
   assertTrue(q.getWorkers().contains(w));
   assertEquals(0, w.getProjects().size());
   }

    @Test
    public void testCreateWorker_nullName() {
        Company company = new Company("Gegee");
        Worker w = company.createWorker(null, validQualifications(), 50.0);
        assertNull(w);
    }

    @Test
    public void testCreateWorker_emptyName() {
        Company company = new Company("Gegee");
        Worker w = company.createWorker("   ", validQualifications(), 50.0);
        assertNull(w);
    }

    @Test
    public void testCreateWorker_nullQualifications() {
        Company company = new Company("Gegee");
        Worker w = company.createWorker("Charlie", null, 50.0);
        assertNull(w);
    }

    @Test
    public void testCreateWorker_emptyQualifications() {
        Company company = new Company("Gegee");
        Worker w = company.createWorker("Alice", new HashSet<>(), 50.0);
        assertNull(w);
    }

    @Test
    public void testCreateWorker_qualificationNotRegistered() {
        Company company = new Company("Gegee");
        Set<Qualification> qs = new HashSet<>();
        qs.add(new Qualification("Python"));
        Worker w = company.createWorker("David", qs, 50.0);
        assertNull(w);
    }

    @Test
    public void testCreateWorker_negativeSalary() {
        Company company = new Company("Gegee");
        Worker w = company.createWorker("Eve", validQualifications(), -10.0);
        assertNull(w);
    }

   // createProject() tests

   @Test
   public void testCreateProject_valid() {
       Company company = new Company("Tech");


       Qualification q = company.createQualification("Java");


       Set<Qualification> qs = new HashSet<>();
       qs.add(q);


       Project p = company.createProject("ProjectX", qs, ProjectSize.SMALL);


       assertTrue(company.getProjects().contains(p));
       assertTrue(p.getRequiredQualifications().contains(q));
   }

   @Test (expected = IllegalArgumentException.class)
   public void testCreateProject_invalidName() {
        company.createProject(null, validQualifications(), ProjectSize.BIG);
   }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProject_emptyName() {
        Company company = new Company("Gegee");
        company.createProject("", validQualifications(), ProjectSize.BIG);
    }

   @Test (expected = IllegalArgumentException.class)
   public void testCreateProject_invalidQualifications() {
        company.createProject("Arc Reactor", null, ProjectSize.BIG);
   }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProject_emptyQualifications() {
        Company company = new Company("Gegee");
        Set<Qualification> emptyQs = new HashSet<>();
        company.createProject("Arc Reactor", emptyQs, ProjectSize.BIG);
    }

   @Test (expected = IllegalArgumentException.class)
   public void testCreateProject_invalidSize() {
        company.createProject("Arc Reactor", validQualifications(), null);
   }
   
   // start() tests

   @Test(expected = IllegalArgumentException.class)
   public void testStart_nullProject_T1() {
       Company company = new Company("Tech");
       company.start(null);
   } 

   @Test(expected = IllegalArgumentException.class)
    public void testStart_ProjectNotInCompany_T2() {
        Project outsideProject = new Project("Outside", validQualifications(), ProjectSize.SMALL);
        company.start(outsideProject);
    }

    @Test
    public void testStart_PlannedAndQualificationsMet_T3() {
        company.assign(worker, project);
        assertEquals(ProjectStatus.PLANNED, project.getStatus());
        company.start(project);
        assertEquals(ProjectStatus.ACTIVE, project.getStatus());
    }

    @Test
    public void testStart_PlannedAndQualificationsMissing_T4() {
        Project p2 = company.createProject("Proj2", validQualifications(), ProjectSize.SMALL);
        company.start(p2);
        assertEquals(ProjectStatus.PLANNED, p2.getStatus());
    }
    
    @Test
    public void testStart_AlreadyActiveOrFinished_T5() {
        project.setStatus(ProjectStatus.FINISHED);
        company.start(project);
        assertEquals(ProjectStatus.FINISHED, project.getStatus());
    }

    @Test
    public void testStart_ProjectSuspendedNoWorker() {
        Project p1 = company.createProject("AwesomeProject", validQualifications(), ProjectSize.BIG);
        p1.setStatus(ProjectStatus.SUSPENDED);
        company.start(p1);
        assertEquals(ProjectStatus.SUSPENDED, p1.getStatus());
    }

    @Test
    public void testStart_ProjectSuspendedWorkerQualified() {
        Project p1 = company.createProject("AwesomeProject", validQualifications(), ProjectSize.BIG);
        p1.addWorker(company.createWorker("Sarah", validQualifications(), 100.0));
        p1.setStatus(ProjectStatus.SUSPENDED);
        company.start(p1);
        assertEquals(ProjectStatus.ACTIVE, p1.getStatus());
    }

    @Test
    public void testStart_ProjectInProgress() {
        project.setStatus(ProjectStatus.ACTIVE);
        company.start(project);
        assertEquals(ProjectStatus.ACTIVE, project.getStatus());
    }

    @Test
    public void testStart_SuspendedAndQualificationsMet() {
        Project p = company.createProject("SuspendedProject", validQualifications(), ProjectSize.MEDIUM);
        Worker w = company.createWorker("John", validQualifications(), 50.0);
        p.addWorker(w);
        p.setStatus(ProjectStatus.SUSPENDED);

        assertTrue(p.getMissingQualifications().isEmpty());
        company.start(p);

        assertEquals(ProjectStatus.ACTIVE, p.getStatus());
    }
   // assign() tests 

    @Test
    public void testAssign_BaseCase_T1() {
        company.assign(worker, project);
        assertTrue(worker.getProjects().contains(project));
        assertTrue(project.getWorkers().contains(worker));
    }

    @Test
    public void testAssign_WorkerNotAvailable_T2() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(java);

        for (int i = 0; i < 4; i++) {
            Project p = company.createProject("Big" + i, qs, ProjectSize.BIG);
            company.assign(worker, p);
        }

        assertFalse(worker.isAvailable());
        company.assign(worker, project);
        assertFalse(project.getWorkers().contains(worker));
    }

    @Test
    public void testAssign_AlreadyAssigned_T3() {
        company.assign(worker, project);
        company.assign(worker, project);

        assertEquals(1, worker.getProjects().size());
        assertEquals(1, project.getWorkers().size());
    }

    @Test
    public void testAssign_ProjectActive_T4() {
        project.setStatus(ProjectStatus.ACTIVE);
        company.assign(worker, project);
        assertFalse(worker.getProjects().contains(project));
    }

    @Test
    public void testAssign_Overloaded_T5() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(java);

        for (int i = 0; i < 3; i++) {
            Project p = company.createProject("Big" + i, qs, ProjectSize.BIG);
            company.assign(worker, p);
        }

        Project medium = company.createProject("Medium", qs, ProjectSize.MEDIUM);
        company.assign(worker, medium);

        assertEquals(11, worker.getWorkload());

        Project overloadProject = company.createProject("Gegee", qs, ProjectSize.MEDIUM);

        company.assign(worker, overloadProject);
        assertFalse(overloadProject.getWorkers().contains(worker));
    }

    @Test
    public void testAssign_NotHelpful_T6() {
        Set<Qualification> pythonReq = new HashSet<>();
        pythonReq.add(python);

        Project newProject = company.createProject("Gegee", pythonReq, ProjectSize.SMALL);
        company.assign(worker, newProject);

        assertFalse(worker.getProjects().contains(newProject));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssign_NullWorker() {
        company.assign(null, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssign_NullProject() {
        company.assign(worker, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssign_WorkerNotInCompany() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(java);

        Worker outsideWorker = new Worker("Gegee", qs, 50000);

        company.assign(outsideWorker, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssign_ProjectNotInCompany() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(java);

        Project outsideProject = new Project("Gegee", qs, ProjectSize.SMALL);

        company.assign(worker, outsideProject);
    }

    @Test
    public void testAssign_ProjectContainsWorkerOnly() {
        project.addWorker(worker);
        company.assign(worker, project);
        assertFalse(worker.getProjects().contains(project));
    }

    @Test
    public void testAssign_WorkerContainsProjectOnly() {
        worker.addProject(project);
        company.assign(worker, project);
        assertFalse(project.getWorkers().contains(worker));
    }   

    @Test
    public void testAssign_ProjectFinishedBranch() {
        project.setStatus(ProjectStatus.FINISHED);
        company.assign(worker, project);
        assertFalse(worker.getProjects().contains(project));
    }

    // unassign() tests

    @Test
    public void testUnassign_BaseCase_T1() {
        worker.addQualification(java);
        company.assign(worker, project);

        Set<Qualification> projQs = new HashSet<>();
        projQs.add(java);
        Project project2 = company.createProject("Gegee", projQs, ProjectSize.SMALL);

        company.assign(worker, project2); 
        company.unassign(worker, project);
        assertFalse(worker.getProjects().contains(project));
        assertFalse(project.getWorkers().contains(worker));
        assertTrue(worker.getProjects().contains(project2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnassign_WorkerNull_T2() {
        company.unassign(null, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnassign_ProjectNull_T3() {
        company.unassign(worker, null);
    }

    @Test
    public void testUnassign_WorkerNotAssigned_T4() {
        company.unassign(worker, project);

        assertFalse(worker.getProjects().contains(project));
        assertFalse(project.getWorkers().contains(worker));
        assertTrue(company.getUnassignedWorkers().contains(worker));
    }

    @Test
    public void testUnassign_ProjectRequirementsNotMet_T6() {
        company.assign(worker, project);
        project.setStatus(ProjectStatus.ACTIVE);
        company.unassign(worker, project);

        assertFalse(worker.getProjects().contains(project));
        assertFalse(project.getWorkers().contains(worker));
        assertEquals(ProjectStatus.SUSPENDED, project.getStatus());
        assertTrue(company.getUnassignedWorkers().contains(worker));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnassign_WorkerNotInCompany() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(java);
        Worker outsideWorker = new Worker("Gegee", qs, 50000);

        company.unassign(outsideWorker, project);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnassign_ProjectNotInCompany() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(java);
        Project outsideProject = new Project("Gegee", qs, ProjectSize.SMALL);

        company.unassign(worker, outsideProject);
    }

    @Test
    public void testUnassign_ProjectHasWorkerOnly() {
        project.addWorker(worker);
        company.unassign(worker, project);
        assertFalse(worker.getProjects().contains(project));
    }

    @Test
    public void testUnassign_WorkerHasProjectOnly() {
        worker.addProject(project);
        company.unassign(worker, project);

        assertFalse(project.getWorkers().contains(worker));
    }

    @Test
    public void testUnassign_ActiveProject_BecomesUnsatisfied_SuspendedStatus() {
        Set<Qualification> worker2Qs = new HashSet<>();
        worker2Qs.add(python);

        Worker worker2 = company.createWorker("Worker2", worker2Qs, 60000);

        Set<Qualification> qs = new HashSet<>();
        qs.add(java);
        qs.add(python);

        Project multiQProject = company.createProject("MultiQ", qs, ProjectSize.MEDIUM);

        company.assign(worker, multiQProject);
        company.assign(worker2, multiQProject);

        multiQProject.setStatus(ProjectStatus.ACTIVE);

        company.unassign(worker, multiQProject);

        assertEquals(ProjectStatus.SUSPENDED, multiQProject.getStatus());
        assertFalse(multiQProject.getWorkers().contains(worker));
    }

    @Test
    public void testUnassign_PlannedProject_NoSuspend() {
        worker.addQualification(java);
        company.assign(worker, project);
        company.unassign(worker, project);

        assertNotEquals(ProjectStatus.SUSPENDED, project.getStatus());
        assertFalse(project.getWorkers().contains(worker));
    }

    @Test
    public void testUnassign_SuspendsActiveProjectWhenRequirementsMetFails() {
        company.assign(worker, project);
        company.start(project);
        assertEquals(ProjectStatus.ACTIVE, project.getStatus());
        company.unassign(worker, project);
        assertEquals(ProjectStatus.SUSPENDED, project.getStatus());
    }

    @Test
    public void testUnassign_RemainsActiveIfOtherWorkersCoverQualifications() {
        Company c = new Company("UnassignCo");
        Qualification q1 = c.createQualification("Java");
        Qualification q2 = c.createQualification("Python");
        Set<Qualification> projectQs = new HashSet<>(Arrays.asList(q1, q2));
        Project p = c.createProject("ActiveProj", projectQs, ProjectSize.SMALL);
        Worker w1 = c.createWorker("Alice", new HashSet<>(Arrays.asList(q1)), 50000);
        Worker w2 = c.createWorker("Bob", projectQs, 50000);
        c.assign(w1, p);
        c.assign(w2, p);
        c.start(p);
        c.unassign(w1, p);
        assertEquals(ProjectStatus.ACTIVE, p.getStatus());
    }

    // finish() tests

    private Set<Qualification> validQualifications() {
        Set<Qualification> set = new HashSet<>();
        set.add(java);
        set.add(python);
        return set;
    }
    
    @Test
    public void testFinish_Base_ActiveProjectWithWorkers_T1() {
        Company company = new Company("MyCompany");

        Qualification software = company.createQualification("SOFTWARE");
        Qualification testing = company.createQualification("TESTING");

        Set<Qualification> quals = new HashSet<>();
        quals.add(software);
        quals.add(testing);

        Worker w1 = company.createWorker("Alice", quals, 50000);
        Worker w2 = company.createWorker("Bob", quals, 60000);

        Project p = company.createProject("Project1", quals, ProjectSize.MEDIUM);
        p.setStatus(ProjectStatus.ACTIVE);

        p.addWorker(w1);
        p.addWorker(w2);
        w1.addProject(p);
        w2.addProject(p);

        company.finish(p);

        assertEquals(ProjectStatus.FINISHED, p.getStatus());
        assertTrue(p.getWorkers().isEmpty());
        assertFalse(w1.getProjects().contains(p));
        assertFalse(w2.getProjects().contains(p));
    }

    @Test
    public void testFinish_ActiveProjectNoWorkers_T2() {
        Company company = new Company("MyCompany");
        Set<Qualification> quals = validQualifications();

        Project p = company.createProject("Project2", quals, ProjectSize.SMALL);
        p.setStatus(ProjectStatus.ACTIVE);

        company.finish(p);

        assertEquals(ProjectStatus.FINISHED, p.getStatus());
        assertTrue(p.getWorkers().isEmpty());
    }

    @Test
    public void testFinish_FinishedProjectReturnsEarly_T3() {
        Company company = new Company("MyCompany");
        Set<Qualification> quals = validQualifications();

        Project p = company.createProject("Project3", quals, ProjectSize.BIG);
        p.setStatus(ProjectStatus.FINISHED);

        company.finish(p);

        assertEquals(ProjectStatus.FINISHED, p.getStatus());
        assertTrue(p.getWorkers().isEmpty());
    }

    @Test
    public void testFinish_SuspendedProjectReturnsEarly_T4() {
        Company company = new Company("MyCompany");
        Set<Qualification> quals = validQualifications();

        Project p = company.createProject("Project4", quals, ProjectSize.MEDIUM);
        p.setStatus(ProjectStatus.SUSPENDED);

        company.finish(p);

        assertEquals(ProjectStatus.SUSPENDED, p.getStatus());
        assertTrue(p.getWorkers().isEmpty());
    }

    @Test
    public void testFinish_PlannedProjectReturnsEarly_T5() {
        Company company = new Company("MyCompany");
        Set<Qualification> quals = validQualifications();

        Project p = company.createProject("Project5", quals, ProjectSize.SMALL);

        company.finish(p);

        assertEquals(ProjectStatus.PLANNED, p.getStatus());
        assertTrue(p.getWorkers().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFinish_NullProjectThrows_T6() {
        Company company = new Company("MyCompany");
        company.finish(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFinish_ProjectNotInCompanyThrows() {
        Company company = new Company("MyCompany");

        Qualification software = company.createQualification("SOFTWARE");
        Qualification testing = company.createQualification("TESTING");
        Set<Qualification> quals = new HashSet<>();
        quals.add(software);
        quals.add(testing);

        Project unknownProject = new Project("UnknownProject", quals, ProjectSize.SMALL);

        company.finish(unknownProject);
    }

    @Test
    public void finish_ActiveProject_FinishesAndRemovesWorkers_Test() {
        Set<Qualification> projectQs = new HashSet<>();
        projectQs.add(java);
        projectQs.add(python);

        Project p = company.createProject("Project", projectQs, ProjectSize.SMALL);

        Set<Qualification> qs1 = new HashSet<>();
        qs1.add(java);

        Set<Qualification> qs2 = new HashSet<>();
        qs2.add(python);

        Worker w1 = company.createWorker("Tom", qs1, 50000);
        assertNotNull("Worker 1 creation failed", w1);
        Worker w2 = company.createWorker("Tim", qs2, 60000);
        assertNotNull("Worker 2 creation failed", w2);

        company.assign(w1, p);
        company.assign(w2, p);

        company.start(p);

        company.finish(p);

        assertEquals(ProjectStatus.FINISHED, p.getStatus());
        assertTrue(p.getWorkers().isEmpty());
        assertFalse(w1.getProjects().contains(p));
        assertFalse(w2.getProjects().contains(p));
    }
    @Test
    public void finish_NotStartedProject_RemainsPlanned_Test() {
        company.assign(worker, project);
    
        assertEquals(ProjectStatus.PLANNED, project.getStatus());
        assertTrue(project.getWorkers().contains(worker));
        assertTrue(worker.getProjects().contains(project));
    
        company.finish(project);
    
        assertEquals(ProjectStatus.PLANNED, project.getStatus());
        assertTrue(project.getWorkers().contains(worker));
        assertTrue(worker.getProjects().contains(project));
    }
    @Test
    public void finish_ProjectOutsideCompany_ThrowsException_Test() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(java);
    
        Project outsideProject = new Project("Outside", qs, ProjectSize.SMALL);
    
        try {
            company.finish(outsideProject);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    // unassignAll() tests

    @Test
    public void testUnassignAll_Base_T1() {
        company.assign(worker, project);
        company.unassignAll(worker);

        assertTrue(worker.getProjects().isEmpty());
        assertFalse(project.getWorkers().contains(worker));
    }

    @Test
    public void testUnassignAll_ZeroOrMultipleProjects_T2() {
        company.unassignAll(worker);
        assertTrue(worker.getProjects().isEmpty());
        
        Project p2 = company.createProject("Proj2", validQualifications(), ProjectSize.MEDIUM);
        worker.addProject(project);
        worker.addProject(p2);
        project.addWorker(worker);
        p2.addWorker(worker);

        company.unassignAll(worker);

        assertTrue(worker.getProjects().isEmpty());
        assertTrue(project.getWorkers().isEmpty());
        assertTrue(p2.getWorkers().isEmpty());
    }

    @Test
    public void testUnassignAll_MissingRequirements_T3() {
        Project p2 = company.createProject("Proj2", validQualifications(), ProjectSize.SMALL);

        project.addWorker(worker);
        p2.addWorker(worker);
        worker.addProject(project);
        worker.addProject(p2);

        company.unassignAll(worker);

        assertFalse(worker.getProjects().contains(project));
        assertFalse(worker.getProjects().contains(p2));
        assertFalse(project.getWorkers().contains(worker));
        assertFalse(p2.getWorkers().contains(worker));
        assertFalse(project.getMissingQualifications().isEmpty() && p2.getMissingQualifications().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnassignAll_NullWorker_T4() {
        company.unassignAll(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnassignAll_WorkerNotInCompany_T5() {
        Worker w2 = new Worker("Bob", validQualifications(), 50000);
        company.unassignAll(w2);
    }

    @Test
    public void testUnassignAll_ProjectNotInCompany_T6() {
        Worker w = company.createWorker("Charlie", validQualifications(), 50000);
        Project externalProject = new Project("External", validQualifications(), ProjectSize.SMALL);

        w.addProject(externalProject);
        externalProject.addWorker(w);
        company.unassignAll(w);

        assertTrue(w.getProjects().contains(externalProject));
        assertTrue(externalProject.getWorkers().contains(w));
    }
    
    // hashCode() tests

    @Test
    public void testHashCode_Unique() {
        Company c1 = new Company("A");
        Company c2 = new Company("B");

        assertTrue(c1.hashCode() != c2.hashCode());
    }

    @Test
    public void testCreateQualification_ReturnsNullOnDuplicate() {
        Qualification first = company.createQualification("C++");
        assertNotNull(first);
        Qualification duplicate = company.createQualification("C++");
        assertNull("Should return null when creating a duplicate qualification", duplicate);
    }
    
    @Test
    public void testCreateQualification_DuplicateIgnoringCase() {
        Company company = new Company("Test");
    
        Qualification q1 = company.createQualification("Java");
        Qualification q2 = company.createQualification("java");
    
        assertNotNull(q1);
        assertNull(q2);
        assertEquals(1, company.getQualifications().size());
    }
    
}
