/**
 * Created by Pete on 7/28/2016.
 */

import java.nio.file.Files;
import java.util.*;
import java.io.*;
import javax.swing.*;


public class BetterBallot implements BallotInterface {

    private File adminFile, voterFile, ballotFile;
    private Scanner adminFileScan, voterFileScan, ballotFileScan;

    private ArrayList<String> pswdList = new ArrayList<>();
    private ArrayList<Voter> voterList = new ArrayList<>();
    private ArrayList<BallotItem> ballotList = new ArrayList<>();

    private int voterCounter = 0;

    public BetterBallot(Scanner S) throws IOException {
        System.out.println("Please enter the Admin filename");
        String adminFileName = S.nextLine();
        adminFile = new File(adminFileName);
        adminFileScan = new Scanner(adminFile);

        System.out.println("Please enter the Voters filename");
        String votersFileName = S.nextLine();
        voterFile = new File(votersFileName);
        voterFileScan = new Scanner(voterFile).useDelimiter(":");

        System.out.println("Please enter the Ballots filename");
        String ballotsFileName = S.nextLine();
        ballotFile = new File(ballotsFileName);
        ballotFileScan = new Scanner(ballotFile).useDelimiter(":|,");
    }

    public void initialize() throws IOException {
        //Handles reading in and storage of Admin information
        while (adminFileScan.hasNext())
            pswdList.add(adminFileScan.nextLine());

        //Handles reading in and storage of Voter information
        int vNum;
        String vName;
        boolean vStatus;
        StringBuilder temp2 = new StringBuilder();

        while (voterFileScan.hasNextLine()) {
            String temp = voterFileScan.next();
            vNum = Integer.parseInt(temp);
            vName = voterFileScan.next();
            temp2.append(voterFileScan.nextLine());
            temp2.deleteCharAt(0);
            vStatus = Boolean.parseBoolean(temp2.toString());

            Voter voter = new Voter(vNum, vName, vStatus);
            voterList.add(voter);
        }
        voterFileScan.close();

        //Handles reading in and storage of Ballot information
        int bNum;
        String bCategory;
        String[] bOptions;
        String[] lineSplitter;

        while (ballotFileScan.hasNextLine()) {
            lineSplitter = ballotFileScan.nextLine().split(":");
            bNum = Integer.parseInt(lineSplitter[0]);
            bCategory = lineSplitter[1];
            bOptions = lineSplitter[2].split(",");

            BallotItem ballot = new BallotItem(bNum, bCategory, bOptions);
            ballotList.add(ballot);

            File ballotRecord = new File(bNum + ".txt");
            PrintWriter writer = new PrintWriter(ballotRecord);
            for (int i = 0; i < bOptions.length; i++) {
                writer.println(bOptions[i] + ":0");
            }
            writer.close();
        }
    }

    public boolean isValid() {
        boolean valid = false;

        if (!(pswdList.isEmpty() || voterList.isEmpty() || ballotList.isEmpty()))
            valid = true;

        return valid;
    }

    public boolean continueVoting() {
        if (voterCounter == voterList.size())
            return false;

        int attemptNum = 1;
        boolean pswdCorrect = false;

        while (attemptNum < 3) {
            System.out.println("Please enter an administrator password.");
            System.out.println("This is attempt " + attemptNum + " of 2");

            Scanner S = new Scanner(System.in);
            String attempt = S.next();
            attemptNum++;

            for (int i = 0; i < pswdList.size(); i++) {
                if (attempt.equals(pswdList.get(i))) {
                    pswdCorrect = true;
                    attemptNum++; //ensures exit from loop
                }
            }
        }
        return pswdCorrect;
    }

    public void nextVoter() throws IOException {
        String temp = new JOptionPane().showInputDialog("Please enter your voter id");
        int id = Integer.parseInt(temp);

        for (Voter v : voterList) {
            String curVoter = v.getName(id);
            boolean hasVoted = v.getStatus();
            if (curVoter != null && !hasVoted) {
                new JOptionPane().showMessageDialog(null, "Welcome " + curVoter + "!" +
                        "\nYou'll be voting for each office on the ballot today");

                //ballot loop setup
                BallotItem b;
                String[] options;
                String category;
                String fileID;
                int counter = 0;
                while (counter < ballotList.size()) {
                    b = ballotList.get(counter);
                    options = b.getOptions();
                    category = b.getCategory();
                    fileID = b.getID();

                    String selection;
                    int confirm;
                    do {
                        selection = (String) JOptionPane.showInputDialog(null, "Which of these is the " + category + "?",
                                category, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                        confirm = JOptionPane.showConfirmDialog(null, "Submit your vote for " + selection + "?"
                        + "\n(null value will not be accepted)");
                    } while (confirm > 0 || selection == null);

                    //SAVE VOTE section
                    //create file and scanner with custom delimiter
                    File ballotFile = new File(fileID + ".txt");
                    Scanner fScan = new Scanner(ballotFile).useDelimiter(":");

                    //set up 2 arrays to read names/votes into
                    int index = 0;
                    String[] fOptions = new String[b.getOptions().length];
                    int[] votes = new int[b.getOptions().length];

                    //add all values to arrays
                    while (fScan.hasNextLine()) {
                        fOptions[index] = fScan.next();
                        StringBuilder tempp = new StringBuilder(fScan.nextLine());
                        tempp.deleteCharAt(0);
                        votes[index] = Integer.parseInt(tempp.toString());
                        index++;
                    }
                    fScan.close();

                    //find the index of users selection, then update votes to reflect the cast vote
                    index = 0;
                    for (String s : fOptions) {
                        if (selection.equals(s)) break;
                        else index++;
                    }
                    votes[index]++;

                    //create PrintWriter, write updated information to ballot file.
                    File tempFile = new File("tempballot.txt");
                    PrintWriter writer = new PrintWriter(tempFile);
                    for (int i = 0; i<votes.length; i++)
                        writer.println(fOptions[i] + ":" + votes[i]);
                    writer.close();

                    //System.out.println(ballotFile.getAbsolutePath());
                    Files.deleteIfExists(ballotFile.toPath());
                    tempFile.renameTo(ballotFile);

                    counter++;
                }
                voterCounter++; //ballot complete. this update allows auto-finish when voter registry is empty

                //record voter has voted (updates FALSE to TRUE for current Voter v)
                v.updateStatus();

                PrintWriter writer = new PrintWriter(voterFile);
                for (Voter v2 : voterList) {
                    writer.println(v2.getID() + ":" + v2.getName() + ":" + v2.getStatus());
                }
                writer.close();
            }
        }
    }

    public void finalize() {
        System.out.println("\nThere are either no voters left, or you failed to provide admin credentials");
        System.out.println(":::Check the following files for updates and results:::");
        System.out.println(voterFile.getName() + " for voter profile updates");
        System.out.println("    Ballot Results Files:");
        for (BallotItem b : ballotList)
            System.out.println(b.getCategory() + ": " + b.getID() + ".txt");
    }
}