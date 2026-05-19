package edu.colostate.cs415.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.colostate.cs415.dto.ProjectDTO;

public class ProjectTest {
    private Qualification software;
    private Qualification testing;

    @Before
    public void setup() {
        software = new Qualification("software");
        testing = new Qualification("testing");
    }

    private Set<Qualification> validQualifications() {
        Set<Qualification> set = new HashSet<>();
        set.add(software);
        set.add(testing);
        return set;
    }

    // Project constructor tests
    @Test
    public void testValidProjectSmall_T1() {
        Project p = new Project("CS415", validQualifications(), ProjectSize.SMALL);
        assertEquals(ProjectStatus.PLANNED, p.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName_T2() {
        new Project(null, validQualifications(), ProjectSize.SMALL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyName_T3() {
        new Project("", validQualifications(), ProjectSize.SMALL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhitespaceOnlyName_T4() {
        new Project(" ", validQualifications(), ProjectSize.SMALL);
    }

    @Test
    public void testNameWithWhitespaceInside_T5() {
        Project p = new Project("Software Testing", validQualifications(), ProjectSize.SMALL);
        assertEquals(ProjectStatus.PLANNED, p.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyQualificationSet_T6() {
        Set<Qualification> emptySet = new HashSet<>();
        new Project("CS415", emptySet, ProjectSize.SMALL);
    }

    @Test
    public void testValidProjectMedium_T7() {
        Project p = new Project("CS415", validQualifications(), ProjectSize.MEDIUM);
        assertEquals(ProjectStatus.PLANNED, p.getStatus());
    }

    @Test
    public void testValidProjectBig_T8() {
        Project p = new Project("CS415", validQualifications(), ProjectSize.BIG);
        assertEquals(ProjectStatus.PLANNED, p.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSize_T9() {
        new Project("CS415", validQualifications(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullQualifications_T10() {
        new Project("CS415", null, ProjectSize.SMALL);
    }

    // getStatus() and setStatus() tests

    @Test
    public void testGetStatus_Planned_T1() {
        Project p = new Project("Test Project", validQualifications(), ProjectSize.SMALL);
        assertEquals(ProjectStatus.PLANNED, p.getStatus());
    }

    @Test
    public void testGetSetStatus_Suspended_T2() {
        Project p = new Project("Test Project", validQualifications(), ProjectSize.SMALL);
        p.setStatus(ProjectStatus.SUSPENDED);
        assertEquals(ProjectStatus.SUSPENDED, p.getStatus());
    }

    @Test
    public void testGetSetStatus_Active_T3() {
        Project p = new Project("Test Project", validQualifications(), ProjectSize.SMALL);
        p.setStatus(ProjectStatus.ACTIVE);
        assertEquals(ProjectStatus.ACTIVE, p.getStatus());
    }

    @Test
    public void testGetSetStatus_Finished_T4() {
        Project p = new Project("Test Project", validQualifications(), ProjectSize.SMALL);
        p.setStatus(ProjectStatus.FINISHED);
        assertEquals(ProjectStatus.FINISHED, p.getStatus());
    }

    @Test
    public void testSetStatus_Planned_T1() {
        Project p = new Project("Test Project", validQualifications(), ProjectSize.SMALL);
        p.setStatus(ProjectStatus.PLANNED);
        assertEquals(ProjectStatus.PLANNED, p.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetStatus_Null_T5() {
        Project p = new Project("Test Project", validQualifications(), ProjectSize.SMALL);
        p.setStatus(null);
    }

    // getSize() tests

    @Test
    public void testGetSize_Small_T1() {
        Project p = new Project("Test Project", validQualifications(), ProjectSize.SMALL);
        assertEquals(ProjectSize.SMALL, p.getSize());
    }

    @Test
    public void testGetSize_Medium_T2() {
        Project p = new Project("Test Project", validQualifications(), ProjectSize.MEDIUM);
        assertEquals(ProjectSize.MEDIUM, p.getSize());
    }

    @Test
    public void testGetSize_Big_T3() {
        Project p = new Project("Test Project", validQualifications(), ProjectSize.BIG);
        assertEquals(ProjectSize.BIG, p.getSize());
    }

    // getName() tests

    @Test
    public void testGetName_Normal_T1() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        assertEquals("Gegee", p.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetName_EmptyString_T2() {
        new Project("", validQualifications(), ProjectSize.SMALL);
    }

    @Test
    public void testGetName_MixedWhitespace_T3() {
        Project p = new Project("Gegee Tsogtbaatar", validQualifications(), ProjectSize.SMALL);
        assertEquals("Gegee Tsogtbaatar", p.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetName_OnlyWhitespaceSpace_T4() {
        new Project(" ", validQualifications(), ProjectSize.SMALL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetName_OnlyWhitespaceNewline_T5() {
        new Project("\n", validQualifications(), ProjectSize.SMALL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetName_OnlyWhitespaceTabNewline_T6() {
        new Project("\t\n", validQualifications(), ProjectSize.SMALL);
    }

    // hashCode() tests

    @Test
    public void testHashCode_Base_T1() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        assertEquals("Gegee".hashCode(), p.hashCode());
    }

    @Test
    public void testHashCode_WithWhitespace_T2() {
        Project p = new Project("Gegee Tsogtbaatar", validQualifications(), ProjectSize.SMALL);
        assertEquals("Gegee Tsogtbaatar".hashCode(), p.hashCode());
    }

    // equals() tests

    @Test
    public void testEquals_SameName_T1() {
        Project p1 = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        Project p2 = new Project("Gegee", validQualifications(), ProjectSize.BIG);
        assertEquals(true, p1.equals(p2));
    }

    @Test
    public void testEquals_DifferentName_T2() {
        Project p1 = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        Project p2 = new Project("Other", validQualifications(), ProjectSize.SMALL);
        assertEquals(false, p1.equals(p2));
    }

    @Test
    public void testEquals_MixedWhitespaceName_T3() {
        Project p1 = new Project("Gegee Tsogtbaatar", validQualifications(), ProjectSize.SMALL);
        Project p2 = new Project("Gegee Tsogtbaatar", validQualifications(), ProjectSize.MEDIUM);
        assertEquals(true, p1.equals(p2));
    }

    @Test
    public void testEquals_DifferentLengthNames_T4() {
        Project p1 = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        Project p2 = new Project("Gegee Tsogtbaatar", validQualifications(), ProjectSize.SMALL);
        assertEquals(false, p1.equals(p2));
    }

    @Test
    public void testEquals_NonProject_T5() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        assertEquals(false, p.equals("NotAProject"));
    }

    @Test
    public void testEquals_Null_T6() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        assertEquals(false, p.equals(null));
    }

    @Test
    public void testEqualsSameReference_T7() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        assertTrue(p.equals(p)); 
    }

    // toString() tests

    @Test
    public void testToString_T1() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        assertEquals("Gegee:0:PLANNED", p.toString());
    }

    @Test
    public void testToString_Planned_MultipleWorkers_T2() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);

        p.addWorker(new Worker("W1", validQualifications(), 50));
        p.addWorker(new Worker("W2", validQualifications(), 51));

        assertEquals("Gegee:2:PLANNED", p.toString());
    }

    @Test
    public void testToString_Active_T3() {
        Project p = new Project("Gegee Tsogtbaatar", validQualifications(), ProjectSize.SMALL);

        p.addWorker(new Worker("W1", validQualifications(), 50));
        p.setStatus(ProjectStatus.ACTIVE);

        assertEquals("Gegee Tsogtbaatar:1:ACTIVE", p.toString());
    }

    @Test
    public void testToString_Suspended_T4() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);

        p.addWorker(new Worker("W1", validQualifications(), 50));
        p.setStatus(ProjectStatus.SUSPENDED);

        assertEquals("Gegee:1:SUSPENDED", p.toString());
    }

    @Test
    public void testToString_Finished_T5() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);

        p.addWorker(new Worker("W1", validQualifications(), 50));
        p.setStatus(ProjectStatus.FINISHED);

        assertEquals("Gegee:1:FINISHED", p.toString());
    }

    // addWorker() tests

    @Test
    public void testAddWorker_EmptySet_T1() {
        Worker worker = new Worker("Worker2", validQualifications(), 100);
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        p.addWorker(worker);
        assertTrue(p.getWorkers().contains(worker));
        assertEquals(1, p.getWorkers().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddWorker_Null_T2() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        p.addWorker(null);
    }

    @Test
    public void testAddWorker_NonEmptySet_T3() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        Worker existingWorker = new Worker("Worker2", validQualifications(), 50);
        p.addWorker(existingWorker);

        Worker newWorker = new Worker("Worker3", validQualifications(), 30);
        p.addWorker(newWorker);

        assertTrue(p.getWorkers().contains(existingWorker));
        assertTrue(p.getWorkers().contains(newWorker));
        assertEquals(2, p.getWorkers().size());
    }

    // getWorkers() tests

    @Test
    public void testGetWorkers_EmptySet_T1() {
        Project p = new Project("EmptyProj", validQualifications(), ProjectSize.SMALL);
        Set<Worker> workers = p.getWorkers();
        assertNotNull(workers);
        assertTrue(workers.isEmpty());
    }

    @Test
    public void testGetWorkers_OneWorker_T2() {
        Project p = new Project("OneWorkerProj", validQualifications(), ProjectSize.SMALL);
        Worker worker1 = new Worker("Worker1", validQualifications(), 50);
        p.addWorker(worker1);

        Set<Worker> workers = p.getWorkers();
        assertNotNull(workers);
        assertEquals(1, workers.size());
        assertTrue(workers.stream().anyMatch(w -> w.getName().equals("Worker1")));
    }

    @Test
    public void testGetWorkers_MultipleWorkers_T3() {
        Project p = new Project("MultiWorkerProj", validQualifications(), ProjectSize.SMALL);
        Worker w1 = new Worker("Worker1", validQualifications(), 50);
        Worker w2 = new Worker("Worker2", validQualifications(), 60);
        p.addWorker(w1);
        p.addWorker(w2);

        Set<Worker> workers = p.getWorkers();
        assertNotNull(workers);
        assertEquals(2, workers.size());
        assertTrue(workers.stream().anyMatch(w -> w.getName().equals("Worker1")));
        assertTrue(workers.stream().anyMatch(w -> w.getName().equals("Worker2")));
    }

    // removeWorker() tests

    @Test
    public void testRemoveWorker_Assigned_T1() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        Worker w1 = new Worker("Worker1", validQualifications(), 50);
        p.addWorker(w1);
        assertTrue(p.getWorkers().contains(w1));

        p.removeWorker(w1);

        assertFalse(p.getWorkers().contains(w1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveWorker_NullWorker_T2() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        p.removeWorker(null);
    }

    @Test
    public void testRemoveWorker_NotAssigned_T3() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        Worker w1 = new Worker("Worker1", validQualifications(), 50);
        Worker w2 = new Worker("Worker2", validQualifications(), 60);
        p.addWorker(w1);
        Set<Worker> before = new HashSet<>(p.getWorkers());

        p.removeWorker(w2);

        assertEquals(before, p.getWorkers());
        assertTrue(p.getWorkers().contains(w1));
    }

    // removeAllWorkers() tests

    @Test
    public void testRemoveAllWorkers_Multiple_T1() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        Worker w1 = new Worker("Worker1", validQualifications(), 50);
        Worker w2 = new Worker("Worker2", validQualifications(), 60);
        p.addWorker(w1);
        p.addWorker(w2);
        assertEquals(2, p.getWorkers().size());

        p.removeAllWorkers();

        assertTrue(p.getWorkers().isEmpty());
    }

    @Test
    public void testRemoveAllWorkers_OneWorker_T2() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        Worker w1 = new Worker("Worker1", validQualifications(), 50);
        p.addWorker(w1);
        assertEquals(1, p.getWorkers().size());

        p.removeAllWorkers();

        assertTrue(p.getWorkers().isEmpty());
    }

    @Test
    public void testRemoveAllWorkers_Empty_T3() {
        Project p = new Project("Gegee", validQualifications(), ProjectSize.SMALL);
        assertTrue(p.getWorkers().isEmpty());

        p.removeAllWorkers();

        assertTrue(p.getWorkers().isEmpty());
    }

    // getRequiredQualifications() tests
    @Test
    public void testGetRequiredQualifications_T1_singleton() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        Project p = new Project("CS415", qs, ProjectSize.SMALL);
        Set<Qualification> qs2 = new HashSet<>();
        qs2.add(software);
        assertEquals(qs2, p.getRequiredQualifications());
    }

    @Test
    public void testGetRequiredQualifications_T2_twoQualifications() {
        Project p = new Project("CS415", validQualifications(), ProjectSize.SMALL);
        assertEquals(validQualifications(), p.getRequiredQualifications());
    }

    @Test
    public void testGetRequiredQualifications_T3_mutableArgumentChanged() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        Project p = new Project("CS415", qs, ProjectSize.SMALL);
        Set<Qualification> qs2 = new HashSet<>();
        qs2.add(software);
        assertEquals(qs2, p.getRequiredQualifications());
        qs.add(testing);
        assertEquals(qs2, p.getRequiredQualifications());
    }

    // addQualiciation() tests

    @Test
    public void testAddQualifications_T1_addNormal() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        Project p = new Project("CS415", qs, ProjectSize.SMALL);

        p.addQualification(testing);

        Set<Qualification> expected = new HashSet<>();
        expected.add(software);
        expected.add(testing);

        assertEquals(expected, p.getRequiredQualifications());
    }

    @Test
    public void testAddQualifications_T2_addExisting() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        Project p = new Project("CS415", qs, ProjectSize.SMALL);

        p.addQualification(software); // already exists

        Set<Qualification> expected = new HashSet<>();
        expected.add(software);

        assertEquals(expected, p.getRequiredQualifications());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddQualifications_T3_nullQualification() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        Project p = new Project("CS415", qs, ProjectSize.SMALL);

        p.addQualification(null);
    }

    @Test(expected = IllegalStateException.class)
    public void testAddQualifications_T4_addToActiveProject() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        Project p = new Project("CS415", qs, ProjectSize.SMALL);

        p.setStatus(ProjectStatus.ACTIVE);
        p.addQualification(testing);
    }
    
    // getMissingQualifications() tests

    @Test
    public void testGetMissingQualifications_T1_oneMissing() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        Project p = new Project("CS415", qs, ProjectSize.SMALL);
        Set<Qualification> qs2 = new HashSet<>();
        qs2.add(software);        
        assertEquals(qs2, p.getMissingQualifications());
    }

    @Test
    public void testGetMissingQualifications_T2_oneClosed() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        Project p = new Project("CS415", qs, ProjectSize.SMALL);
        p.addWorker(new Worker("Bob Dylan", 
            new HashSet<>(Arrays.asList(software)), 50));

        Set<Qualification> qs2 = new HashSet<>();         
        assertEquals(qs2, p.getMissingQualifications());
    }

    @Test
    public void testGetMissingQualifications_T3_oneOfTwoClosed() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        qs.add(testing);
        Project p = new Project("CS415", qs, ProjectSize.SMALL);
        p.addWorker(new Worker("Bob Dylan", 
            new HashSet<>(Arrays.asList(software)), 50));

        Set<Qualification> qs2 = new HashSet<>();         
        qs2.add(testing);
        assertEquals(qs2, p.getMissingQualifications());
    }

    @Test
    public void testGetMissingQualifications_T4_closedByManyPeople() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        qs.add(testing);
        Project p = new Project("CS415", qs, ProjectSize.SMALL);
        p.addWorker(new Worker("Bob Dylan", 
            new HashSet<>(Arrays.asList(software)), 50));
        p.addWorker(new Worker("Dob Bylan", 
            new HashSet<>(Arrays.asList(testing)), 1500));

        Set<Qualification> qs2 = new HashSet<>();                 
        assertEquals(qs2, p.getMissingQualifications());
    }

