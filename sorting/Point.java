package sortingPractice.notProject;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point>{

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    // constructs the point (x, y)
    public Point(int x, int y) {

        this.x = x;
        this.y = y;
    }

    // draws this point
    public   void draw() {

        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates

    public int compareTo(Point that) {

   if(this.y < that.y) return -1;
   else if (this.y > that.y) return +1;
   else if (this.y == that.y){

       if(this.x < that.x) return -1;
       else if (this.x > that.x) return +1;


        }

        return 0;

   }

    // the slope between this point and that point
    public double slopeTo(Point that) {

        double yDiff = that.y - this.y;
        double xDiff = that.x - this.x;

       double a = 1;
       double posZero = (a-a)/a;

       if(xDiff == 0){

            if(yDiff == 0) return Double.NEGATIVE_INFINITY;
            else return Double.POSITIVE_INFINITY;
        }

        else if (yDiff == 0) return posZero;

        else return yDiff/xDiff;
    }

    // compare two points by slopes they make with this point

    public Comparator<Point> slopeOrder() {

        Comparator<Point> BY_SLOPE =  new bySlope();

        return BY_SLOPE;

        }



    private class bySlope implements Comparator<Point>{

    public int compare(Point p1, Point p2){

        if(Point.this.slopeTo(p1) < Point.this.slopeTo(p2)) return -1;
        else if(Point.this.slopeTo(p1) > Point.this.slopeTo(p2)) return +1;
        return 0;


    }

        }





    public static void main(String[] args) {
        Point point1 = new Point(0, 0);
        Point point2 = new Point (1, 1);

    }


}
