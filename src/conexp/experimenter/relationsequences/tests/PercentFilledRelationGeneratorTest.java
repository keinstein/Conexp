package conexp.experimenter.relationsequences.tests;

import conexp.experimenter.relationsequences.PercentFilledRelationGenerationStrategy;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit test case for PercentFilledRelationGeneratorTest
 */

public class PercentFilledRelationGeneratorTest extends TestCase {
    public void testCalcRelationSize() {
        PercentFilledRelationGenerationStrategy seq = new PercentFilledRelationGenerationStrategy(5, 100, 20, 20, 20, 0.1);
        assertEquals(20, seq.calcRelationSizeY(0));
        assertEquals(20, seq.calcRelationSizeY(19));

        assertEquals(5, seq.calcRelationSizeX(0));
        assertEquals(100, seq.calcRelationSizeX(19));
    }
}