/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.stability;

import conexp.core.BinaryRelation;
import conexp.core.ContextFactoryRegistry;
import conexp.core.DefaultBinaryRelationProcessor;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import util.MathUtil;
import util.collection.CollectionFactory;

import java.util.Iterator;
import java.util.Map;



public class PointAndIntegralStabilityCalculator extends DefaultBinaryRelationProcessor implements PointStabilityCalculator, IntegralStabilityCalculator {
    private int powerOfObjectCount;

    public void setRelation(BinaryRelation relation) {
        super.setRelation(relation);
        calcStability();
    }

    private Map setToOccurencesMap = CollectionFactory.createDefaultMap();
    private Map setToSumOfOccurencesMap = CollectionFactory.createDefaultMap();

    private ModifiableSet[] intersectionsByDepth;

    private void calcStability() {
        setToOccurencesMap.clear();
        final int rowCount = getRelation().getRowCount();
        intersectionsByDepth = new ModifiableSet[rowCount];
        final int colCount = getRelation().getColCount();
        for (int i = 0; i < intersectionsByDepth.length; i++) {
            intersectionsByDepth[i] = ContextFactoryRegistry.createSet(colCount);
        }
        ModifiableSet currentIntersection = ContextFactoryRegistry.createSet(colCount);
        currentIntersection.fill();
        doCalcStabilityOfSubsets(0, currentIntersection);
        intersectionsByDepth = null;
        powerOfObjectCount = MathUtil.powerOfTwo(rowCount);
        calculateSumsOverSubsets();
    }

    private void calculateSumsOverSubsets() {
        //this implementation is the simplest one and is extremely inefficient as such.
        for (Iterator outerIter = setToOccurencesMap.keySet().iterator(); outerIter.hasNext();) {
            Set outer = (Set) outerIter.next();
            int count = 0;
            for (Iterator innerIter = setToOccurencesMap.keySet().iterator(); innerIter.hasNext();) {
                Set inner = (Set) innerIter.next();
                if (inner.isSubsetOf(outer)) {
                    count += getPointOccurencesForSet(inner);
                }
            }
            setToSumOfOccurencesMap.put(outer, new Integer(count));
        }
    }

    private void doCalcStabilityOfSubsets(int currentDepth, Set currentIntersection) {
        if (currentDepth >= intersectionsByDepth.length) {
            Integer currentCount = (Integer) setToOccurencesMap.get(currentIntersection);
            if (currentCount == null) {
                //here we creating permanent copy, it's first time generation of this set
                setToOccurencesMap.put(currentIntersection.makeModifiableSetCopy(), new Integer(1));
            } else {
                setToOccurencesMap.put(currentIntersection, new Integer(currentCount.intValue() + 1));
            }
            return;
        }
        int currentObjectIndex = currentDepth;
        Set currentObjectIntent = getRelation().getSet(currentObjectIndex);
        ModifiableSet nextIntersection = intersectionsByDepth[currentDepth];
        nextIntersection.copy(currentIntersection);
        int nextDepth = currentDepth + 1;
        //calc stabilityToDesctruction without current object
        doCalcStabilityOfSubsets(nextDepth, nextIntersection);
        //calc stabilityToDesctruction of subsets with current object
        nextIntersection.and(currentObjectIntent);

        doCalcStabilityOfSubsets(nextDepth, nextIntersection);
    }


    public double getIntegralStabilityForSet(Set set) {
        return (double) safeGetIntFromMap(setToSumOfOccurencesMap, set) / (double) powerOfObjectCount;
    }

    public double getPointStabilityOfSet(Set set) {
        return (double) getPointOccurencesForSet(set) / (double) powerOfObjectCount;
    }

    private int getPointOccurencesForSet(Set set) {
        return safeGetIntFromMap(setToOccurencesMap, set);
    }

    private static int safeGetIntFromMap(final Map map, Set set) {
        Integer occurencesCount = (Integer) map.get(set);
        if (null == occurencesCount) {
            return 0;
        }
        return occurencesCount.intValue();
    }

    public String toString() {
        return setToOccurencesMap.toString();
    }

    public void tearDown() {
        super.tearDown();
        setToOccurencesMap.clear();
        setToSumOfOccurencesMap.clear();
    }

}
