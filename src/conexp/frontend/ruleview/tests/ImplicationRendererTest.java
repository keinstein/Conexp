/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.ruleview.tests;

import conexp.core.Implication;
import conexp.core.tests.SetBuilder;
import conexp.frontend.ruleview.ImplicationRenderer;
import conexp.frontend.ruleview.RuleRenderer;
import junit.framework.Test;
import junit.framework.TestSuite;


public class ImplicationRendererTest extends GenericRuleRendererTest {
    private static final Class THIS = ImplicationRendererTest.class;


    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 1:18:24)
     */
    protected void addRepresentableDependencies() {
        depSet.addDependency(new Implication(SetBuilder.makeSet(new int[]{1, 0, 0}),
                SetBuilder.makeSet(new int[]{0, 1, 1}),
                0));
        depSet.addDependency(new Implication(SetBuilder.makeSet(new int[]{0, 1, 0}),
                SetBuilder.makeSet(new int[]{0, 0, 1}),
                1));
    }


    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 1:19:11)
     */
    protected RuleRenderer makeRuleRenderer() {
        return new ImplicationRenderer();
    }


    public static Test suite() {
        return new TestSuite(THIS);
    }
}
