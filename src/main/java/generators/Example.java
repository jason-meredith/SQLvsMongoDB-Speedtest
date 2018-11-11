package generators;

import database.MongoDBConnector;
import database.MySQLConnector;
import sampledata.SampleDataLevelOne;
import sampledata.SampleDataLevelTwo;

public class Example {

    public static void main(String[] args) {

        // Add our data prototypes
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelOne.class);
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelTwo.class);

        // Create our generator
        SampleDataGenerator generator = new SampleDataGenerator();

        // Add our InsertionThreads with their respective connectors
        generator.addInsertionThread("mysql", new MySQLConnector());
        generator.addInsertionThread("mongodb", new MongoDBConnector());


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

}
