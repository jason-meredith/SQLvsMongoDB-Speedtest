package sampledata;

import java.util.HashMap;
import java.util.Random;

public class SampleDataLevelOne implements SampleData {

    public int identifier;


    public static String tableName = "SampleDataLevelOne";


    private static int id = 0;

    private int randomNumber;

    public SampleDataLevelOne() {
        Random rng = new Random();

        randomNumber = rng.nextInt(500);

        identifier = id++;

    }


    @Override
    public SampleData getClone() {
        SampleDataLevelOne clone = new SampleDataLevelOne();

        clone.identifier = this.identifier;
        clone.randomNumber = this.randomNumber;

        return clone;
    }


    /**
     * Return a HashMap of database insertion statements representing this object based on different database
     * types given as the HashMap key
     * @return
     */
    public HashMap<String, String> getInsertionStatements() {
        HashMap<String, String> insertStatements = new HashMap<>();


        String sqlStatement = "INSERT INTO " + tableName + " VALUES("
                + String.valueOf(identifier) + ", "
                + String.valueOf(randomNumber) + "); ";

        String mongoStatement = "{ insert: \"" + tableName + "\", "
                + "documents: [ { "
                    + "_id: " + String.valueOf(getIdentifier()) + ", "
                    + "randomNumber: " + String.valueOf(this.randomNumber) + " } ] }";


        insertStatements.put("mysql", sqlStatement);
        insertStatements.put("mongodb", mongoStatement);

        return insertStatements;
    }

    public int getIdentifier() {
        return this.identifier;
    }


}
