package sampledata;

import java.util.HashMap;
import java.util.Random;

public class SampleDataLevelTwo implements SampleData {

    public int identifier;


    public static String tableName = "SampleDataLevelTwo";

    private static int id = 0;


    private int alpha, beta, gamma, delta, theta;

    public SampleDataLevelTwo() {
        Random rng = new Random();


        alpha = rng.nextInt(500);
        beta = rng.nextInt(500);
        gamma = rng.nextInt(500);
        delta = rng.nextInt(500);
        theta = rng.nextInt(500);

        identifier = id++;

    }



    @Override
    public SampleData getClone() {
        SampleDataLevelTwo clone = new SampleDataLevelTwo();

        clone.identifier = this.identifier;
        clone.alpha = this.alpha;
        clone.beta = this.beta;
        clone.gamma = this.gamma;
        clone.delta = this.delta;
        clone.theta = this.theta;


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
                + String.valueOf(getIdentifier()) + ", "
                + String.valueOf(this.alpha) + ", "
                + String.valueOf(this.beta) + ", "
                + String.valueOf(this.gamma) + ", "
                + String.valueOf(this.delta) + ", "
                + String.valueOf(this.theta) + "); ";

        String mongoStatement = "{"
                + "insert: \"" + tableName + "\", "
                + "documents: [ { "
                + "_id:" + String.valueOf(getIdentifier()) + ", "
                + "alpha: " + String.valueOf(this.alpha) + ", "
                + "beta: " + String.valueOf(this.beta) + ", "
                + "gamma: " + String.valueOf(this.gamma) + ", "
                + "delta: " + String.valueOf(this.delta) + ", "
                + "theta: " + String.valueOf(this.theta) + " } ] }";


        insertStatements.put("mysql", sqlStatement);
        insertStatements.put("mongodb", mongoStatement);

        return insertStatements;
    }

    public int getIdentifier() {
        return this.identifier;
    }

}