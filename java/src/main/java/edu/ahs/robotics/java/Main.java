package edu.ahs.robotics.java;

public class Main {
    public static void main(String arg[]){
        Point a = new Point(0, 0);
        Point b = new Point( 3, 4);
        Point c = new Point(6, 8);
        Point d = new Point(9, 12);
        Point e = new Point(5, 0);
        LineSegment line = new LineSegment(a, d);
        Point[] innerPoints = line.subDivide(3);
        System.out.println(line.calcMidPoint());
        System.out.println(line.interpolate(a, 7.5));
    }
}
