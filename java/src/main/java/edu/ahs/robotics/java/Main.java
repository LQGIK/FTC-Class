package edu.ahs.robotics.java;

import org.junit.Test;

import static org.junit.Assert.fail;

public class Main {

    @Test
    public static void testDuplicatesRemoved() {
        // Make some points
        Point a = new Point(0, 0);
        Point b = new Point(0, 0);
        Point c = new Point(3, 4);
        Point d = new Point(3, 2);
        Point e = new Point(3, 3);
        Point f = new Point(3, 3);
        Point g = new Point(3, 3);
        Point h = new Point(0,0);

        Point[] points = {a, b, c, d, e, f, g, h};
        Point[] points1 = new Point[0];

        Path path = new Path(points);
        LineSegment line = new LineSegment(a, b);
        LineSegment line2 = new LineSegment(b, c);
        double[] domain = line2.domainRestriction();
        boolean online = line2.onLine(d);

    }



    public static void main(String arg[]){

        testDuplicatesRemoved();

    }
}
