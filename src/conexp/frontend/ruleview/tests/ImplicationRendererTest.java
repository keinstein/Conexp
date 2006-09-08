/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview.tests;

import conexp.core.tests.ImplicationsBuilder;
import conexp.frontend.ruleview.ImplicationRenderer;
import conexp.frontend.ruleview.RuleRenderer;


public class ImplicationRendererTest extends GenericRuleRendererTest {
    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 1:18:24)
     */
    protected void addRepresentableDependencies() {
        int[] premiseSet = new int[]{1, 0, 0};
        int[] conclusionSet = new int[]{0, 1, 1};
        int numObjects = 0;
        depSet.addDependency(ImplicationsBuilder.makeImplication(premiseSet, conclusionSet, numObjects));
        depSet.addDependency(ImplicationsBuilder.makeImplication(new int[]{0, 1, 0}, new int[]{0, 0, 1}, 1));
    }

    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 1:19:11)
     */
    protected RuleRenderer makeRuleRenderer() {
        return new ImplicationRenderer();
    }
}
