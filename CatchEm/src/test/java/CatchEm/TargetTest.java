/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CatchEm;

import CatchEm.ID;
import CatchEm.Target;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Johannes
 */
public class TargetTest {
    Target t = new Target(30, 30, ID.Target);
    Target t2 = new Target(30 , 30, ID.Target);

    public TargetTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getXWorks() {
        assertEquals(30, t.getX());
    }
    
    @Test
    public void getYWorks() {
        assertEquals(30, t.getY());
    }
    
    @Test
    public void getIdWorks(){
        assertEquals(t.getId(), t.getId());
    }
    
    @Test
    public void healthIsZero(){
        for (int i = 0; i <= 100; i++) {
            t.removeHealth();
        }
        assertEquals(true, t.removeHealth());
    }
    
    @Test
    public void getHitpointsWorks() {
        assertEquals(100, t.getHitpoints());
    }
    
    @Test
    public void getNameWorks(){
        assertEquals("target", t.getName());
    }
    
    @Test
    public void toStringWorks(){
        assertEquals("Target: target, remaining hitpoints: 100" , t.toString());
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
