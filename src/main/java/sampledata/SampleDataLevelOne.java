package sampledata;

import java.util.HashMap;
import java.util.Random;

public class SampleDataLevelOne implements SampleData {

    public int identifier;


    public static String tableName = "SampleDataLevelOne";


    private int randomNumber;

    public SampleDataLevelOne() {
        Random rng = new Random();

        randomNumber = rng.nextInt();

        identifier = rng.nextInt();

    }


    @Override
    public SampleData getClone() {
        SampleDataLevelOne clone = new SampleDataLevelOne();

        clone.randomNumber = this.randomNumber;
        clone.identifier = this.identifier;

        return clone;
    }


    /**
     * Return a HashMap of database insertion statements representing this object based on different database
     * types given as the HashMap key
     * @return
     */
    public HashMap<String, String> getInsertionStatements() {
        HashMap<String, String> insertStatements = new HashMap<>();

        insertStatements.put("mysql", "INSERT INTO " + tableName + " VALUES(" + String.valueOf(this.randomNumber) + ");");
        insertStatements.put("mongodb", "db." + tableName+ ".insert( { randomNumber: " + this.randomNumber + " } )");

        return insertStatements;
    }

    public int getIdentifier() {
        return this.identifier;
    }


}
