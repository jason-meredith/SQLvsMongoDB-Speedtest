package generators;

import sampledata.SampleData;
import sampledata.SampleDataLevelOne;

import java.util.ArrayList;

/**
 * Factory object for generate Sample Data Objects.
 *
 * Complexity can be set which determines which subclass of SampleDataObject is
 * instantiated. Complexity number == index of prototype of SampleDataObject
 * class in ArrayList to be instantiated and returned
 */
public class SampleDataFactory {

    /**
     * ArrayList of SampleDataObject prototypes to be instantiated, ordered in List
     * by complexity
     */
    protected static ArrayList<Class<? extends SampleData>> prototypes  = new ArrayList<>();

    /**
     * Complexity level of Sample Data Objects that we should be generating
     */
    private static int complexity = 1;


    /**
     * Get a new instance of SampleData at the current complexity
     * @return
     */
    public static SampleData getSampleData() {

        SampleData data = null;

        try {
             data = (SampleData) prototypes.get(complexity - 1).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    /**
     * Add a new SampleData prototype
     * @param prototype
     */
    public static void addSampleDataObjectPrototype(Class<? extends SampleData> prototype) {
        prototypes.add(prototype);
    }


    /**
     * Set the SampleData complexity
     * @param level
     */
    public static void setComplexity(int level) {
       complexity = level;
    }

    /**
     * Get the maximum complexity level available
     * @return
     */
    public int getMaxComplexityLevel() {
        return prototypes.size();
    }



}
