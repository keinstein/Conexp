/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.stability;

import conexp.core.BinaryRelation;
import conexp.core.BinaryRelationUtils;
import conexp.core.ContextFactoryRegistry;
import conexp.core.DefaultBinaryRelationProcessor;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import util.MathUtil;



public class OneConceptPointStabilityCalculator extends DefaultBinaryRelationProcessor implements PointStabilityCalculator {
    private ModifiableSet[] currentExtents;
    private ModifiableSet[] visitedSubsets;
    private int maxDepth;

    public OneConceptPointStabilityCalculator() {
    }

    public double getPointStabilityOfSet(Set attributeSet) {
        /**
         *  Basic ideas of the algorithm:
         *  we are performing search by all subsets of extent of this intent
         *  (in case, when intent is a closed set). If it is not a closed set,
         *  than search is pruned on the base of following property:
         *  if closure of the subset of object  not equal to closure of set, than
         *  closure of all of its subsets also will not be equal to closure of object
         */


        final BinaryRelation relation = getRelation();
        ModifiableSet extent = BinaryRelationUtils.derivationOfAttributeSet(relation, attributeSet);
        ModifiableSet closedAttributeSet = BinaryRelationUtils.derivationOfObjectsSet(relation, extent);
        if (!attributeSet.isEquals(closedAttributeSet)) {
            return 0;
        }
        return calculateCountOfGeneratorsBySubsets(extent, attributeSet);


    }

    private double calculateCountOfGeneratorsBySubsets(ModifiableSet extent, Set attributeSet) {
        maxDepth = extent.elementCount();
        int count;
        if (maxDepth == 0) {
            count = 1;
        } else {
            currentExtents = new ModifiableSet[maxDepth];
            visitedSubsets = new ModifiableSet[maxDepth];

            final int rowCount = extent.size();
            for (int i = 0; i < maxDepth; i++) {
                currentExtents[i] = ContextFactoryRegistry.createSet(rowCount);
                visitedSubsets[i] = ContextFactoryRegistry.createSet(rowCount);
            }

            ModifiableSet visitedObjects = ContextFactoryRegistry.createSet(rowCount);
            count = doCalculateGeneratorsBySubsets(0, extent, visitedObjects, attributeSet);

            currentExtents = null;
            visitedSubsets = null;
        }
        return (double) count / MathUtil.powerOfTwo(getRelation().getRowCount());
    }

    private int doCalculateGeneratorsBySubsets(int depth, Set extent, Set visitedObjects, Set attributeSet) {
        if (depth >= maxDepth) {
            return 1;
        }
        ModifiableSet currentExtent = currentExtents[depth];
        currentExtent.copy(extent);
        int count = 1;
        ModifiableSet currentVisitedObjects = visitedSubsets[depth];
        currentVisitedObjects.copy(visitedObjects);
        ModifiableSet workingSet = currentExtent.makeModifiableSetCopy();
        workingSet.andNot(visitedObjects);
        for (int objId = workingSet.firstIn(); objId != Set.NOT_IN_SET; objId = workingSet.nextIn(objId)) {
            currentVisitedObjects.put(objId);
            currentExtent.remove(objId);
            ModifiableSet closureOfCurrentExtent = BinaryRelationUtils.derivationOfObjectsSet(getRelation(), currentExtent);
            if (closureOfCurrentExtent.isEquals(attributeSet)) {
                count += doCalculateGeneratorsBySubsets(depth + 1, currentExtent, currentVisitedObjects, attributeSet);
            } else {
                //do nothing
            }
            currentExtent.put(objId);

        }
        return count;
    }
}
