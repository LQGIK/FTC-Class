package edu.ahs.robotics.java;

import org.junit.Test;

import static org.junit.Assert.*;

public class PathTest {

    @Test
    public void testTargetPoint() {
        // Make some points
        Point a = new Point(-.75, 0);
        Point b = new Point(3, 5);
        Point c = new Point(7, 5);
        Point d = new Point(0,0);

        Point robot = new Point(0, 1);

        Point[] points = {a, b, c, d};
        Path path = new Path(points);

        double distance = path.totalDistance();
        Point target = path.targetPoint(robot, 20).point;

        assertEquals(b, path.targetPoint(robot, 5).point);
        assertEquals(d, path.targetPoint(robot, 20).point);
    }
}