/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.relationsequences.tests;

import conexp.core.tests.SetBuilder;
import conexp.experimenter.relationsequences.ContextLoadingRelationGenerationStrategy;
import junit.framework.TestCase;
import util.testing.TestUtil;



public class ContextLoadingRelationGenerationStrategyTest extends TestCase {
    public static void testLoading() {
        String url = "conexp/experimenter/relationsequences/tests/C2.cxt";
        try {
            ContextLoadingRelationGenerationStrategy strategy = new ContextLoadingRelationGenerationStrategy(url);
            assertEquals(1, strategy.getRelationCount());
            assertEquals(SetBuilder.makeRelation(new int[][]{{1, 1},
                    {1, 0}}), strategy.getRelation(0));
        } catch (Exception e) {
            TestUtil.reportUnexpectedException(e);
        }


    }
}
