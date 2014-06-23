import javax.swing.SwingUtilities;

public class TSPSolver {

    // Size of the window where we draw the resulting path
    private final static int WINDOW_SIZE = 500;
    // used for generating a randomized path through the graph
    private static long rndSeed;

    public static void main(String[] args) {

        // Read the x and y values for al nodes from a csv file.
        final int[] data = TSPTools.readGraphFromCVSFile("../graphs/10_locations.csv");

        // How long should the path be / how many nodes are we dealing with.
        final int size = data.length / 2;

        // Get the distances between all nodes
        final double[][] arcs = Arcs.getArray(data, size, size);

        // Create a array for holding the path
        final int[] path = new int[size];

        // Seed for creating a random path
        rndSeed = System.currentTimeMillis();

        // create a random path
        TSPTools.getRandomizedStartPath(path, rndSeed);

        // TODO improve the path !!!
        // This is where YOU improve the path, please use the TSPTools where
        // needed, to check that your path is valid and to check the length of
        // it for example.
        // END TODO

        // Validate that the path is valid (contains all nodes exactly once),
        // and print the length of it.
        TSPTools.checkPath(arcs, path);

        // Save the path to file, named after the input and length
        TSPTools.savePathToFile(path, rndSeed,
                "result_" + size + "_" + TSPTools.getPathLength(arcs, path) + ".csv");

        // Show the resulting path in a window, useful for debugging and
        // improving the optimising algorithms.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TSPTools.createAndShowGUI(TSPTools.getPolygonForPlotting(data, path, WINDOW_SIZE), WINDOW_SIZE,
                        "TSP path, number of nodes: " + size + ", length: " + TSPTools.getPathLength(arcs, path));
            }
        });
    }

}
