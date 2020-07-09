package edu.ahs.robotics.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Path {

    private ArrayList<WayPoint> wayPoints;
    private int count;


    /**
     * @param rawPoints Array of X, Y points, Duplicate points are discarded
     *                  A path must have at least 2 non-identical points
     */
    public Path(Point[] rawPoints) {

        this.wayPoints = new ArrayList<>();

        // Remove consecutive duplicates
        for (int i=0; i < rawPoints.length; i++){

            // Initialize first WayPoint
            if (i == 0){
                this.wayPoints.add(new WayPoint(rawPoints[0], 0, 0, 0));
            }

            // Check prev != current (already checks if i==0 in previous conditional)
            else if (!rawPoints[i - 1].equals(rawPoints[i])){

                // Calculate distance values from current point to previous point
                Point prev = rawPoints[i-1];
                double distanceFromPrev = rawPoints[i].distance(prev);
                double deltaXFromPrev = rawPoints[i].getX() - prev.getX();
                double deltaYFromPrev = rawPoints[i].getY() - prev.getY();

                this.wayPoints.add(new WayPoint(rawPoints[i], deltaXFromPrev, deltaYFromPrev, distanceFromPrev));
            }

        }
        // Path must consist of more than two points
        if (this.wayPoints.size() <= 2){
            throw new IllegalArgumentException("A Path must be defined by at least two non-duplicate points.");
        }

    }


    /**
     * @return all WayPoints
     */
    public ArrayList<WayPoint> getWayPoints(){
        return this.wayPoints;
    }

    /**
     * @return total distance of the path
     */
    public double totalDistance(){
        double totalD = 0;
        for (int i=0; i < this.wayPoints.size(); i++){
            if (!(i == 0)){
                totalD += this.wayPoints.get(i).distanceFromPrevious;
            }
        }
        return totalD;
    }

    public Path.WayPoint pathInterpolate(WayPoint start, double distance){
        /**
         * @param Starting Point, Distance from the Starting Point of that path
         * @return the target point given a start and distance along a path
         */

        // If there is no next segment and we've reached the end of the path, simply return the last WayPoint
        WayPoint lastWP = wayPoints.get(wayPoints.size() - 1);
        if (start.equals(lastWP)){
            return lastWP;
        }
        // If we've exhausted the distance to travel or if the targetPoint is on the given line
        else if (distance <= 0){
            return start;
        }

        // Retrieve next WP, Interpolate the leftover distance,
        WayPoint next = wayPoints.get(count + 1);
        WayPoint targetPoint = calcWP(start, new LineSegment(start.point, next.point).interpolate(start.point, distance));
        count += 1;
        return pathInterpolate(next, next.point.distance(targetPoint.point));
    }



    public static Path.WayPoint calcWP(WayPoint prevWP, Point current){
        /**
         * @param current point
         * @return a WayPoint given the previous WayPoint, and the current Point
          */
        // Calculate WayPoint Data
        double deltaX = current.getX() - prevWP.point.getX();
        double deltaY = current.getY() - prevWP.point.getY();
        double distanceFromPrev = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        return new WayPoint(current, deltaX, deltaY, distanceFromPrev);
    }


    /**
     * @return a point at the supplied distance along the path from the supplied current position
     * Note that the point will usually be interpolated between the points that originally defined the Path
     */
    public Path.WayPoint targetPoint(Point current, double distance) {

        for (int i=0; i < wayPoints.size(); i++){

            // If we've come across a positive component length
            double componentLength = wayPoints.get(i).componentAlongPath(current);
            if (componentLength > 0){

                // Remember segment we are on so we don't have to traverse 'n' times. (i-1 bc in our pathInterpolate our current is where we start, not where end up)
                count = i - 1;


                // Be cautious of Start exceptions. It will just wrap around. Can only occur on a closed loop or intersection
                if (i == 0){
                    Point pathPrev = wayPoints.get(wayPoints.size() - 1).point;
                }

                // Grab points for interpolation
                Point pathCurrent = wayPoints.get(i).point;
                Point pathPrev = wayPoints.get(i - 1).point;

                // Calculate projected point
                LineSegment line2InterBack = new LineSegment(pathPrev, pathCurrent);
                WayPoint projected = calcWP(wayPoints.get(i-1), line2InterBack.interpolate(pathCurrent, -1 * componentLength));


                // Recursively run this method for path interpolation till we reach the final point
                return pathInterpolate(projected, distance);
            }
        }
        throw new Error("All paths are behind. No positive ComponentAlongPath.");
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WayPoint wayPoint = (WayPoint) o;
            return Double.compare(wayPoint.deltaXFromPrevious, deltaXFromPrevious) == 0 &&
                    Double.compare(wayPoint.deltaYFromPrevious, deltaYFromPrevious) == 0 &&
                    Double.compare(wayPoint.distanceFromPrevious, distanceFromPrevious) == 0 &&
                    point.equals(wayPoint.point);
        }

        @Override
        public String toString() {
            return "WayPoint{" +
                    "point=" + point +
                    ", deltaXFromPrevious=" + deltaXFromPrevious +
                    ", deltaYFromPrevious=" + deltaYFromPrevious +
                    ", distanceFromPrevious=" + distanceFromPrevious +
                    '}';
        }

        @Override
        public int hashCode() {
            return Objects.hash(point, deltaXFromPrevious, deltaYFromPrevious, distanceFromPrevious);
        }
    }


}
