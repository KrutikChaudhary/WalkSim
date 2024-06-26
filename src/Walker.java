import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * A class encoding a walker which moves randomly along an integer lattice.
 * The randomness is encoded as a Markov chain encoding
 * which direction the walker should move: North, East, South, West.
 * The Markov chain's states should be a subset of, or equal to, {"N", "E", "S", "W"}.
 */
public abstract class Walker {

    public static final int START_X = 0;
    public static final int START_Y = 0;
    public static final int STEP_SIZE = 1; //each step along the lattice moves this many units

    protected MarkovChain mc;
    protected Coordinate curPos;
    protected ArrayList<Coordinate> path;

    /**
     * Construct a RandomWalker from a Markov chain.
     * The Markov chain should at most have the state labels "N", "E", "S", "W";
     * which encode the cardinal directions to talk
     * @param chain the Markov chain encoding walk direction
     */
    public Walker(MarkovChain chain) {
        mc = chain;
        curPos = new Coordinate(START_X, START_Y);
        path = new ArrayList<>();
    }

    /**
     * Perform a walk of Nsteps and return the resulting path.
     */
    public abstract ArrayList<Coordinate> walk(int Nsteps);

    /**
     * Given a random walker, store its most recently walked path to a file.
     * If the walker's path contains 0 steps, then the resulting file will be empty.
     * Otherwise, for N steps, there will be N+1 coordinates printed to the file,
     * one per line. Line i and line i+1 encode the i+1'th step beginning
     * at coordinate i and ending at coordinate i+1.
     * @param fname the name of the file in which to write the path
     * @throws IOException if the file could not be open or created writing
     */
    public void saveWalkToFile(String fname) throws IOException {
        PrintWriter writer = new PrintWriter(fname);

        for (Coordinate coord : path) {
            if(fname.substring(fname.length()-4,fname.length()).equals(".txt")){
                writer.println(String.format("(%d, %d)", coord.x, coord.y));
            }
            else if(fname.substring(fname.length()-4,fname.length()).equals(".dat")){
                writer.print(String.format(coord.x + " " +  coord.y + " "));
            }

        }

        writer.close();
    }

    /**
     * Private helper method for walk().
     * Given the current state of the Markov chain,
     * compute the "step" based on the direction encoded by the Markov chain.
     * @return a coordinate encoding the difference between the Walker's destination position
     *         and its starting position for this step.
     */
    public Coordinate getStepDirection() {
        Coordinate step = new Coordinate(0,0);
        String state =  mc.getStateString();
        if (state.equals("N")) {
            step.accumulate(new Coordinate(0, STEP_SIZE));
        } else if (state.equals("E")) {
            step.accumulate(new Coordinate(STEP_SIZE, 0));
        } else if (state.equals("S")) {
            step.accumulate(new Coordinate(0, -STEP_SIZE));
        } else {
            step.accumulate(new Coordinate(-STEP_SIZE, 0));
        }
        return step;
    }

}
