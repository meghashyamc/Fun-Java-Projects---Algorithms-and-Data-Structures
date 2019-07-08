package sortingPractice.notProject;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Stack;

// the  aim is to examine 4 points at a time and check whether they all lie on the same line segment, returning all such line segments

public class BruteCollinearPoints {

    private Stack<LineSegment> lines;
    private Point[] points;
    private LineSegment[] linesArray;
    private LineSegment[] linesArrayCopy;

    // finds all line segments containing 4 points

    public BruteCollinearPoints(Point[] points){

        if (points == null) throw new IllegalArgumentException();

        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }

        this.points = points;
        Point[] pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);
        checkRepeated(pointsCopy);
        lines = new Stack<>();

        this.linesArray = findSegments(pointsCopy);

        linesArrayCopy = new LineSegment[linesArray.length];

    }

    // the number of line segments
    public int numberOfSegments(){

        return linesArray.length;

    }

    // the line segments

    public LineSegment[] segments(){

        System.arraycopy(linesArray, 0, linesArrayCopy, 0, linesArray.length );;
        return linesArrayCopy;


    }

    // returns line segments which have four collinear points
    private LineSegment[] findSegments(Point[] points){
        for(int i = 0; i < points.length - 3; i++){

            for(int j = i+1; j < points.length - 2; j++) {

                double slope = points[i].slopeTo(points[j]);

                outerloop:
                for (int k = j + 1; k < points.length - 1; k++) {


                    if (slope == points[i].slopeTo(points[k])) {

                        for(int l = k+1; l < points.length ; l++) {


                            if (slope == points[i].slopeTo(points[l])){

                                Point[] temp = new Point[4];

                                temp[0] = points[i];
                                temp[1] = points[j];
                                temp[2] = points[k];
                                temp[3] = points[l];

                                Arrays.sort(temp);

                                lines.push(new LineSegment(temp[0], temp[3]));

                                break outerloop;
                            }


                        }

                    }
                }

            }

        }

        int numLines = lines.size();
        LineSegment[] linesArray = new LineSegment[numLines];
        for(int i = 0; i < numLines; i++){

            linesArray[i] = lines.pop();

        }

        return linesArray;
    }


    // makes sure a point in the input isn't repeated
   private void checkRepeated(Point[] points) {

        Arrays.sort(points);

        for (int i = 0; i < points.length-1; i++) {
            if ((points[i].compareTo(points[i+1])) == 0) throw new java.lang.IllegalArgumentException();
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

//        // draw the points
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        StdDraw.setPenRadius(0.01);
//        for (sortingPractice.notProject.Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

            LineSegment[] segments = collinear.segments();
        for (LineSegment segment : segments) {
            StdOut.println(segment);
//            segment.draw();
        }

//        StdDraw.show();


    }
}
