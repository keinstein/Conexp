/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.latticeeditor.labelingstrategies.tests;

import conexp.frontend.latticeeditor.labelingstrategies.GenericLabelingStrategy;
import conexp.frontend.latticeeditor.labelingstrategies.ObjectsCountLabelingStrategy;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import conexp.frontend.latticeeditor.queries.ConceptNodeQueryFactory;
import junit.framework.Test;
import junit.framework.TestSuite;

public class ObjectsCountLabelingStrategyTest extends GenericLabelingStrategyTest {
    protected ConceptNodeQuery makeAcceptable() {
        return ConceptNodeQueryFactory.makeWithOwnObjects();
    }

    protected GenericLabelingStrategy makeStrategy() {
        return new ObjectsCountLabelingStrategy(new conexp.frontend.latticeeditor.LatticePainterDrawParams());
    }

    public static Test suite() {
        return new TestSuite(ObjectsCountLabelingStrategyTest.class);
    }
}
