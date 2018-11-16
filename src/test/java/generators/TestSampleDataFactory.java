package test.java.generators;

import org.junit.Test;

import main.java.generators.SampleDataFactory;
import main.java.sampledata.SampleData;
import main.java.sampledata.SampleDataLevelOne;
import main.java.sampledata.SampleDataLevelTwo;

import static junit.framework.TestCase.assertSame;

public class TestSampleDataFactory {

    @Test
    public void TestGenerate() {

        // Add SampleData prototypes to our factory
        Class<SampleDataLevelOne> complexityOne = SampleDataLevelOne.class;
        Class<SampleDataLevelTwo> complexityTwo = SampleDataLevelTwo.class;
        SampleDataFactory.addSampleDataObjectPrototype(complexityOne);
        SampleDataFactory.addSampleDataObjectPrototype(complexityTwo);

        // First call to get data should return data of level one complexity (first in array)
        SampleData testData = SampleDataFactory.getSampleData();
        assertSame(testData.getClass(), complexityOne);

        // Increase complexity
        SampleDataFactory.setComplexity(2);

        // Test for level two complexity
        testData = SampleDataFactory.getSampleData();
        assertSame(testData.getClass(), complexityTwo);
    }

}
