package conexp.core.layout.tests;

import conexp.core.layout.HeightInLatticeLayerAssignmentFunction;
import conexp.core.layout.ILayerAssignmentFunction;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public class HeightInLatticeLayerAssignmentFuctionTest extends LayerAssignmentFunctionBaseTest {
    protected ILayerAssignmentFunction makeLayerAssignmentFunction() {
        return HeightInLatticeLayerAssignmentFunction.getInstance();
    }
}
