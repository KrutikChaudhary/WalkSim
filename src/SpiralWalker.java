import java.util.ArrayList;

public class SpiralWalker extends Walker{

    /**
     * Construct a RandomWalker from a Markov chain.
     * The Markov chain should at most have the state labels "N", "E", "S", "W";
     * which encode the cardinal directions to talk
     *
     * @param chain the Markov chain encoding walk direction
     */
    public SpiralWalker(MarkovChain chain) {
        super(chain);
    }

    /**
     * Perform a spiral walk of Nsteps and return the resulting path.
     * The path is encoded as a list of coordinates indicating the start and end of
     * each step. Specifically, the coordinate at index i and the coordinate at index i+1
     * encode the i+1'th step from coordinate i to coordinate i+1.
     *
     * If Nsteps is 0 or negative, the resulting path is empty.
     *
     * Note that each call to walk resets the path of this walker to start again from
     * the default starting point and clears the history of the previous path.
     *
     * @param Nsteps: the number of steps to simulate in the spiral walk
     * @return the path of the walk.
     */
    @Override
    public ArrayList<Coordinate> walk(int Nsteps) {
        curPos = new Coordinate(START_X,START_Y);
        path.clear(); // reset the path for this new walk.
        char state = 'N'; //initialize state
        int radius = 1;//radius

        //add origin (0,0)
        if (Nsteps > 0) {
            path.add(new Coordinate(curPos.x, curPos.y));
        }
        else if(Nsteps==0){
            return path;
        }

        //initialze
        int i = 0;

        while (i<Nsteps) {
            for (int j = 0; j < 2; j++) { // radius will increament after 2 iterations
                for (int k = 0; k < radius; k++) {
                    if(i>=Nsteps){
                        break;
                    }
                    //find next position
                    if (state == 'N') {
                        curPos = new Coordinate(curPos.x, curPos.y + 1);
                    } else if (state == 'E') {
                        curPos = new Coordinate(curPos.x + 1, curPos.y);
                    } else if (state == 'S') {
                        curPos = new Coordinate(curPos.x, curPos.y - 1);
                    } else {
                        curPos = new Coordinate(curPos.x - 1, curPos.y);
                    }

                    //add and increment
                    path.add(new Coordinate(curPos.x, curPos.y));
                    i++;
                }
                //set next state
                if (state == 'N') {
                    state = 'E';
                } else if (state == 'E') {
                    state = 'S';
                } else if (state == 'S') {
                    state = 'W';
                } else {
                    state = 'N';
                }

                 if(j>0) {
                    radius++;//increment radius
                }
            }
        }
        return path;

    }

}
