package edu.colostate.cs415.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;

import edu.colostate.cs415.dto.WorkerDTO;

public class WorkerTest {
	private Set<Qualification> validQualifications;
	private Project p1, p2, pBig1, pBig2, pBig3, pBig4;

	@Before
	public void setup() {
		validQualifications = new HashSet<>();
		validQualifications.add(new Qualification("Java"));
		p1 = new Project("Ad Marketing", validQualifications, ProjectSize.SMALL);
		p2 = new Project("Order Tracker", validQualifications, ProjectSize.MEDIUM);
		pBig1 = new Project("Big Project 1", validQualifications, ProjectSize.BIG);
		pBig2 = new Project("Big Project 2", validQualifications, ProjectSize.BIG);
		pBig3 = new Project("Big Project 3", validQualifications, ProjectSize.BIG);
		pBig4 = new Project("Big Project 4", validQualifications, ProjectSize.BIG);
	}

	//Worker Constructor
	@Test
	public void Valid_inputs_constructor_Test() {
		Qualification qualification = new Qualification("Java");
		Set<Qualification> qualificationSet = new HashSet<>();
		qualificationSet.add(qualification);

		Worker worker = new Worker("Alice", qualificationSet, 100.0);

		assertEquals("Alice", worker.getName());
		assertEquals(100.0, worker.getSalary(), 0.0);
		assertEquals(1, worker.getQualifications().size());
		assertEquals(0, worker.getProjects().size());
	}

	@Test
	public void Null_Name_Test(){
		try{
			new Worker(null, new HashSet<>(), 100.0);
			fail();
		} catch (IllegalArgumentException e) {}
	}

	@Test
	public void Empty_Name_Test(){
		try{
			new Worker("", new HashSet<>(), 100.0);
			fail();
		}catch (IllegalArgumentException e){}
	}

	@Test 
	public void Whitespace_Name_Test(){
		try{
			new Worker("   ", new HashSet<>(), 100.0);
			fail();
		}catch (IllegalArgumentException e){}
	}

	@Test 
	public void Null_QualificationSet_Test(){
		try{
			new Worker("Alice", null, 100.0);
			fail();
		}catch (IllegalArgumentException e){}
	}

	@Test(expected = IllegalArgumentException.class)
	public void empty_QualifiactionSet_Test(){
		Worker worker = new Worker("Alice", new HashSet<>(), 100.0);
		assertEquals(0, worker.getQualifications().size());
	}

	@Test 
	public void Negative_Salary_Test(){
		try{
			new Worker("Alice", new HashSet<>(), -1.0);
			fail();
		}catch (IllegalArgumentException e){}
	}
	@Test 
	public void Zero_Salary_Test(){
		Worker worker = new Worker("Alice", validQualifications, 0.0);
		assertEquals(0.0, worker.getSalary(), 0.0);
	}

	//equals(o.Object) Tests=====================================================
	@Test 
	public void equals_null(){
		// Set<Qualification> qualificationSet = new HashSet<>();
		// qualificationSet.add(new Qualification("Java"));
		Worker worker = new Worker("Alice", validQualifications, 100.0);
		assertFalse(worker.equals(null));
	}
	@Test
	public void equals_nonWorker(){
		// Set<Qualification> qualificationSet = new HashSet<>();
		// qualificationSet.add(new Qualification("Java"));
		Worker worker = new Worker("Alice", validQualifications, 100.0);
		assertFalse(worker.equals("Alice"));
	}
	@Test
	public void equals_SameName(){
		Set<Qualification> qualificationSet = new HashSet<>();
		qualificationSet.add(new Qualification("Java"));

		Set<Qualification> qualificationSet2 = new HashSet<>();
		qualificationSet2.add(new Qualification("JUnit"));
		
		Worker w1 = new Worker("Alice", qualificationSet, 100.0);
		Worker w2 = new Worker("Alice", qualificationSet2, 100.0);

		assertTrue(w1.equals(w2));
	}
	@Test
	public void equals_differentName(){
		Set<Qualification> qualificationSet = new HashSet<>();
		qualificationSet.add(new Qualification("Java"));
		Worker w1 = new Worker("Alice", qualificationSet, 100.0);
		Worker w2 = new Worker("Bob", qualificationSet, 100.0);
		assertFalse(w1.equals(w2));
	}
	@Test
	public void equals_itself(){
		Set<Qualification> qualificationSet = new HashSet<>();
		qualificationSet.add(new Qualification("Java"));

		Worker worker = new Worker("Alice", qualificationSet, 100.0);
		assertTrue(worker.equals(worker));
	}

