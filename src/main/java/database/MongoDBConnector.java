package database;

import generators.InsertionThread;
import sampledata.SampleData;

import java.util.Random;

/**
 * Used to connect to a MongoDB database, insert a SampleData object query and
 * return an InsertionResult
 */
public class MongoDBConnector implements DatabaseConnector {
    Random rng = new Random();


    @Override
    public InsertionResult insert(SampleData data) {

        /*

        TODO Insert actual DB Insertion logic here

         */


        // Testing values
        // Generate example data until database connection is implemented
        System.out.println("MongoDB Connector: " + data.getInsertionStatements().get("mongodb"));
        double time = (rng.nextDouble()*500);
        try {
            Thread.sleep(Math.round(time));
        } catch (Exception e) {
            e.printStackTrace();;
        }

        return new InsertionResult("MongoDB", time, 0, data);
    }
}
