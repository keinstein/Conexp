package cefacade.tests;

import cefacade.CEFacadeFactory;
import cefacade.ISimpleContext;
import util.Assert;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */

public class ObjectMother {
    public static ISimpleContext buildContext(int[][] relation) {
        if (relation.length == 0) {
            return CEFacadeFactory.makeContext(0, 0);
        }
        int objectCount = relation.length;
        int attributeCount = relation[0].length;
        ISimpleContext context = CEFacadeFactory.makeContext(objectCount, attributeCount);
        for (int i = objectCount; --i >= 0;) {
            int[] row = relation[i];
            Assert.isTrue(row.length == attributeCount);
            for (int j = attributeCount; --j >= 0;) {
                context.setRelationAt(i, j, row[j] != 0);
            }
        }
        return context;
    }
}
