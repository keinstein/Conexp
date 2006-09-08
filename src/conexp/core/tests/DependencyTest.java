/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.tests;

import conexp.core.Dependency;
import junit.framework.TestCase;

public abstract class DependencyTest extends TestCase {

    protected static void checkInvariants(Dependency dep) {
        assertNotNull(dep.getPremise());
        assertNotNull(dep.getConclusion());
        if (0 == dep.getPremiseSupport()) {
            assertEquals(1.0, dep.getConfidence(), 0.001);
            assertEquals(0, dep.getRuleSupport());
        } else {
            assertEquals(dep.getConfidence(), dep.getRuleSupport() / dep.getPremiseSupport(), 0.001);
        }
    }
}
