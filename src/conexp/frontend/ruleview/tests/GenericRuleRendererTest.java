/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview.tests;

import conexp.core.Context;
import conexp.core.DependencySet;
import conexp.core.tests.SetBuilder;
import conexp.frontend.ruleview.RuleRenderer;
import junit.framework.TestCase;


public abstract class GenericRuleRendererTest extends TestCase {

    private RuleRenderer renderer;

    protected DependencySet depSet;

    protected abstract void addRepresentableDependencies();


    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 1:19:11)
     */
    protected abstract RuleRenderer makeRuleRenderer();


    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 0:45:19)
     */
    protected void setUp() {
        Context cxt = SetBuilder.makeContext(new int[][]{{0, 1, 1},
                {0, 0, 1}});
        depSet = new DependencySet(cxt);
        addRepresentableDependencies();
        renderer = makeRuleRenderer();
    }


    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 1:10:10)
     */
    public void testDependencyStyle() {
        for (int i = depSet.getSize(); --i >= 0;) {
            assertNotNull(renderer.dependencyStyle(depSet.getDependency(i)));
        }
    }


    public void testDescribeImplication() {
        for (int i = depSet.getSize(); --i >= 0;) {
            StringBuffer buf = new StringBuffer();
            renderer.describeRule(buf, depSet.getAttributesInformation(), depSet.getDependency(i));
            assertTrue(buf.toString().length() > 0);
        }
    }
}
