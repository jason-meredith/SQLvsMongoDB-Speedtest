package generators;

import database.InsertionResult;

import java.util.ArrayList;
import java.util.HashMap;

public interface SampleDataGeneratorInterface {

    void start();

    void stop();

    void setComplexity(int level);

    HashMap<String, ArrayList<InsertionResult>> getResults();


}
