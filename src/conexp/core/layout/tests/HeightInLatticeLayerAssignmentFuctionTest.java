/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.tests;

import conexp.core.layout.HeightInLatticeLayerAssignmentFunction;
import conexp.core.layout.ILayerAssignmentFunction;


public class HeightInLatticeLayerAssignmentFuctionTest extends LayerAssignmentFunctionBaseTest {
    protected ILayerAssignmentFunction makeLayerAssignmentFunction() {
        return HeightInLatticeLayerAssignmentFunction.getInstance();
    }
}
