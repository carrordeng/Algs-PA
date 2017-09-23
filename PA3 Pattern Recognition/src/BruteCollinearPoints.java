import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;

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
                throw new java.lang.IllegalArgumentException("The argument to the constructor contains a repeated point")
        }
        findLineSegment(points);
    }

    private void findLineSegment(Point[] points) {
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    continue;
                for (int k = j + 1; k < points.length - 1; k++) {
                    if (points[i].compareTo(points[k]) == 0)
                        continue;
                    if (points[j].compareTo(points[k]) == 0)
                        continue;
                    if (comparator)
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentnumber;
    }

    // the line segments
    public LineSegment[] segments() {

    }
}