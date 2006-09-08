/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations.tests;

import conexp.core.associations.AssociationMiner;
import conexp.core.associations.SecondAssociationMiner;
import junit.framework.Test;
import junit.framework.TestSuite;

public class SecondAssociationMinerTest extends AssociationMinerTest {
    private static final Class THIS = SecondAssociationMinerTest.class;

    public static Test suite() {
        return new TestSuite(THIS);
    }

    protected AssociationMiner makeAssociationMiner() {
        return new SecondAssociationMiner();
    }
}
