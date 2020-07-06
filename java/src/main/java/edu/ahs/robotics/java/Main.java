package edu.ahs.robotics.java;

import org.junit.Test;

import static org.junit.Assert.fail;

public class Main {

    @Test
    public static void testDuplicatesRemoved() {
        // Make some points
        Point[] points = new Point[0];
        try {
            Path path = new Path(points);
            fail("Expected Path() to throw IllegalArgumentException for empty point array");
        } catch (IllegalArgumentException e){

        }
    }



    public static void main(String arg[]){

        testDuplicatesRemoved();

    }
}
