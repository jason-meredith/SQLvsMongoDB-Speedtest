package generators;

import database.DatabaseConnector;
import database.InsertionResult;
import sampledata.SampleData;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * InsertionThread is a runnable class that queues Sample Object Data to
 * be passed off to its respective Database Connector
 */
public class InsertionThread implements Runnable {

    /**
     * Queue of Sample Data Objects to be passed to the Database Connector
     */
    private ArrayBlockingQueue<SampleData> dataQueue;

    /**
     * Are we currently supposed to be dequeuing objects to the database?
     */
    private boolean running;

    private long startTime, runtime;

    private int successfulInsertions;

    /**
     * The database connector used to insert our objects
     */
    private DatabaseConnector connector;

    private ArrayList<InsertionResult> results;

    public InsertionThread(DatabaseConnector connector) {

        this.dataQueue = new ArrayBlockingQueue<SampleData>(100);
        this.running = false;
        this.connector = connector;
        this.results = new ArrayList<>();
        this.successfulInsertions = 0;

    }

    /**
     * Start inserting data
     */
    public void start() {
        this.running = true;
        startTime = System.currentTimeMillis();
        successfulInsertions = 0;
    }

    /**
     * Stop inserting data
     */
    public void stop() {
        this.running = false;
        runtime = 0;
    }

    /**
     * Nested while-loop so it can before turned off and on repeatedly at will
     */
    public void run() {
        while(true) {
            while (running) {
                try {
                    SampleData data = dataQueue.take();
                    InsertionResult insertionResult = connector.insert(data);
                    successfulInsertions++;
                    runtime = System.currentTimeMillis() - this.startTime;
                    results.add(insertionResult); /* <- returns an InsertionResult object (complexity/database/time) (*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    /**
     * Is this Insertion Thread running?
     * @return
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Add a Sample Data Object to our Queue
     * @param data
     */
    public void addSampleData(SampleData data) {
        try {
            this.dataQueue.put(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clear all Sample Data Objects from our Queue
     */
    public synchronized void clearQueuedSampleData() {
        this.dataQueue.clear();
    }


    /**
     * Get all the Results returned to this InsertionThread from the insertions it has performed
     * @return
     */
    public ArrayList<InsertionResult> getResults() {
        return this.results;
    }

    public double getInsertionsPerSecond() {

        if(runtime < 10) {
            runtime = 1000;
        }

        double insertionsPerSecond = (double) (successfulInsertions + 1) / (runtime / 1000);

        if(insertionsPerSecond < 0.5)
            return 0;

        return insertionsPerSecond;

    }


}
