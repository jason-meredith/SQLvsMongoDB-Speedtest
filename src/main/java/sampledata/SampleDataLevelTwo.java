package sampledata;

import java.util.HashMap;
import java.util.Random;

public class SampleDataLevelTwo implements SampleData {

    public int identifier;


    public static String tableName = "SampleDataLevelTwo";


    private int alpha, beta, gamma, delta, theta;

    public SampleDataLevelTwo() {
        Random rng = new Random();


        alpha = rng.nextInt();
        beta = rng.nextInt();
        gamma = rng.nextInt();
        delta = rng.nextInt();
        theta = rng.nextInt();

        identifier = rng.nextInt();

    }


    @Override
    public SampleData getClone() {
        SampleDataLevelTwo clone = new SampleDataLevelTwo();

        clone.alpha = this.alpha;
        clone.beta = this.beta;
        clone.gamma = this.gamma;
        clone.delta = this.delta;
        clone.theta = this.theta;

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

        String sqlStatement = "INSERT INTO " + tableName + " VALUES("
                + String.valueOf(this.alpha) + ", "
                + String.valueOf(this.beta) + ", "
                + String.valueOf(this.gamma) + ", "
                + String.valueOf(this.delta) + ", "
                + String.valueOf(this.theta) + "); ";

        insertStatements.put("mysql", sqlStatement);
        insertStatements.put("mongodb", "db." + tableName+ ".insert( { randomNumber: " + this.alpha + " } )");

        return insertStatements;
    }

    public int getIdentifier() {
        return this.identifier;
    }

}
