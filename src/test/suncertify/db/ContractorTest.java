package test.suncertify.db;

import main.suncertify.db.Contractor;

import org.junit.Test;

import static org.junit.Assert.*;

public class ContractorTest {

    private static final double DELTA = 1e-15;

    private String[] specialties = {"Drainlaying", "Plumbing", "Building"};

    private Contractor contractor = new Contractor("Test Contractor", "Wellington", specialties, 15,  80.00, 12345678);

    @Test
    public void testEqualsTrue() {
        Contractor contractor2 = new Contractor();
        Contractor contractor3 = contractor2;
        assertTrue(contractor2.equals(contractor3));
    }

    @Test
    public void testEqualsFalse() {
        Contractor contractor4 = new Contractor();
        assertFalse(contractor.equals(contractor4));
    }

    @Test
    public void testGetName() {
        assertEquals(contractor.getName(), "Test Contractor");
    }

    @Test
    public void testGetLocation() {
        assertEquals(contractor.getLocation(), "Wellington");
    }

    @Test
    public void testGetSpecialites() {
        assertArrayEquals(contractor.getSpecialties(), specialties);
    }

    @Test
    public void testGetSize() {
        assertEquals(contractor.getSize(), 15, DELTA);
    }

    @Test
    public void testGetRate() {
        assertEquals(contractor.getRate(), 80.00, DELTA);
    }

    @Test
    public void testGetOwner() {
        assertEquals(contractor.getOwner(), 12345678);
    }





}