    // isHelpful() tests

    @Test
    public void testIsHelpful_T1_noMissingQualifications() {
        Worker rr = new Worker("Worker1", 
            new HashSet<>(Arrays.asList(software)), 50);
        
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        qs.add(testing);

        Project p = new Project("CS415", qs, ProjectSize.SMALL);
        p.addWorker(new Worker("Bob Dylan", 
            qs, 50));
        
        assertFalse(p.isHelpful(rr));
    }

    @Test
    public void testIsHelpful_T2_coversMissingQualification() {
        Worker rr = new Worker("Worker1", 
            new HashSet<>(Arrays.asList(software)), 50);
        
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        qs.add(testing);

        Project p = new Project("CS415", qs, ProjectSize.SMALL);
        p.addWorker(new Worker("Bob Dylan", 
            new HashSet<>(Arrays.asList(testing)), 50));
        
        assertTrue(p.isHelpful(rr));
    }

    @Test
    public void testIsHelpful_T3_notCoversMissingQualification() {
        Worker rr = new Worker("Worker1", 
            new HashSet<>(Arrays.asList(software)), 50);
        
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        qs.add(testing);

        Project p = new Project("CS415", qs, ProjectSize.SMALL);
        p.addWorker(new Worker("Bob Dylan", 
            new HashSet<>(Arrays.asList(software)), 50));
        
        assertFalse(p.isHelpful(rr));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIsHelpful_T5_nullWorker() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(software);
        qs.add(testing);

        Project p = new Project("CS415", qs, ProjectSize.SMALL);
        p.isHelpful(null);
    }

    @Test
    public void testToDto() {
        Project p = new Project("CS415", validQualifications(), ProjectSize.SMALL);
        p.addWorker(new Worker("Bob Dylan", validQualifications(), 50));
        p.addWorker(new Worker("Dob Bylan", validQualifications(), 1500));

        ProjectDTO dto = p.toDTO();
        assertEquals("CS415", dto.getName());
        assertEquals(ProjectSize.SMALL, dto.getSize());
        assertEquals(ProjectStatus.PLANNED, dto.getStatus());

        assertTrue(new HashSet<String>(Arrays.asList(dto.getQualifications()))
                    .containsAll(
                        validQualifications()
                            .stream()
                            .map(q -> q.toString())
                            .collect(java.util.stream.Collectors.toSet())
                    ));
                    
        assertTrue(new HashSet<String>(Arrays.asList(dto.getWorkers()))
            .contains("Bob Dylan"));        
          assertTrue(new HashSet<String>(Arrays.asList(dto.getWorkers()))
            .contains("Dob Bylan"));          
    }
}
