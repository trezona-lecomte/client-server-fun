package test.suncertify.db;

import main.suncertify.db.Contractor;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for {@link main.suncertify.db.Contractor}.
 */
public class ContractorTest {

    private Contractor contractorA;
    private Contractor contractorB;
    private Contractor contractorC;

    @Before
    public void setUp() throws Exception {
        contractorA = new Contractor();
        contractorB = new Contractor();
        contractorC = new Contractor();
        contractorA.setName("McFarlane Contracting");
        contractorB.setName("McFarlane Contracting");
        contractorC.setName("Andy's Contracting");
    }

    @Test
    public void testEqualsSymmetric() throws Exception {
        assertTrue(contractorA.equals(contractorB) && contractorB.equals(contractorA));
        assertFalse(contractorA.equals(contractorC) && contractorC.equals(contractorA));
    }

    @Test
    public void testEqualsReflexive() throws Exception {
        assertTrue(contractorA.equals(contractorA));
        assertTrue(contractorB.equals(contractorB));
        assertTrue(contractorC.equals(contractorC));
    }

    @Test
    public void testEqualsTransitive() throws Exception {
        contractorC.setName("McFarlane Contracting");
        assertTrue(contractorA.equals(contractorB));
        assertTrue(contractorB.equals(contractorC));
        assertTrue(contractorA.equals(contractorC));
    }

    @Test
    public void testEqualsConsistent() throws Exception {
        contractorC.setName("Jerry's Contracting");
        assertTrue(contractorA.equals(contractorB));
        assertTrue(contractorA.equals(contractorB));
        assertTrue(contractorA.equals(contractorB));
        assertFalse(contractorA.equals(contractorC));
        assertFalse(contractorA.equals(contractorC));
        assertFalse(contractorA.equals(contractorC));
    }

    @Test
    public void testEqualsNull() throws Exception {
        assertFalse(contractorA.equals(null));
    }

    @Test
    public void testHashCodeEqual() throws Exception {
        assertEquals(contractorA.hashCode(), contractorB.hashCode());
    }

    @Test
    public void testHashCodeConsistent() throws Exception {
        int a = contractorA.hashCode();
        int b = contractorA.hashCode();
        int c = contractorA.hashCode();
        assertEquals(a, b);
        assertEquals(a, c);
        assertEquals(a, contractorA.hashCode());
    }

    @Test
    public void testSetAndGetName() throws Exception {
        contractorA.setName("Johnny's Contracting");
        assertEquals("Johnny's Contracting", contractorA.getName());
    }

    @Test
    public void testSetAndGetLocation() throws Exception {
        contractorA.setLocation("Wellington");
        assertEquals("Wellington", contractorA.getLocation());
    }

    @Test
    public void testSetAndGetSpecialties() throws Exception {
        String[] specialties = {"Drainlaying, plubming, building, carpeting"};
        contractorA.setSpecialties(specialties);
        assertEquals("Drainlaying, plubming, building, carpeting", contractorA.getSpecialties());
    }

    @Test
    public void testSetAndGetSize() throws Exception {
        contractorA.setSize(20.05);
        assertEquals("20.05", contractorA.getSize());

    }

    @Test
    public void testSetAndGetRate() throws Exception {
        contractorA.setRate(50.5);
        assertEquals("50.5", contractorA.getRate());

    }

    @Test
    public void testSetAndGetOwner() throws Exception {
        contractorA.setOwner(1234);
        assertEquals("1234", contractorA.getOwner());

    }
}