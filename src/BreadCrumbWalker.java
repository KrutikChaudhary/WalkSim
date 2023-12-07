import java.util.ArrayList;

public class BreadCrumbWalker extends Walker{

    /**
     * Construct a RandomWalker from a Markov chain.
     * The Markov chain should at most have the state labels "N", "E", "S", "W";
     * which encode the cardinal directions to talk
     *
     * @param chain the Markov chain encoding walk direction
     */
    public BreadCrumbWalker(MarkovChain chain) {
        super(chain);
    }

    /**
     * Perform a bread-crumb walk of Nsteps and return the resulting path.
     * The path is encoded as a list of coordinates indicating the start and end of
     * each step. Specifically, the coordinate at index i and the coordinate at index i+1
     * encode the i+1'th step from coordinate i to coordinate i+1.
     *
     * If Nsteps is 0 or negative, the resulting path is empty.
     *
     * Note that each call to walk resets the path of this walker to start again from
     * the default starting point and clears the history of the previous path.
     *
     * @param Nsteps: the number of steps to simulate in the bread-crumb walk
     * @return the path of the walk.
     */
    @Override
    public ArrayList<Coordinate> walk(int Nsteps) {
        int N = Nsteps;
        Coordinate curPos = new Coordinate(START_X, START_Y);
        path.clear(); //reset the path for this new walk.

        if (N > 0) {
            path.add(new Coordinate(curPos.x, curPos.y));
        }
        for (int step = 0; step < N; ++step) {
            mc.nextState();
            curPos.accumulate(getStepDirection());
            path.add(new Coordinate(curPos.x, curPos.y));
        }

        for(int i = path.size()-1; i>=0; i--){
            path.add(path.get(i));
        }
        return path;
    }
}
