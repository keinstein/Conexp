/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import util.Assert;


public class ContextFunctions {
    private ContextFunctions() {
    }

    //TODO: think about variant with attribute mask
    public static int stabilityToDesctruction(Set s, ExtendedContextEditingInterface cxt) {

        BinaryRelation rel = cxt.getRelation();
        final int colCount = rel.getColCount();
        int[] attributeStabilities = new int[colCount];
        final int rowCount = rel.getRowCount();
        boolean isFull = s.elementCount() == colCount;

        if (isFull) {
            int extentSize = 0;
            for (int i = 0; i < rowCount; i++) {
                if (rel.getSet(i).equals(s)) {
                    extentSize++;
                }
            }
            return extentSize;
        }

        for (int i = 0; i < rowCount; i++) {
            Set currObj = rel.getSet(i);
            if (s.isSubsetOf(currObj)) {
                for (int j = 0; j < colCount; j++) {
                    if (!currObj.in(j)) {
                        attributeStabilities[j]++;
                    }
                }
            }
        }
        int minStability = 0;
        for (int j = 0; j < colCount; j++) {
            if (!s.in(j)) {
                if (minStability == 0) {
                    minStability = attributeStabilities[j];
                } else if (minStability > attributeStabilities[j]) {
                    minStability = attributeStabilities[j];
                }
            }
        }
        return minStability;
    }

    public static int idealSize(Set queryIntent, ExtendedContextEditingInterface cxt, Set attributeMask) {
        Assert.isTrue(queryIntent.size() == attributeMask.size());
        Assert.isTrue(queryIntent.isSubsetOf(attributeMask));
        BinaryRelation relation = cxt.getRelation();
        ModifiableSet temp = ContextFactoryRegistry.createSet(queryIntent.size());
        int ret = 0;
        for (int i = 0; i < relation.getRowCount(); i++) {
            temp.copy(relation.getSet(i));
            temp.and(attributeMask);
            if (queryIntent.isSubsetOf(temp)) {
                ret++;
            }
        }
        return ret;
    }

    public static int contingentSize(Set queryIntent, ExtendedContextEditingInterface cxt, Set attributeMask) {
        BinaryRelation relation = cxt.getRelation();
        ModifiableSet temp = ContextFactoryRegistry.createSet(queryIntent.size());
        int ret = 0;
        for (int i = 0; i < relation.getRowCount(); i++) {
            temp.copy(relation.getSet(i));
            temp.and(attributeMask);
            if (queryIntent.isEquals(temp)) {
                ret++;
            }
        }
        return ret;
    }

}
