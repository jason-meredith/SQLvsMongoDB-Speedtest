package test.java.generators;

import main.java.database.InsertionResult;
import main.java.database.MongoDBConnector;
import main.java.database.MySQLConnector;
import main.java.generators.InsertionThread;
import main.java.generators.SampleDataFactory;
import main.java.generators.SampleDataGenerator;
import main.java.sampledata.SampleDataLevelOne;
import main.java.sampledata.SampleDataLevelTwo;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.omg.CORBA.portable.UnknownException;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import main.java.database.MongoDBConnector;
import main.java.database.MySQLConnector;
import main.java.sampledata.SampleDataLevelOne;
import main.java.sampledata.SampleDataLevelTwo;

public class TestSampleDataGenerator {

    SampleDataGenerator generator;

    private static final String MYSQL = "mysql";
    private static final String MONGODB = "mongodb";
	/** Create a MongoClient instance */
	static MongoClient mongoClient = null;
	/** Create a server name */
	static String serverName = "localhost";
	/** Create a server port */
	static int serverPort = 27017;
	/** Create mongoDB instance */
	static MongoDatabase database = null;

    @BeforeClass
    public static void SetupFactory() {

        // Add our data prototypes
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelOne.class);
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelTwo.class);

    }

    @Before
    public void SetupGenerator() {

    	// connect to MongoDB
    	connectToMongo();
    	
        // Create our generator
        generator = new SampleDataGenerator();

        // Add our InsertionThreads with their respective connectors
        generator.addInsertionThread(MYSQL, new MySQLConnector());
        generator.addInsertionThread(MONGODB, new MongoDBConnector(database));

    }

    @Test
    public void TestStartAll() {

        HashMap<String, InsertionThread> insertionThreads = generator.getInsertionThreads();

        // Make sure the threads are not running upon insertion
        boolean running = false;
        Iterator<?> iterator = insertionThreads.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<?, ?> pair = (Map.Entry<?, ?>)iterator.next();
            running = ((InsertionThread) pair.getValue()).isRunning();
        }

        assertFalse("InsertionThreads should start as not running", running );

        // Start the generator
        generator.start();

        iterator = insertionThreads.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<?, ?> pair = (Map.Entry<?, ?>)iterator.next();
            running = ((InsertionThread) pair.getValue()).isRunning();
        }

        assertTrue("InsertionThreads should start be running after start()", running );

    }

    /**
     * Makes sure that the same values are being sent to every InsertionThread
     */
    @Test
    public void TestValueEquivalence() {

        // Start the generator
        generator.start();

        // Wait for it to run a bit first
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        generator.stop();

        // Get results
        ArrayList<InsertionResult> mysqlResults = generator.getResults().get(MYSQL);
        ArrayList<InsertionResult> mongoResults = generator.getResults().get(MONGODB);

        // The amount we check should be the smaller one
        int amount = mysqlResults.size();
        if(mongoResults.size() < amount)
            amount = mongoResults.size();

        // Check the query run for each result to make sure the data was the same
        for(int i = 0; i < amount; i++) {
            int mysqlData = mysqlResults.get(i).getSampleData().getIdentifier();
            int mongoData = mongoResults.get(i).getSampleData().getIdentifier();

            assertEquals(mysqlData, mongoData);
        }

    }

    /**
     * Make sure when the generator complexity is increased we actually start using the more complex objects
     */
    @Test
    public void TestComplexityIncrease() {
        // Start the generator
        generator.start();

        // Wait for it to run a bit first
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Make sure we are using the complexity 1 class
        assertEquals(generator.getResults().get(MYSQL).get(0).getSampleData().getClass(), SampleDataLevelOne.class);

        // Increase the complexity
        generator.setComplexity(2);

        // Wait for it to run a bit first
        try {
            Thread.sleep(4000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Get the last element
        int lastElement = generator.getResults().get(MYSQL).size() - 1;

        // Make sure we are now using the complexity 2 class
        assertEquals(generator.getResults().get(MYSQL).get(lastElement).getSampleData().getClass(), SampleDataLevelTwo.class);

    }
    
	static MongoDatabase connectToMongo() {
		try {
			mongoClient = new MongoClient();
			// Create a database instance to get db
			database = mongoClient.getDatabase("project");

		} catch (UnknownException e) {
			System.err.println("Error connecting to MongoDB Client.");
			Logger.getLogger(Mongo.class.getName()).log(Level.SEVERE, null, e);
		}
		return database;
	}

}
