package generators;

import database.MongoAsyncDBConnector;
import database.MongoDBConnector;
import database.MySQLConnector;
import sampledata.SampleData;
import sampledata.SampleDataLevelOne;
import sampledata.SampleDataLevelThree;
import sampledata.SampleDataLevelTwo;


public class Example {

    public static void main(String[] args) {

        // Add our data prototypes
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelOne.class);
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelTwo.class);
        SampleDataFactory.addSampleDataObjectPrototype(SampleDataLevelThree.class);


        // Create our generator
        SampleDataGenerator generator = new SampleDataGenerator();

        // Add our InsertionThreads with their respective connectors
        generator.addInsertionThread("mysql", new MySQLConnector());
        generator.addInsertionThread("mongodb", new MongoAsyncDBConnector());

        generator.setComplexity(3);


        // Start the generator
        generator.start();

        while (true) {



            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Mysql: " + generator.getInsertionThread("mysql").getInsertionsPerSecond()
                    + " insertions per second");

            System.out.println("Mongo: " + generator.getInsertionThread("mongodb").getInsertionsPerSecond()
                    + " insertions per second");
        }

        // Increase the complexity of the data
        //generator.setComplexity(2);

    }


}
