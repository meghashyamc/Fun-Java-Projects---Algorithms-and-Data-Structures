package sortingPractice.notProject;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Stack;

// Given a set of n distinct points in the plane, finds every (maximal) line segment that connects a subset of 4 or more of the points
// in O(n^2 x log(n)) time
public class FastCollinearPoints {

    private Stack<LineSegment> lines;
    private Point[] points;
    private LineSegment[] linesArray;
    private LineSegment[] linesArrayCopy;


    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

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
    public int numberOfSegments() {

        return linesArray.length;

    }


    // the line segments
    public LineSegment[] segments() {

        System.arraycopy(linesArray, 0, linesArrayCopy, 0, linesArray.length );;
    return linesArrayCopy;
    }

    private LineSegment[] findSegments(Point[] points){
        double firstSlope;
        Stack<String> linesAsStrings = new Stack<>();

        Point[] naturalSortedPoints = new Point[points.length];

        System.arraycopy(points, 0, naturalSortedPoints, 0, points.length);
        Arrays.sort(naturalSortedPoints);



        for (int i = 0; i < naturalSortedPoints.length; i++) {

            Arrays.sort(points, 0, points.length, naturalSortedPoints[i].slopeOrder());


            for(int j = 0; j < points.length;)   {


            if (points[j].compareTo(naturalSortedPoints[i]) == 0) {
                j++;
                continue;
            }

                firstSlope = naturalSortedPoints[i].slopeTo(points[j++]);
                int q = j-1;
                int counter = 2;

                while ((j < points.length) && (firstSlope == naturalSortedPoints[i].slopeTo(points[j]))) {
                    counter++;
                    j++;

                }

                if (counter >= 4)

                    storeLine(linesAsStrings, firstSlope, counter, i, q, points, naturalSortedPoints);



            }

        }


        int numLines = lines.size();
        LineSegment[] linesArray = new LineSegment[numLines];

        for (int z = 0; z < numLines; z++) {

            linesArray[z] = lines.pop();

        }


        return linesArray;
    }


    private void storeLine(Stack<String> linesAsStrings , double firstSlope, int counter, int i, int q, Point[] points, Point[] naturalSortedPoints){



                Point[] temp = new Point[counter];

                temp[0] = naturalSortedPoints[i];
                int x = q;

                for (int k = 1; k < counter; k++) {
                    temp[k] = points[x++];

               }

                 Arrays.sort(temp);

               LineSegment newLine = new LineSegment(temp[0], temp[counter - 1]);

               if (!linesAsStrings.contains(newLine.toString())){

                   linesAsStrings.push(newLine.toString());
                   lines.push(newLine);

               }


        }




    private void checkRepeated(Point[] points) {

        Arrays.sort(points);

        for (int i = 0; i < points.length-1; i++) {
            if ((points[i].compareTo(points[i+1])) == 0) throw new IllegalArgumentException();
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
//            segment.draw();
        }
//        StdDraw.show();
    }

}
