/**
 * Created by Pedro on 7/31/2016.
 */

public class BallotItem {

    private int idNumber;
    private String category;
    private String[] options;

    public BallotItem() {}

    public BallotItem(int n, String c, String[] o) {
        idNumber = n;
        category = c;
        options = o;
    }

    public String toString() {
        StringBuilder tempOptionList = new StringBuilder();
        for (int i = 0; i < options.length; i++)
            tempOptionList.append(options[i] + ", ");
        String theString;
        theString = "NUMBER: " + Integer.toString(idNumber) +" CATEGORY: " + category + " OPTIONS: " + tempOptionList.toString();
        return theString;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCategory() {
        return category;
    }

    public String getID() {
        String fileID = Integer.toString(idNumber);
        return fileID;
    }
}
