import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int segmentnumber;
    private Point[] points;
    private ArrayList<LineSegment> segmentlist = new ArrayList<LineSegment>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] inputpoints) {
        if (inputpoints == null)
            throw new java.lang.IllegalArgumentException("The argument to the constructor is null.");
        points = new Point[inputpoints.length];
        for (int i = 0; i < inputpoints.length; i++) {
            if (inputpoints[i] == null)
                throw new java.lang.IllegalArgumentException("The argument to the constructor contains a null point.");
            points[i] = inputpoints[i];
        }
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new java.lang.IllegalArgumentException("The argument to the constructor contains a repeated point");
        }
        findLineSegment(points);
    }

    private void findLineSegment(Point[] points) {
        for (int i = 0; i < points.length - 3; i++) {
            Comparator<Point> comparator = points[i].slopeOrder();
            for (int j = i + 1; j < points.length - 2; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    continue;
                for (int k = j + 1; k < points.length - 1; k++) {
                    if (points[i].compareTo(points[k]) == 0)
                        continue;
                    if (points[j].compareTo(points[k]) == 0)
                        continue;
                    if (comparator.compare(points[j], points[k]) == 0) {
                        for (int l = k + 1; l < points.length; l++) {
                            if (points[i].compareTo(points[l]) == 0)
                                continue;
                            if (points[j].compareTo(points[l]) == 0)
                                continue;
                            if (points[k].compareTo(points[l]) == 0)
                                continue;
                            if (comparator.compare(points[k], points[l]) == 0) {
                                LineSegment segment = new LineSegment(points[i], points[l]);
                                segmentlist.add(segment);
                            }
                        }
                    }
                }
            }
        }
        segmentnumber = segmentlist.size();
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentnumber;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segmentnumber];
        int i=0;
        for (LineSegment temp:segmentlist) {
            segments[i]=temp;
            i++;
        }
        return segments;
    }

    public static void main(String[] args) {
        // read the n points from a file
//        In in = new In(args[0]);
        In in = new In("C:\\Users\\Carrot\\Desktop\\Algs-PA\\PA3 Pattern Recognition\\src\\collinear\\input50.txt"); //本地测试使用
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}