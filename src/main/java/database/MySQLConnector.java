package database;

import java.util.Random;

import sampledata.SampleData;

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
        System.out.println("---");
        System.out.println("MySQL Connector: " + data.getInsertionStatements().get("mysql"));
        System.out.println("\n");


        return new InsertionResult("MySQL", 0, 0, data);
    }
}
