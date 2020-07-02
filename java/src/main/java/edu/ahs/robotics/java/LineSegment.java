package edu.ahs.robotics.java;

public class LineSegment {

    private Point a;
    private Point b;
    private double slope;
    private double yInt;

    public LineSegment(Point a, Point b){
        this.a = a;
        this.b = b;
        this.slope = a.calcSlope(b);
        this.yInt = a.calcYInt(b);
    }

    public String toString(){
        return "Points: " + a + ", " + b + "\nSlope: " + slope + "\nB: " + yInt;
    }

    public boolean onLine(Point point){
        /**
         * @param (Point point)
         * @return Returns true if a given point is on the line segment (As of now it doesn't account for boundaries)
         */
        if ((point.getX() * slope) + yInt == point.getY()){
            return true;
        }
        else {
            return false;
        }
    }

    public Point[] subDivide(int subSegments){
        /**
         * @param Number of segments to divide line into
         * @return Returns a list of points at each segment
         */
        if (subSegments <= 0){
            return null;
        }

        // Initialize return array
        Point[] points = new Point[subSegments - 1];

        // Initialize ratio
        double ratio = 1.0 / subSegments;

        // Init total X and Y distance
        double yDist = b.getY() - a.getY();
        double xDist = b.getX() - a.getX();

        // Iterate and add the deltaX to the original x for each segment
        for (int i=0; i < subSegments - 1; i++){
            double new_x = a.getX() + ((i+1) * ratio * xDist);
            double new_y = a.getY() + ((i+1) * ratio * yDist);
            Point newPoint = new Point(new_x, new_y);
            points[i] = newPoint;
        }

        return points;
    }

    public Point calcMidPoint(){
        /**
         * @param None
         * @return Returns Point midpoint
         */

        return this.subDivide(2)[0];
    }



    public Point interpolate(Point start, double euclidDistance){
        /**
         * @param (Starting Point, Distance from point)
         * @return Returns point along this line that is a given distance away
         */

        // Assuming a or b isn't on an axis. Otherwise we have some issues with this algorithm

        // check if point is on line
        if (!onLine(start)){
            return null;
        }

        // Calculate ratio of hypotenuses because this is same ratio for X and Y
        double ratio = euclidDistance / a.distance(b);

        // Calculate total x and y distance between a and b
        double xDist = b.getX() - a.getX();
        double yDist = b.getY() - a.getY();

        // Calculate new point
        double new_x = a.getX() + (ratio * xDist);
        double new_y = a.getY() + (ratio * yDist);
        Point newPoint = new Point(new_x, new_y);

        return newPoint;

    }

}
