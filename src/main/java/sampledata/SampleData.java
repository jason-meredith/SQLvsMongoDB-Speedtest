package sampledata;

import java.util.HashMap;

public interface SampleData {

    SampleData getClone();

    //String getSqlInsertStatement();
    //String getMongoInsertStatement();

    HashMap<String, String> getInsertionStatements();

    int getIdentifier();
}
