package main.java.generators;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.CORBA.portable.UnknownException;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import main.java.database.MongoDBConnector;
import main.java.database.MySQLConnector;
import main.java.sampledata.SampleDataLevelOne;
import main.java.sampledata.SampleDataLevelTwo;


public class Example {

	/** Create a MongoClient instance */
	static MongoClient mongoClient = null;
	/** Create a server name */
	static String serverName = "localhost";
	/** Create a server port */
	static int serverPort = 27017;
	/** Create mongoDB instance */
	static MongoDatabase database = null;

    public static void main(String[] args) {

        // Add our data prototypes
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelOne.class);
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelTwo.class);

        
        // Connect to MongoDB
        connectToMongo();
        
        // Create our generator
        SampleDataGenerator generator = new SampleDataGenerator();

        // Add our InsertionThreads with their respective connectors
        generator.addInsertionThread("mysql", new MySQLConnector());
        generator.addInsertionThread("mongodb", new MongoDBConnector(database));


        // Start the generator
        generator.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Increase the complexity of the data
        generator.setComplexity(2);

    }
    
	static void connectToMongo() {
		try {
			mongoClient = new MongoClient();
			// Create a database instance to get db
			database = mongoClient.getDatabase("project");

		} catch (UnknownException e) {
			System.err.println("Error connecting to MongoDB Client.");
			Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, e);
		}
	}

}
