import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tools {

    /**
     * Checks that the path is valid (contains every node exactly once), and
     * also prints the length of the path.
     *
     * @param arcs matrix containing the arcs of the graph
     * @param path the path to get a length for
     */
    public static void checkPath(double[][] arcs, int[] path) {
        double length = Tools.getPathLength(arcs, path);
        boolean valid = Tools.isPathValid(path);
        if (path.length != arcs[0].length) {
            valid = false;
        }
        System.out.println("This TSP path is " + length + " km long and is " + (valid ? "valid" : "invalid") + ".");
        Tools.printPath(path);
    }

    /**
     * Calculate the length of a path.
     *
     * @param arcs matrix containing the arcs of the graph
     * @param path the path to get a length for
     * @return the length of the path
     */
    public static double getPathLength(double[][] arcs, int[] path) {
        double l = 0;
        for (int i = 1; i < path.length; i++) {
            l += arcs[path[i - 1]][path[i]];
        }
        l += arcs[path[path.length - 1]][path[0]];
        return l;
    }

    /**
     * Check that the path contains every node exactly once.
     *
     * @param path the path to check
     * @return the outcome of the check
     */
    public static boolean isPathValid(int[] path) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < path.length; i++) {
            list.add(path[i]);
        }
        Collections.sort(list);
        for (int i = 0; i < path.length; i++) {
            if (list.get(i) != i) {
                return false;
            }
        }
        return true;
    }

    /**
     * Reverses a subsection of a path.
     *
     * @param path the path to reverse a subsection of
     * @param reverseStart the first node of the subsection
     * @param reverseEnd the last node of the subsection
     */
    public static void reversSubSectionOfArray(int[] path, int reverseStart, int reverseEnd) {
        if (reverseEnd < reverseStart) {
            int t = reverseEnd;
            reverseEnd = reverseStart;
            reverseStart = t;
        }
        int d2 = (reverseEnd - reverseStart) / 2;
        for (int i = 0; i <= d2; i++) {
            int t = path[reverseStart + i];
            path[reverseStart + i] = path[reverseEnd - i];
            path[reverseEnd - i] = t;
        }
    }

    /**
     * Fill in the path with a randomized valid set of nodes.
     *
     * @param path this is where the result is returned.
     * @param seed the seed used to create the path, reusing the same seed
     *            results in the same path.
     */
    public static void getRandomizedStartPath(int[] path, long seed) {
        Random rnd = new Random(seed);
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < path.length; i++) {
            list.add(new Integer(i));
        }
        int j = 0;
        while (!list.isEmpty()) {
            int ri = rnd.nextInt(list.size());
            path[j++] = (Integer) list.remove(ri);
        }
    }

    /**
     * Read a graph from a comma separated file, file should be on x,y,x,y...
     * format
     *
     * @param file the file containing the graph
     * @return the nodes x and y values of the graph
     */
    public static int[] readGraphFromCVSFile(String file) {
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(file));
            line = br.readLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] split = line.split(",");
        int[] results = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            try {
                results[i] = Integer.parseInt(split[i].trim());
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
        return results;
    }

    /**
     * Read a graph from a comma separated file, file should be on x,y,x,y...
     * format
     *
     * @param file the file containing the graph
     * @return the nodes x and y values of the graph
     */
    public static int[] readPathFromCVSFile(String file) {
        BufferedReader br = null;
        String line = "#";
        try {
            br = new BufferedReader(new FileReader(file));
            while (line.startsWith("#")) {
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] split = line.split(",");
        int[] results = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            try {
                results[i] = Integer.parseInt(split[i].trim());
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }
        return results;
    }

    /**
     * Looks for the maximum coordinate on both x and y axis, returns the max
     * value found.
     *
     * @param data the x,y values for al the nodes in the graph
     * @return integer for the largest x or y value found
     */
    public static int getMaxCoordSize(int[] data) {
        int max = 0;
        for (int s : data) {
            max = Math.max(max, s);
        }
        return max;
    }

    /**
     * Prints the path
     *
     * @param path the path to print
     */
    public static void printPath(int[] path) {
        System.out.print("Path: ");
        for (int i = 0; i < path.length; i++) {
            System.out.print(path[i] + ", ");
        }
        System.out.println();
    }

    /**
     * Prints the arcs of the graph
     *
     * @param distanceArray
     */
    public static void printArcs(int[][] distanceArray) {
        System.out.println(" - Distance array - ");
        for (int i = 0; i < distanceArray.length; i++) {
            for (int j = 0; j < distanceArray[0].length; j++) {
                System.out.print(" " + distanceArray[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Show a polygon in a window.
     *
     * @param p the polygon to display
     * @param windowSize the size of the window
     * @param windowName name of the window
     */
    public static void createAndShowGUI(Polygon p, int windowSize, String windowName) {
        JFrame f = new JFrame(windowName);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(windowSize, windowSize);
        f.add(new MyPanel(p, windowSize));
        f.pack();
        f.setVisible(true);
    }

    /**
     * Build a polygon for plotting the path on the screen
     *
     * @param nodeData x,y,x,y... data of the graph
     * @param path the path to plot
     * @param windowSize size of the window to plot in
     * @return the polygon to plot.
     */
    public static Polygon getPolygonForPlotting(int[] nodeData, int[] path, int windowSize) {
        Polygon p = new Polygon();
        float maxCoordinate = 0;
        for (int coodr : nodeData) {
            maxCoordinate = Math.max(maxCoordinate, coodr);
        }

        int padding = 50;
        float scale = (float) (windowSize - padding) / maxCoordinate;
        for (int a = 0; a < path.length; a++) {
            p.addPoint((int) (nodeData[2 * path[a]] * scale) + padding / 2, //
                    (int) (nodeData[2 * path[a] + 1] * scale) + padding / 2);
        }
        return p;
    }

    /**
     * Write the path to file
     *
     * @param path the path to write to file
     * @param rndSeed the seed used to create the path
     * @param string file name
     */
    public static void savePathToFile(int[] path, long rndSeed, String string) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        StringBuffer data = new StringBuffer();
        data.append("Time: ");
        data.append(sdf.format(date));
        data.append("\n");
        data.append("Seed: ");
        data.append(rndSeed);
        data.append("\n");
        for (int i = 0; i < path.length; i++) {
            data.append(path[i]).append(", ");
        }
        data.append("\n");
        FileWriter writer;
        try {
            writer = new FileWriter(string);
            writer.write(data.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class MyPanel extends JPanel {
        private static final long serialVersionUID = 1L;

        private Polygon p = null;

        private int windowSize = 100;

        public MyPanel(Polygon p, int windowSize) {
            setBorder(BorderFactory.createLineBorder(Color.black));
            this.p = p;
            this.windowSize = windowSize;
        }

        public Dimension getPreferredSize() {
            return new Dimension(windowSize, windowSize);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the path/polygon
            g.drawPolygon(p);

            int width = 3;
            int height = 3;
            // Draw the points with small squares
            for (int i = 0; i < p.npoints; i++) {
                g.drawRect(p.xpoints[i] - width / 2, p.ypoints[i] - height / 2, width, height);
            }

            // Draw first/last point in path larger
            g.drawRect(p.xpoints[0] - width * 2, p.ypoints[0] - height * 2, width * 4, height * 4);
        }
    }
}
