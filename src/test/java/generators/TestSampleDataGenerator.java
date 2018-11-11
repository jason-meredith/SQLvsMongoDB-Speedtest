package generators;

import database.InsertionResult;
import database.MongoDBConnector;
import database.MySQLConnector;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sampledata.SampleDataLevelOne;
import sampledata.SampleDataLevelTwo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSampleDataGenerator {

    SampleDataGenerator generator;

    private static final String MYSQL = "mysql";
    private static final String MONGODB = "mongodb";

    @BeforeClass
    public static void SetupFactory() {

        // Add our data prototypes
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelOne.class);
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelTwo.class);

    }

    @Before
    public void SetupGenerator() {

        // Create our generator
        generator = new SampleDataGenerator();

        // Add our InsertionThreads with their respective connectors
        generator.addInsertionThread(MYSQL, new MySQLConnector());
        generator.addInsertionThread(MONGODB, new MongoDBConnector());

    }

    @Test
    public void TestStartAll() {

        HashMap<String, InsertionThread> insertionThreads = generator.getInsertionThreads();

        // Make sure the threads are not running upon insertion
        boolean running = false;
        Iterator iterator = insertionThreads.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            running = ((InsertionThread) pair.getValue()).isRunning();
        }

        assertFalse("InsertionThreads should start as not running", running );

        // Start the generator
        generator.start();

        iterator = insertionThreads.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
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

}