	// hashcode() Tests============================================================
	@Test
	public void hashCodeSameName(){
		Set<Qualification> qualificationSet = new HashSet<>();
    	qualificationSet.add(new Qualification("Java"));

		Worker worker1 =  new Worker("John", qualificationSet, 100);
		Worker worker2 = new Worker("John", qualificationSet, 100);
		assertEquals(worker1.hashCode(), worker2.hashCode());
	}
	@Test
	public void hashCodeDiffName(){
		Set<Qualification> qualificationSet = new HashSet<>();
    	qualificationSet.add(new Qualification("Java"));
		
		Worker worker1 =  new Worker("John", qualificationSet, 100);
		Worker worker2 = new Worker("Bob", qualificationSet, 100);
		assertTrue(worker1.hashCode() != worker2.hashCode());
	}

	// toString() Tests=============================================================
	@Test
	public void toString_base(){
		Worker worker = new Worker("Alice", validQualifications, 100.0);
		assertEquals("Alice:0:1:100", worker.toString());
	}
	@Test
	public void toString_T2() {
		Worker worker = new Worker("Alice", validQualifications, 100.0);
		worker.addProject(p1); 

		assertEquals("Alice:1:1:100", worker.toString());
	}
	@Test
	public void toString_T3() {
		Worker worker = new Worker("Alice", validQualifications, 100.0);
		worker.addProject(p1);
		worker.addProject(p2); 

		assertEquals("Alice:2:1:100", worker.toString());
	}
	@Test
	public void toString_T4() {
		Set<Qualification> qualificationSet = new HashSet<>();
		qualificationSet.add(new Qualification("Java"));

		Worker worker = new Worker("Alice", qualificationSet, 100.0);

		assertEquals("Alice:0:1:100", worker.toString());
	}
	@Test
	public void toString_T5() {
		Set<Qualification> qualificationSet = new HashSet<>();
		qualificationSet.add(new Qualification("Java"));
		qualificationSet.add(new Qualification("JUnit"));

		Worker worker = new Worker("Alice", qualificationSet, 100.0);

		assertEquals("Alice:0:2:100", worker.toString());
	}
	@Test
	public void toString_T6() {
		Worker worker = new Worker("Alice", validQualifications, 100.99);
		assertEquals("Alice:0:1:100", worker.toString());
	}
	
	// getName() Tests =============================================================
	@Test(expected = IllegalArgumentException.class)
	public void testGetNameLengthZeroA1() {
		new Worker("", new HashSet<>(), 100.0);
	}

	@Test 
	public void testGetNameGreaterThanZeroA2() {
		Worker w = new Worker("Mario", validQualifications, 100.00);
		assertTrue(w.getName().length() > 0);
	}

	@Test
	public void testGetNameNoWhitespaceB1() {
		Worker w = new Worker("Bowser", validQualifications, 100.00);
		assertEquals("Bowser", w.getName());
	}

	@Test
	public void testGetNameMixWhitespaceB2() {
		Worker w = new Worker("Princess Peach", validQualifications, 100.00);
		assertEquals("Princess Peach", w.getName());
	}


