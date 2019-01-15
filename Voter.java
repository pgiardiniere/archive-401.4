/**
 * Created by Pedro on 7/31/2016.
 */

public class Voter {

    private int number;
    private String name;
    private boolean status;

    public Voter(int num, String n, boolean stat) {
        number = num;
        name = n;
        status = stat;
    }

    public String toString() {
        String theString;
        theString = " Number: " + Integer.toString(number) + " Has Voted: " + Boolean.toString(status) + " Name: " + name;
        return theString;
    }

    public int getID() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getName(int num) {
        String n = null;
        if (num == number)
            n = name;
        return n;
    }

    public boolean getStatus() {
        return status;
    }

    public void updateStatus() {
        status = true;
    }
}
