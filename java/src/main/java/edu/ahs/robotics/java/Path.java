package edu.ahs.robotics.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Path {

    private ArrayList<Point> wayPoints;


    /**
     * @param rawPoints Array of X, Y points, Duplicate points are discarded
     *                  A path must have at least 2 non-identical points
     */
    public Path(Point[] rawPoints) {

        ArrayList<Point> newPoints = new ArrayList<Point>();
        for (int i=0; i < rawPoints.length; i++){
            if (!newPoints.contains(rawPoints[i])){
                newPoints.add(rawPoints[i]);
            }
        }
        // Path must consist of more than two points
        if (newPoints.size() <= 2){
            throw new IllegalArgumentException("A Path must be defined by at least two non-duplicate points.");
        }
        this.wayPoints = newPoints;

    }

    public ArrayList<Point> getWayPoints(){
        return this.wayPoints;
    }

    /**
     * @return total distance of the path
     */
    public double totalDistance(){
        double totalD = 0;
        for (int i=0; i < this.wayPoints.size(); i++){
            if (!(i == 0)){
                totalD += this.wayPoints.get(i).distance(this.wayPoints.get(i - 1));
            }
        }
        return totalD;
    }

    /**
     * @return a point at the supplied distance along the path from the supplied current position
     * Note that the point will usually be interpolated between the points that originally defined the Path
     */
    public Path.WayPoint targetPoint(Point current, double distance) {
        return new WayPoint(new Point(0,0), 0.0, 0.0, 0.0);
    }


    public static class WayPoint {
        public Point point;
        private double deltaXFromPrevious;
        private double deltaYFromPrevious;
        private double distanceFromPrevious;

        private WayPoint(Point point, double deltaXFromPrevious, double deltaYFromPrevious, double distanceFromPrevious) {
            this.point = point;
            this.deltaXFromPrevious = deltaXFromPrevious;
            this.deltaYFromPrevious = deltaYFromPrevious;
            this.distanceFromPrevious = distanceFromPrevious;
        }

        /**
         * Calculates the projection of the vector Vcurrent leading from the supplied current
         * point to this WayPoint onto the vector Vpath leading from the previous point on the path
         * to this WayPoint.  If the return value is positive, it means that the WayPoint is
         * farther along the path from the current point.  If the return value is negative, it means
         * that the WayPoint is before the current point.  The magnitude of the value tells the
         * distance along the path.  The value is computed as the dot product between Vcurrent and
         * Vpath, normalized by the length of vPath
         * @param current The source point to compare to the WayPoint
         */
        private double componentAlongPath(Point current) {
            double deltaXFromCurrent = this.point.getX() - current.getX();
            double deltaYFromCurrent = this.point.getY() - current.getY();

            double dp = deltaXFromCurrent * deltaXFromPrevious + deltaYFromCurrent * deltaYFromPrevious;
            return dp / distanceFromPrevious;
        }
    }


}
