package main.java.database;

import java.util.Random;

import main.java.sampledata.SampleData;

/**
 * Used to connect to a MongoDB database, insert a SampleData object query and
 * return an InsertionResult
 */
public class MySQLConnector implements DatabaseConnector {

    Random rng = new Random();


    @Override
    public InsertionResult insert(SampleData data) {

        /*

        TODO Insert actual DB Insertion logic here

         */

        // Testing values
        // Generate example data until database connection is implemented
        System.out.println("MySQL Connector: " + data.getInsertionStatements().get("mysql"));
        double time = (rng.nextDouble()*500);
        try {
            Thread.sleep(Math.round(time));
        } catch (Exception e) {
            e.printStackTrace();;
        }

        return new InsertionResult("MySQL", time, 0, data);
    }
}
