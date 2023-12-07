import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A main class for orchestrating a random walk.
 */
public class WalkSim {

    /**
     * The main method for a random walk simulation.
     * First reads a file which encodes a Markov chain's transition matrix,
     * and then performs a random walk.
     * Command line arguments:
     *    [0]: the file containing the FloatMatrix for the Markov chain
     *    [1]: the output file to store the path produced
     *    [2]: the number of steps to simulate
     */
    public static void main(String[] args) {

        String exampleFile = "test_cases/example1.txt";
        Scanner kb = new Scanner(System.in);
        int nSteps = 200;
        System.out.println("Enter number of steps: ");
        while(true){
            try{
                nSteps= kb.nextInt();
                break;
            }
            catch (InputMismatchException e){
                System.out.println("Enter valid integer: ");
                kb.nextLine();
            }

        }
        int walk = -1;
        System.out.println("Enter walk type: ");
        while(true){
            try{
                walk= kb.nextInt();
                if(walk>2||walk<0){
                    System.out.println("Enter value from 0,1,2");
                    continue;
                }
                break;
            }
            catch (InputMismatchException e){
                System.out.println("Enter valid integer: ");
                kb.nextLine();
            }

        }
        System.out.println("Enter file name to store output");
        String outputFile= kb.next();


        int walkerType = 0;

        int stepDuration = 30; //controls the speed of the animation

        if (args.length > 0) {
            exampleFile = args[0];
        }
        if (args.length > 1) {
            outputFile = args[1];
        }
        if (args.length > 2) {
            try {
                int steps = Integer.parseInt(args[2]);
                nSteps = steps;
            } catch (NumberFormatException nfe) {
                System.out.println("Could not use args[2] since it was not an integer: " + args[2]);
                System.out.printf("Defaulting to %d steps.\n", nSteps);
            }
        }
        if (args.length > 3) {
            try {
                int type = Integer.parseInt(args[3]);
                walkerType = type;
            } catch (NumberFormatException nfe) {
                System.out.println("Could not use args[3] since it was not an integer: " + args[3]);
                System.out.printf("Defaulting to walker type %d.\n", walkerType);
            }
        }

        String[] cardinals = {"N", "E", "S", "W"};
        FloatMatrix T1 = null;
        try {

            T1 = FloatMatrix.fromFile(exampleFile);
            assert T1.rows() == 4 : "Walker MarkovChain should have 4 states";

            MarkovChain mc = new MarkovChain(T1, cardinals);
            Walker walker;

            if(walk == 1){
                 walker = new SpiralWalker(mc);
            }
            else if (walk == 0){
                walker = new RandomWalker(mc);
                System.out.println(T1.prettyString());
            }
            else{
                walker = new BreadCrumbWalker(mc);
                System.out.println(T1.prettyString());
            }



            ArrayList<Coordinate> theWalk = walker.walk(nSteps);
            walker.saveWalkToFile(outputFile);

            WalkFrame walkFrame = new WalkFrame();
            walkFrame.animatePath(theWalk, stepDuration);

        } catch (FileNotFoundException fnfe) {
            System.out.println("Could not find the specified matrix file.");
            System.out.println(fnfe.toString());
            System.exit(1);

        } catch (IOException ioe) {
            System.out.println("Could not save walk to file: " + outputFile);
            System.out.println(ioe.getMessage());
            System.exit(1);

        }

    }
}
