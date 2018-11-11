package generators;

import org.junit.Test;
import sampledata.SampleData;
import sampledata.SampleDataLevelOne;
import sampledata.SampleDataLevelTwo;

import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.assertTrue;

public class TestSampleDataFactory {

    @Test
    public void TestGenerate() {

        // Add SampleData prototypes to our factory
        Class complexityOne = SampleDataLevelOne.class;
        Class complexityTwo = SampleDataLevelTwo.class;
        SampleDataFactory.addSampleDataObjectPrototype(complexityOne);
        SampleDataFactory.addSampleDataObjectPrototype(complexityTwo);

        // First call to get data should return data of level one complexity (first in array)
        SampleData testData = SampleDataFactory.getSampleData();
        assertSame(testData.getClass(), complexityOne);

        // Increate complexity
        SampleDataFactory.setComplexity(2);

        // Test for level two complexity
        testData = SampleDataFactory.getSampleData();
        assertSame(testData.getClass(), complexityTwo);
    }

}
