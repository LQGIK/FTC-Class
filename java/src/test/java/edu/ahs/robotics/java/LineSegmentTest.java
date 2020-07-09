package edu.ahs.robotics.java;

import org.junit.Test;

import static org.junit.Assert.*;

public class LineSegmentTest {

    @Test
    public void testInterpolate() {
        Point a = new Point(0, 0);
        Point b = new Point(3, 4);
        LineSegment line = new LineSegment(a, b);
        Point c = line.interpolate(b,-5);
        assertEquals(a, c);
    }
}