package main.java.database;

import main.java.sampledata.SampleData;

public interface DatabaseConnector {

    InsertionResult insert(SampleData data);

}
