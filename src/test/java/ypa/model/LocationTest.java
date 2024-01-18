package ypa.model;

import org.junit.jupiter.api.Test;

import ypa.model.Direction;
import ypa.model.Location;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test cases for {@code Location}.
 *
 * @author Tom Verhoeff (Eindhoven University of Technology)
 */
public class LocationTest {

    /**
     * Test of Location(Scanner) constructor of class Location.
     */
    @Test
    public void testLocationScanner() {
        System.out.println("Location(Scanner)");
        String input = "b 3";
        Scanner scanner = new Scanner(input);
        Location instance = new Location(scanner);
        assertEquals("b 3", instance.toString(), "toString for 'b 3'");

        input = "z10";
        instance = new Location(new Scanner(input));
        assertEquals("z10", instance.toString(), "toString for 'z10'");
    }

}
