package symbolTables;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET  {

    private SET<Point2D> store;

    // construct an empty set of points
    public PointSET()  {

        store = new SET<>();


    }

    // is the set empty?
    public boolean isEmpty(){

        return store.isEmpty();

    }

    // number of points in the set
    public int size(){

        return store.size();

    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){

        nullCheck(p);

        store.add(p);

    }

    // does the set contain point p?
    public boolean contains(Point2D p){

        nullCheck(p);

        return store.contains(p);

    }

    // draw all points to standard draw
    public void draw(){

        for(Point2D p: store){
            p.draw();
        }

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect){

        nullCheck(rect);

        Stack<Point2D> pointsInRect = new Stack<>();

        for(Point2D p: store){

            if (rect.contains(p)) pointsInRect.push(p);
        }

        return pointsInRect;

    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

        nullCheck(p);

        if (store.isEmpty()) return null;

        double nearestDist = p.distanceTo(store.min());
        Point2D nearestPoint = null;

        for (Point2D storePoint : store) {

            double currDist = p.distanceTo(storePoint);
            if (nearestPoint == null){
                nearestDist = currDist;
                nearestPoint = storePoint;

            }

            else if (currDist < nearestDist) {

                nearestDist = currDist;
                nearestPoint = storePoint;
            }

        }


            return nearestPoint;

        }


    private void nullCheck(Object o){

        if (o == null) throw new java.lang.IllegalArgumentException();
    }

    public static void main(String[] args){


    }


}
