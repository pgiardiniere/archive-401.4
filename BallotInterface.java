// CS 401 Summer 2016 Main Program
// BallotInterface.  Your Ballot class must implement this interface as specified.

import java.util.*;
import java.io.*;

public interface BallotInterface
{
    public void initialize() throws IOException;
    // Set up the object with anything necessary to initialize
    // the voting.

    public boolean isValid();
    // Return true if the "election" has been set up properly.  Return
    // false if there were any problems in the intialization.

    public boolean continueVoting();
    // Return true if another voter can vote.  The conditions for returning
    // true or false depend on how the overall voting process is done.  See
    // the Assignment sheet for some discussion on this method.

    public void nextVoter() throws IOException;
    // Handle the voting of one voter.  This can be done in various ways
    // depending on how the overall voting process is designed.

    public void finalize();
    // Complete the voting process by "cleaning up" or reporting final results.
}