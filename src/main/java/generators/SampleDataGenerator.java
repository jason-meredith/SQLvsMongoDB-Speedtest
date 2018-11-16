package main.java.generators;

import main.java.database.DatabaseConnector;
import main.java.database.InsertionResult;
import main.java.sampledata.SampleData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The main backend controlling class.
 *
 * For every database that is being tested an InsertionThread object is instantiated and
 * accessed directly through a HashMap. The InsertionThread, which extends Runnable, is
 * executed through the ExecutorService threadPool.
 *
 * Calling start() will iterate through all InsertionThreads and call their respective start()
 * method, just as call stop() will call their stop() methods.
 *
 */
public class SampleDataGenerator implements SampleDataGeneratorInterface {

    private ExecutorService threadPool;
    private HashMap<String, InsertionThread> insertionThreads;
    private ArrayBlockingQueue<SampleData> sampleData;


    public SampleDataGenerator() {


        threadPool = Executors.newCachedThreadPool();
        insertionThreads = new HashMap<String, InsertionThread>();
        sampleData = new ArrayBlockingQueue<>(100);



        /*
         This thread will continually generate sample data and load it into
         our Queue
          */
        threadPool.execute(() -> {
            while(true) {
                try {
                    sampleData.put(SampleDataFactory.getSampleData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        /*
         This separate thread will continually take Sample Data from our queue
         and clone it into each InsertionThread
          */
        threadPool.execute(() -> {
            while (true) {

                try {
                    // Take the next Sample Data Object from our Queue
                    SampleData nextDataObject = SampleDataGenerator.this.sampleData.take();

                    // Iterate over each InsertionThread and add a clone to it's own Queue
                    Iterator<?> iterator = insertionThreads.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<?, ?> pair = (Map.Entry<?, ?>)iterator.next();

                        ((InsertionThread) pair.getValue()).addSampleData(nextDataObject.getClone());
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    /**
     * Iterates over each InsertionThread and starts them
     */
    public synchronized void start() {
        Iterator<?> iterator = insertionThreads.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<?, ?> pair = (Map.Entry<?, ?>)iterator.next();
            InsertionThread thread = ((InsertionThread) pair.getValue());
            threadPool.execute(thread);
            thread.start();
        }
    }

    /**
     * Iterate over each InsertionThread and compile all their InsertionResults
     * into a HashMap with their names as the keys
     * @return
     */
    public HashMap<String, ArrayList<InsertionResult>> getResults() {
        HashMap<String, ArrayList<InsertionResult>> results = new HashMap<>();

        // Iterate over each InsertionThread
        Iterator<?> iterator = insertionThreads.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<?, ?> pair = (Map.Entry<?, ?>)iterator.next();
            String key = (String) pair.getKey();
            ArrayList<InsertionResult> value = ((InsertionThread) pair.getValue()).getResults();

            results.put(key, value);
        }

        return results;
    }

    /**
     * Iterates over each InsertionThread and stops them
     */
    public synchronized void stop() {
        // Iterate over all keys and call stop()
        Iterator<?> iterator = insertionThreads.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<?, ?> pair = (Map.Entry<?, ?>)iterator.next();
            ((InsertionThread) pair.getValue()).stop();
        }
    }


    /**
     * Add a new InsertionThread
     * @param name Name of InsertionThread
     * @param connector Database Connector to use
     */
    public void addInsertionThread(String name, DatabaseConnector connector) {
        this.insertionThreads.put(name, new InsertionThread(connector));
    }


    /**
     * Get an InsertionThread
     * @param name Name of InsertionThread you wish to retrieve
     * @return
     */
    public InsertionThread getInsertionThread(String name) {
        return this.insertionThreads.get(name);
    }

    /**
     * Get all InsertionThreads
     * @return
     */
    public HashMap<String, InsertionThread> getInsertionThreads() {
        return this.insertionThreads;
    }

    /**
     * Sets the complexity of the data being generated and removes all
     * Queued SampleData
     * @param level
     */
    public void setComplexity(int level) {
        SampleDataFactory.setComplexity(level);
        this.sampleData.clear();

        Iterator<?> iterator = insertionThreads.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<?, ?> pair = (Map.Entry<?, ?>)iterator.next();
            ((InsertionThread) pair.getValue()).clearQueuedSampleData();
        }
    }





}
