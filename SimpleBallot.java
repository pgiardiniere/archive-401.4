/**
 * Created by Pete on 7/28/2016.
 * program implements BallotInterface without much additional functionality
 * lacks voter registration, tracking results between runs, etc.
 * Created only for proof of concept
 */

import java.util.*;
import java.io.*;

public class SimpleBallot implements BallotInterface {

    private Scanner inScan;

    public SimpleBallot(Scanner S) {
        inScan = S;
    }

    private int numVotes;
    private int [] tablet, punk, movie;

    public void initialize() {
        numVotes = 0;
        tablet = new int [3];
        punk = new int [5];
        movie = new int [4];
    }

    public boolean isValid() {
        boolean validInput = false;
        if (numVotes == 0)
            validInput = true;
        return validInput;
    }

    public boolean continueVoting() {

        boolean willContinue = false;
        boolean badInput = true;
        while (badInput) {
            inScan.nextLine();
            System.out.println("Is there another voter? Enter Y or N (Exactly as shown)");
            String input = inScan.nextLine();
            if (input.equals("Y")) {
                willContinue = true;
                badInput = false;
            }
            if (input.equals("N")) {
                willContinue = false;
                badInput = false;
            }
            else if (!(input.equals("Y") || input.equals("N")))
                System.out.println("I don't recognize that value, try again please.");
        }
        return willContinue;
    }

    public void nextVoter() {
        System.out.println("Welcome to a SimpleBallot election!");
        System.out.println("This program uses console input, NOT panels");
        System.out.println("It's also missing a lot of BetterBallot's functionality");

        numVotes++;
        System.out.println("You are voter number " + numVotes + ".");

        //begin TABLET vote
        boolean badInput = true;
        int input;
        while (badInput) {
            System.out.println("Enter the number position (i.e. 1, 2, or 3) of the tablet you like best:");
            System.out.println("iPad, Surface, or Galaxy");

            input = inScan.nextInt();

            if (input > 0 && input < 4) {
                badInput = false;
                input -= 1;
                tablet[input]++;
            }
            else
                System.out.println("I didn't recognize that value. Try again \n");
        }

        //begin PUNK vote
        badInput = true;
        while (badInput) {
            System.out.println("Enter the number position (i.e. 1, 2, 3, 4, or 5) of the punk band you like best:");
            System.out.println("Ramones, Clash, Black Flag, X, Bad Brains");

            input = inScan.nextInt();

            if (input > 0 && input < 6) {
                badInput = false;
                input -= 1;
                punk[input] = punk[input] + 1;
            }
            else
                System.out.println("I didn't recognize that value. Try again \n");
        }

        //begin MOVIE vote
        badInput = true;
        while (badInput) {
            System.out.println("Enter the number position (i.e. 1, 2, 3, or 4) of the movie you like best:");
            System.out.println("Princess Bride, Dead Poets Society, Casablanca, Alien");

            input = inScan.nextInt();

            if (input > 0 && input < 5) {
                badInput = false;
                input -= 1;
                movie[input] = movie[input] + 1;
            }
            else
                System.out.println("I didn't recognize that value. Try again \n");
        }
    }

    public void finalize() {
        System.out.println("The results are in!\n");
        System.out.println("Total number of voters: " + numVotes);

        System.out.println("\nTablet category results:");
        System.out.println("iPad:" + tablet[0] + " Surface:" + tablet[1] + " Galaxy:" + tablet[2]);

        System.out.println("\nPunk band category results:");
        System.out.println("Ramones:" + punk[0] + " Clash:" + punk[1] + " Black Flag:" + punk[2]
         + " X:" + punk[3] + " Bad Brains:" + punk[4]);

        System.out.println("\nMovie category results:");
        System.out.println("Princess Bride:" + movie[0] + " Dead Poets Society:" + movie[1] +
                " Casablanca:" + movie[2] + " Alien:" + movie[3]);

    }
}
