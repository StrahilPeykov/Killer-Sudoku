package ypa.model;

import org.junit.jupiter.api.Test;

import ypa.model.HSpec;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for {@code HSpec}.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class HSpecTest {

    /**
     * Test of getLength method, of class HSpec.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
        HSpec instance = new HSpec(3);
        int expResult = 3;
        int result = instance.getLength();
        assertEquals(expResult, result, "Result");
    }

    /**
     * Test of toString method, of class HSpec.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        HSpec instance = new HSpec(3);
        String expResult = "Length: 3";
        String result = instance.toString();
        assertEquals(expResult, result, "Result");
    }

    /**
     * Test of toStringLong method, of class HSpec.
     */
    @Test
    public void testToStringLong() {
        System.out.println("toStringLong");
        HSpec instance = new HSpec(3);
        String expResult = "{length: 3 }";
        String result = instance.toStringLong();
        assertEquals(expResult, result, "Result");
    }

}
