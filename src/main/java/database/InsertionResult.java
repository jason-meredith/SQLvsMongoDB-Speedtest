package database;

import sampledata.SampleData;

/**
 * InsertionResult is a snippet of data representing the result of a single Insertion, containing the
 * original data that was inserted and how long it took
 */
public class InsertionResult {

    /**
     * The name of the Connector used to return this result
     */
    private String connectorName;

    /**
     * Total amount of time this insertion took
     */
    private long insertionTime;

    /**
     * Complexity of the data used to return this result
     */
    private int complexity;

    /**
     * Original data used for this insertion
     */
    private SampleData sampleData;

    public InsertionResult(String connectorName, long insertionTime, int complexity, SampleData sampleData) {
        this.connectorName = connectorName;
        this.insertionTime = insertionTime;
        this.complexity = complexity;
        this.sampleData = sampleData;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public long getInsertionTime() {
        return insertionTime;
    }

    public int getComplexity() {
        return complexity;
    }

    public SampleData getSampleData() {
        return sampleData;
    }
}
