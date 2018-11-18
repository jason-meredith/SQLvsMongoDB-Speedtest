package database;

import sampledata.SampleData;

public interface DatabaseConnector {

    InsertionResult insert(SampleData data);

}
