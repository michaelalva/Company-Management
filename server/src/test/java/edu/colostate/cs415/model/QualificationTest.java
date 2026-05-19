package edu.colostate.cs415.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.HashSet;
import java.util.Set;
import edu.colostate.cs415.dto.QualificationDTO;
import org.junit.Test;
import static org.junit.Assert.*;

public class QualificationTest {
	@Test
    public void constructor_throws_whenNull() {
        try {
            new Qualification(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void constructor_throws_whenEmpty() {
        try {
            new Qualification("");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void constructor_throws_whenWhitespaceOnly() {
        try {
            new Qualification(" \n\t ");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {}
    }

	@Test
	public void QualificationTest_setValid(){
		Qualification qualification = new Qualification("Java");
		assertEquals("Java", qualification.toString());
	}

	@Test
	public void QualificationTest_validWhiteSpace(){
		Qualification qualification = new Qualification("software testing");
		assertEquals("software testing", qualification.toString());
	}

    // equals(qualification Object) Tests========================================================

    @Test
    public void equals_Base() {
        Qualification qualification = new Qualification("Java");
        Object o = new Qualification("Java");
        assertTrue(qualification.equals(o));
    }
    @Test
    public void equals_T2() {
        Qualification qualification = new Qualification("Java");
        Object o = null;
        assertFalse(qualification.equals(o));
    }

    @Test
    public void equals_T3() {
        Qualification qualification = new Qualification("Java");
        Object o = "Java";
        assertFalse(qualification.equals(o));
    }

    @Test
    public void equals_T4() {
        Qualification qualification = new Qualification("Java");
        Object o = new Qualification("Python");
        assertFalse(qualification.equals(o));
    }
    @Test
    public void equals_T5() {
        Qualification qualificiation = new Qualification("Java");
        Object o = qualificiation;
        assertTrue(qualificiation.equals(o));
    }
    
    // hashCode() Tests==============================================================

    @Test
    public void hashCode_Base(){
        Qualification qualification1 = new Qualification("description");
        Qualification qualification2 = new Qualification("description");
        assertTrue(qualification1.equals(qualification2));
        assertEquals(qualification1.hashCode(), qualification2.hashCode());
    }
    @Test
    public void hashCode_T2() {
        Qualification qualification1 = new Qualification("description");
        Qualification qualification2 = new Qualification("different description");
        assertNotEquals(qualification1.hashCode(), qualification2.hashCode());
    }

    // toString() Tests=================================================================

    @Test
    public void toString_Base(){
        Qualification qualification = new Qualification("Java");
        assertEquals("Java", qualification.toString());
    }
    @Test
    public void toString_T2() {
        Qualification qualification = new Qualification("Software testing");
        assertEquals("Software testing", qualification.toString());
    }

    // getWorker() Tests=================================================================

    @Test
    public void getWorkers_base() {
        Qualification qualification = new Qualification("Java");

        Set<Worker> result = qualification.getWorkers();

        assertTrue(result.isEmpty());
    }

    @Test
    public void getWorkers_T2(){
        Qualification qualification = new Qualification("Java");
    
        Set<Qualification> qualificationSet = new HashSet<>();
        qualificationSet.add(qualification);
        Worker w1 = new Worker("Alice", qualificationSet, 100.0);
    
        qualification.addWorker(w1);
    
        Set<Worker> result = qualification.getWorkers();
        assertEquals(1, result.size());
        assertTrue(result.contains(w1));
    }
    
    @Test
    public void getWorkers_T3() {
        Qualification qualification = new Qualification("Java");
    
        Set<Qualification> qualificationSet1 = new HashSet<>();
        qualificationSet1.add(qualification);
        Worker w1 = new Worker("Alice", qualificationSet1, 100.0);
    
        Set<Qualification> qualificationSet2 = new HashSet<>();
        qualificationSet2.add(qualification);
        Worker w2 = new Worker("Bob", qualificationSet2, 120.0);
    
        qualification.addWorker(w1);
        qualification.addWorker(w2);
    
        Set<Worker> result = qualification.getWorkers();
        assertEquals(2, result.size());
        assertTrue(result.contains(w1));
        assertTrue(result.contains(w2));
    }
    // addWorker() Tests=================================================================

    @Test
    public void addWorker_base(){
        Qualification qualification = new Qualification("Java");

        try {
            qualification.addWorker(null);
            fail();
        } catch (IllegalArgumentException e) {}
    }

    @Test
    public void addWorker_T2() {
        Qualification qualification = new Qualification("Java");

        Set<Qualification> qualificationSet = new HashSet<>();
        qualificationSet.add(qualification);
        Worker w1 = new Worker("Alice", qualificationSet, 100.0);

        qualification.addWorker(w1);

        assertEquals(1, qualification.getWorkers().size());
        assertTrue(qualification.getWorkers().contains(w1));
    }

    @Test
    public void addWorker_T3() {
        Qualification qualification = new Qualification("Java");

        Set<Qualification> qualificationSet = new HashSet<>();
        qualificationSet.add(qualification);
        Worker w1 = new Worker("Alice", qualificationSet, 100.0);

        qualification.addWorker(w1);
        qualification.addWorker(w1);

        assertEquals(1, qualification.getWorkers().size());
    }

    //removeWorkers()  Tests=======================================================

    @Test
    public void removeWorkers_base(){
        Qualification qualification = new Qualification("Java");

		Set<Qualification> qualificationSet1 = new HashSet<>();
		qualificationSet1.add(qualification);
		Worker w1 = new Worker("Alice", qualificationSet1, 100.0);

		Set<Qualification> qualificationSet2 = new HashSet<>();
		qualificationSet2.add(qualification);
		Worker w2 = new Worker("Bob", qualificationSet2, 100.0);

		qualification.addWorker(w1);
		qualification.addWorker(w2);

		assertTrue(qualification.getWorkers().contains(w1));
		assertTrue(qualification.getWorkers().contains(w2));

		qualification.removeWorker(w1);

		assertFalse(qualification.getWorkers().contains(w1));
		assertTrue(qualification.getWorkers().contains(w2));
		assertEquals(1, qualification.getWorkers().size());
    }

    @Test
    public void removeWorker_T2(){
        Qualification qualification = new Qualification("Java");

        try{
            qualification.removeWorker(null);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }
    
    @Test
    public void removeWorker_T3(){
        Qualification qualification = new Qualification("Java");

		Set<Qualification> qualificationSet1 = new HashSet<>();
		qualificationSet1.add(qualification);
		Worker w1 = new Worker("Alice", qualificationSet1, 100.0);

		Set<Qualification> qualificationSet2 = new HashSet<>();
		qualificationSet2.add(qualification);
		Worker w2 = new Worker("Bob", qualificationSet2, 100.0);

		qualification.addWorker(w1);

		assertTrue(qualification.getWorkers().contains(w1));
		assertFalse(qualification.getWorkers().contains(w2));

		qualification.removeWorker(w2);

		assertTrue(qualification.getWorkers().contains(w1));
		assertFalse(qualification.getWorkers().contains(w2));
		assertEquals(1, qualification.getWorkers().size());
	}

	@Test
	public void removeWorker_T4(){
		Qualification qualification = new Qualification("Java");

		Set<Qualification> qualificationSet = new HashSet<>();
		qualificationSet.add(qualification);
		Worker w1 = new Worker("Alice", qualificationSet, 100.0);

		assertEquals(0, qualification.getWorkers().size());

		qualification.removeWorker(w1);

		assertEquals(0, qualification.getWorkers().size());
		assertFalse(qualification.getWorkers().contains(w1));
	}

	@Test
	public void removeWorker_T5(){
		Qualification qualification = new Qualification("Java");

		Set<Qualification> qualificationSet = new HashSet<>();
		qualificationSet.add(qualification);
		Worker w1 = new Worker("Alice", qualificationSet, 100.0);

		qualification.addWorker(w1);

		assertEquals(1, qualification.getWorkers().size());
		assertTrue(qualification.getWorkers().contains(w1));

		qualification.removeWorker(w1);

		assertEquals(0, qualification.getWorkers().size());
		assertFalse(qualification.getWorkers().contains(w1));
	}
    // toDTO() Tests============================================================================
    @Test
    public void toDTO_base(){
        Qualification qualification = new Qualification("Java");

        QualificationDTO dto = qualification.toDTO();

        assertEquals("Java", dto.getDescription());
        assertNotNull(dto.getWorkers());
        assertEquals(0, dto.getWorkers().length);
    }
    @Test
    public void toDTO_T2() {
        Qualification qualification = new Qualification("Java");

        Set<Qualification> qualificationSet = new HashSet<>();
        qualificationSet.add(qualification);

        Worker w1 = new Worker("Alice", qualificationSet, 100.0);
        qualification.addWorker(w1);

        QualificationDTO dto = qualification.toDTO();

        assertEquals("Java", dto.getDescription());
        assertEquals(1, dto.getWorkers().length);
        assertEquals("Alice", dto.getWorkers()[0]);
    }

    @Test
    public void toDTO_T3() {
        Qualification qualification = new Qualification("Java");

        Set<Qualification> qualificationSet = new HashSet<>();
        qualificationSet.add(qualification);

        Worker w1 = new Worker("Alice", qualificationSet, 100.0);
        Worker w2 = new Worker("Bob", qualificationSet, 120.0);

        qualification.addWorker(w1);
        qualification.addWorker(w2);

        QualificationDTO dto = qualification.toDTO();

        assertEquals("Java", dto.getDescription());
        assertEquals(2, dto.getWorkers().length);

        String[] names = dto.getWorkers();

        assertTrue(
            (names[0].equals("Alice") && names[1].equals("Bob")) ||
            (names[0].equals("Bob") && names[1].equals("Alice"))
        );
    }
    
}
