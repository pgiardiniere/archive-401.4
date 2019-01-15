// CS 401 Summer 2016 Main Program
// Your classes should work with this program as given with no changes.  SimpleBallot
// you can write using your own ideas as long as it is reasonable.  However,
// BetterBallot must follow the specifications indicated in the Assignment sheet.

import java.util.*;
import java.io.*;

public class Assig4
{
    public static void main(String [] args) throws IOException
    {
        Scanner keyin = new Scanner(System.in);
        BallotInterface B;

        if (args[0].equals("SimpleBallot"))
            B = new SimpleBallot(keyin);
        else

            B = new BetterBallot(keyin);

        B.initialize();

        if (!B.isValid())
        {
            System.out.println("Problem initializing ballot -- aborting program");
            System.exit(0);
        }

        while (B.continueVoting())
        {
            B.nextVoter();
        }

        B.finalize();
    }
}


