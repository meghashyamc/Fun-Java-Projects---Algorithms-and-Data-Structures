package symbolTables;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

/*

Write a data type to represent a set of points in the unit square (all points have x- and y-coordinates between 0 and 1)
 using a 2d-tree to support efficient range search
 (find all of the points contained in a query rectangle)
 and nearest-neighbor search (find a closest point to a query point).
 */

public class KdTree {

    private Node root;
    private int size;

    public KdTree(){

        root = null;
        size = 0;
    }

    private static class Node {
        // the point
        private Point2D p;

        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;

        // the left/bottom subtree
        private Node left;

        // the right/top subtree
        private Node right;


        public Node(Point2D p) {
            this.p = p;

        }

    }

    // is the set empty?
    public boolean isEmpty(){

        return root == null;

    }

    // number of points in the set
    public int size(){

        return size;

    }


    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){

        nullCheck(p);

if (root == null) {
    root = new Node(p);
    this.size++;
    root.rect = new RectHV(0.0,0.0,1.0,1.0);
    return;
}

        root = insert(root, p, true, root.rect);


    }

    private Node insert(Node x, Point2D p, boolean vertical, RectHV rect){


        if (x == null){

            x = new Node(p);
            this.size++;
            x.rect = rect;

            return x;
        }

        int cmp;
        if (vertical)
            cmp = Point2D.X_ORDER.compare(p, x.p);
        else
            cmp = Point2D.Y_ORDER.compare(p, x.p);

        if  (cmp < 0)

            x.left = insert(x.left, p, !vertical, getLeftChildRect(x, vertical));


        else if (cmp > 0)

            x.right = insert(x.right, p, !vertical, getRightChildRect(x, vertical));

        else{

            if (x.p.equals(p)) return x;
            // this is the case when the point on the dividing line of the other point (neither to its left/bottom, nor top/right)
            else x.right = insert(x.right, p, !vertical, getRightChildRect(x, vertical));

        }

        return x;
    }

    private RectHV getLeftChildRect(Node x, boolean vertical) {

        if (x.left == null) {

            if (vertical) {
                return new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());

            }

            else {
                return new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());

            }

        }

        else return x.left.rect;
    }

    private RectHV getRightChildRect(Node x, boolean vertical){

        if (x.right == null){

            if (vertical)
                return new RectHV(x.p.x(), x.rect.ymin(),x.rect.xmax(), x.rect.ymax());

            else
                return new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
        }

        else return x.right.rect;
    }



    // does the set contain point p?
    public boolean contains(Point2D p){

        nullCheck(p);

        return contains(root, p, true);

    }

    private boolean contains(Node x, Point2D p, Boolean vertical){

        if (x == null) return false;
        int cmp;
        if (vertical)
            cmp = Point2D.X_ORDER.compare(p, x.p);
        else
            cmp = Point2D.Y_ORDER.compare(p, x.p);

        if      (cmp < 0) return contains (x.left,  p, !vertical);
        else if (cmp > 0) return contains (x.right, p, !vertical);

        else{

            if (x.p.equals(p)) return true;
            // this is the case when the point on the dividing line of the other point (neither to its left/bottom, nor top/right)
            else return contains(x.right, p, !vertical);

        }

    }

    // draw all points to standard draw
    public void draw(){

       draw(root, true);

    }

    private void draw(Node x, boolean vertical){

        if (x == null) return;

        draw(x.left, !vertical);

        if (vertical) {
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            drawNodePoint(x);
        }

        else {

            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
            drawNodePoint(x);
        }

        draw(x.right, !vertical);
    }


    private void drawNodePoint(Node x){

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect){

        nullCheck(rect);

        Stack<Point2D> pointsInRect = new Stack<>();

        range(root, rect, pointsInRect);

        return pointsInRect;

    }

    private void range(Node x, RectHV rect, Stack<Point2D> pointsInRect){

        if (x == null) return;

        // we search a node and its subtrees only if the node's rectangle intersects our input rectangle
        if (rect.intersects(x.rect)) {

            if (rect.contains(x.p)) pointsInRect.push(x.p);
            range(x.left, rect, pointsInRect);
            range(x.right, rect, pointsInRect);
        }


    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {

        nullCheck(p);

        if (root == null) return null;

        return nearest(root, p, root.p.distanceSquaredTo(p), root.p, true);

    }

    private Point2D nearest(Node x, Point2D p, double minDistSq, Point2D minDistPoint, boolean vertical){


        // consider updating the min dist square found so far if
        // it is less than the dist from the given point to this node's rectangle
        // else don't even search this node or its subtrees, return the min. point found
        // so far
        if (x.rect.distanceSquaredTo(p) < minDistSq) {

            double currDistSq = x.p.distanceSquaredTo(p);
            if (currDistSq < minDistSq) {

                minDistSq = currDistSq;
                minDistPoint = x.p;

            }

            if (x.left == null && x.right == null) return minDistPoint;
            else if (x.left == null) return nearest(x.right, p, minDistSq, minDistPoint, !vertical);
            else if (x.right == null) return nearest(x.left, p, minDistSq, minDistPoint, !vertical);


            else {

                Node checkFirst, checkSecond;

                // check that side first, to which the point p lies (with respect to
                // the node x's splitting line)
            if (isToLeft(x, p, vertical)) {
                checkFirst = x.left;
                checkSecond = x.right;
            }

            else {
                checkFirst = x.right;
                checkSecond = x.left;

            }

            Point2D firstCheckedMinPoint = nearest(checkFirst, p, minDistSq, minDistPoint, !vertical);
            return nearest(checkSecond, p, firstCheckedMinPoint.distanceSquaredTo(p), firstCheckedMinPoint, !vertical);

            }
        }

        return minDistPoint;


    }

    // returns true ONLY if the given point's x coord (if vertical is true) or y coord (if vertical is false) is less than the node's point's respective x or y coord.
    private boolean isToLeft(Node x, Point2D p, boolean vertical){

        if (vertical) return p.x() < x.p.x();
        else return p.y() < x.p.y();
    }

    private void nullCheck(Object o){

        if (o == null) throw new IllegalArgumentException();
    }

    public static void main(String[] args){




    }


}