	@Test(expected = IllegalArgumentException.class)
	public void testGetNameOnlyWhitespaceB3_Space() {
		new Worker(" ", validQualifications, 100.00);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNameOnlyWhitespaceB3_NewLine() {
		new Worker("\n", validQualifications, 100.00);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNameOnlyWhitespaceB3_TabNewLine() {
		new Worker("\t\n", validQualifications, 100.00);
	}

	// getSalary() Tests ============================================================
	@Test(expected = IllegalArgumentException.class)
	public void testGetSalaryNegativeA1() {
		new Worker("Waluigi", validQualifications, -100.00);
	}

	@Test
	public void testGetSalaryZeroA2() {
		Worker w = new Worker("Wario", validQualifications, 0.0);
		assertEquals(0.0, w.getSalary(), 0.0);
	}

	@Test
	public void testGetSalaryPositiveA3() {
		Worker w = new Worker("Luigi", validQualifications, 160000.00);
		assertEquals(160000.00, w.getSalary(), 0.0);
	}

	// setSalary(double) Tests ============================================================
	@Test(expected = IllegalArgumentException.class)
	public void testSetSalaryNegativeA1() {
		Worker w = new Worker("Toad", validQualifications, 500.00);
		w.setSalary(-100.00);
	}

	@Test
	public void testSetSalaryZeroA2() {
		Worker w = new Worker("Toadette", validQualifications, 500.00);
		w.setSalary(0.0);
		assertEquals(0.0, w.getSalary(), 0.0);
	}

	@Test
	public void testSetSalaryPositiveA3() {
		Worker w = new Worker("Yoshi", validQualifications, 500.00);
		w.setSalary(160000.00);
		assertEquals(160000.00, w.getSalary(), 0.0);
	}

	// getQualifications() Tests ============================================================
	@Test(expected = IllegalArgumentException.class)
	public void testGetQualificationsEmpty() {
		Worker w = new Worker("Daisy", new HashSet<>(), 500.00);
		assertTrue(w.getQualifications().isEmpty());
	}

	@Test
	public void testGetQualificationsNonEmpty() {
		Set<Qualification> qs = new HashSet<>();
		qs.add(new Qualification("Java"));
		qs.add(new Qualification("JUnit"));
		Worker w = new Worker("Boo", qs, 500.00);	
		assertEquals(2, w.getQualifications().size());	
	}

	// addQualification(Qualification) Tests ============================================================
	@Test(expected = IllegalArgumentException.class)
	public void testAddQualificationToEmpty() {
		Worker w = new Worker("Rosaline", new HashSet<>(), 500.00);
		w.addQualification(new Qualification("Java"));
		assertEquals(1, w.getQualifications().size());
	}

	@Test
	public void testAddQualificationToNonEmpty() {
		Set<Qualification> qs = new HashSet<>();
		qs.add(new Qualification("Java"));
		Worker w = new Worker("Donkey Kong", qs, 500.00);
		w.addQualification(new Qualification("JUnit"));
		assertEquals(2, w.getQualifications().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddQualificationNull() {
		Worker w = new Worker("Diddy Kong", validQualifications, 500.00);
		w.addQualification(null);
	}

	@Test
	public void testAddQualificationDuplicate() {
		Qualification q = new Qualification("Java");
		Set<Qualification> qs = new HashSet<>();
		qs.add(q);
		Worker w = new Worker("Pauline", qs, 500.00);
		w.addQualification(q);
		assertEquals(1, w.getQualifications().size());
	}

	// getProjects() Tests ============================================================
	@Test
	public void testGetProjectsEmpty() {
		Worker w = new Worker("Dixie Kong", validQualifications, 500.00);
		assertTrue(w.getProjects().isEmpty());
	}

	@Test
	public void testGetProjectsMultiple() {
		Worker w = new Worker("Cranky Kong", validQualifications, 500.00);
		w.addProject(p1);
		w.addProject(p2);
		assertEquals(2, w.getProjects().size());
	}

	// addProject(Project) Tests ============================================================
	@Test(expected = IllegalArgumentException.class)
	public void testAddProjectNull() {
		Worker w = new Worker("Goomba", validQualifications, 500.00);
		w.addProject(null);
	}

	@Test
	public void testAddProjectValid() {
		Worker w = new Worker("Koopa Troopa", validQualifications, 500.00);
		w.addProject(p1);
		assertTrue(w.getProjects().contains(p1));
	}

	// removeProject(Project) Tests ============================================================
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveProjectNull() {
		Worker w = new Worker("Piranha Plant", validQualifications, 500.00);
		w.removeProject(null);
	}

	@Test
	public void testRemoveProjectInSet() {
		Worker w = new Worker("Luma", validQualifications, 500.00);
		w.addProject(p1);
		w.removeProject(p1);
		assertFalse(w.getProjects().contains(p1));
	}
	
	@Test
	public void testRemoveProjectNotInSet() {
		Worker w = new Worker("Birdo", validQualifications, 500.00);
		w.addProject(p1);
		w.removeProject(p2);
		assertEquals(1, w.getProjects().size());
	}

	// getWorkload() Tests ============================================================
	@Test
	public void testGetWorkloadOnlyFinished() {
		Worker w = new Worker("Sonic", validQualifications, 500.00);
		p1.setStatus(ProjectStatus.FINISHED);
		w.addProject(p1);
		assertEquals(0, w.getWorkload());
	}

	@Test
	public void testGetWorkloadMixed() {
		Worker w = new Worker("Tails", validQualifications, 500.00);
		p1.setStatus(ProjectStatus.ACTIVE);
		pBig1.setStatus(ProjectStatus.FINISHED);
		w.addProject(p1);
		w.addProject(pBig1);
		assertEquals(1, w.getWorkload());
	}

	@Test
	public void testGetWorkloadAtLimit() {
		Worker w = new Worker("Knuckles", validQualifications, 500.00);
		w.addProject(pBig1);
		w.addProject(pBig2);
		w.addProject(pBig3);
		w.addProject(pBig4);
		assertEquals(12, w.getWorkload());
	}

	@Test
	public void testGetWorkloadOverLimit() {
		Worker w = new Worker("Shadow", validQualifications, 500.00);
		w.addProject(pBig1);
		w.addProject(pBig2);
		w.addProject(pBig3);
		w.addProject(pBig4);
		w.addProject(p1);
		assertEquals(13, w.getWorkload());
	}

	// willOverload(Project) Tests ============================================================
	@Test
	public void testWillOverloadSmall() {
		Worker w = new Worker("Amy", validQualifications, 500.00);
		w.addProject(pBig1);
		w.addProject(pBig2);
		w.addProject(pBig3);
		w.addProject(p1);
		assertFalse(w.willOverload(p1));
	}
	
	@Test
	public void testWillOverloadAtLimit() {
		Worker w = new Worker("Rouge", validQualifications, 500.00);
		w.addProject(pBig1);
		w.addProject(pBig2);
		w.addProject(pBig3);
		w.addProject(p2);
		assertFalse(w.willOverload(p1));
	}

	@Test
	public void testWillOverloadOverLimit() {
		Worker w = new Worker("Silver", validQualifications, 500.00);
		w.addProject(pBig1);
		w.addProject(pBig2);
		w.addProject(pBig3);
		w.addProject(p1);
		assertTrue(w.willOverload(pBig4));
	}

	@Test
	public void testWillOverloadAlreadyAssigned() {
		Worker w = new Worker("Blaze", validQualifications, 500.00);
		w.addProject(p1);
		assertFalse(w.willOverload(p1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWillOverloadNull() {
		Worker w = new Worker("Metal Sonic", validQualifications, 500.00);
		w.willOverload(null);
	}

	@Test
	public void testWillOverloadAlreadyAssignedOverLimit() {
		Worker w = new Worker("Dr. Eggman", validQualifications, 500.00);
		w.addProject(pBig1);
		w.addProject(pBig2);
		w.addProject(pBig3);
		w.addProject(pBig4);
		w.addProject(p1);
		assertTrue(w.willOverload(p1));
	}

	@Test
	public void testWillOverloadExactlyAtLimitAlreadyAssigned() {
		Worker w = new Worker("Shadow", validQualifications, 500.00);
		w.addProject(pBig1);
		w.addProject(pBig2);
		w.addProject(pBig3);
		w.addProject(pBig4);
		assertFalse(w.willOverload(pBig1));
	}

	// isAvailable() Tests ============================================================
	@Test
	public void testIsAvailableTrue() {
		Worker w = new Worker("Espio", validQualifications, 500.00);
		w.addProject(pBig1);
		w.addProject(pBig2);
		w.addProject(pBig3);
		w.addProject(p2);
		assertTrue(w.isAvailable());
	}

	@Test
	public void testIsAvailableAtLimit() {
		Worker w = new Worker("Vector", validQualifications, 500.00);
		w.addProject(pBig1);
		w.addProject(pBig2);
		w.addProject(pBig3);
		w.addProject(pBig4);
		assertFalse(w.isAvailable());
	}

	@Test
	public void testIsAvailableOverLimit() {
		Worker w = new Worker("Charmy", validQualifications, 500.00);
		w.addProject(pBig1);
		w.addProject(pBig2);
		w.addProject(pBig3);
		w.addProject(pBig4);
		w.addProject(p1);
		assertFalse(w.isAvailable());
	}

	// toDTO() Tests ============================================================
	@Test
	public void testToDTOBase() {
		Worker w = new Worker("Link", validQualifications, 500.00);
		WorkerDTO dto = w.toDTO();
		assertEquals("Link", dto.getName());
		assertEquals(500.00, dto.getSalary(), 0.0);
		assertEquals(0, dto.getWorkload());
		assertEquals(0, dto.getProjects().length);
		assertEquals(1, dto.getQualifications().length);
		assertEquals("Java", dto.getQualifications()[0]);
	}

	@Test
	public void testToDTOWithProjects() {
		Worker w = new Worker("Zelda", validQualifications, 500.00);
		w.addProject(p1);
		WorkerDTO dto = w.toDTO();
		assertEquals(1, dto.getWorkload());
		assertEquals(1, dto.getProjects().length);
		assertEquals("Ad Marketing", dto.getProjects()[0]);
		assertEquals(1, dto.getQualifications().length);
	}

	@Test
	public void testToDTOMultipleQualifications() {
		Set<Qualification> qs = new HashSet<>();
		qs.add(new Qualification("Java"));
		qs.add(new Qualification("JUnit"));
		Worker w = new Worker("Ganondorf", qs, 500.00);
		WorkerDTO dto = w.toDTO();
		assertEquals(2, dto.getQualifications().length);
		List<String> qualificationsList = Arrays.asList(dto.getQualifications());
		assertTrue(qualificationsList.contains("Java"));
		assertTrue(qualificationsList.contains("JUnit"));
		assertEquals(0, dto.getProjects().length);
	}

	@Test
	public void testToDTOMultipleProjects() {
		Worker w = new Worker("Zelda", validQualifications, 500.00);
		w.addProject(p1);
		w.addProject(p2);
		WorkerDTO dto = w.toDTO();
		assertEquals(2, dto.getProjects().length);
		List<String> projectsList = Arrays.asList(dto.getProjects());
		assertTrue(projectsList.contains("Ad Marketing"));
		assertTrue(projectsList.contains("Order Tracker"));
	}

}	