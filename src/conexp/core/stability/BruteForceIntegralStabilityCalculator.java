/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.stability;

import conexp.core.BinaryRelation;
import conexp.core.Concept;
import conexp.core.ConceptsCollection;
import conexp.core.Context;
import conexp.core.DefaultBinaryRelationProcessor;
import conexp.core.FCAEngineRegistry;
import conexp.core.ModifiableBinaryRelation;
import conexp.core.Set;
import conexp.core.utils.PowerSetIterator;
import util.DoubleUtil;
import util.MathUtil;
import util.collection.CollectionFactory;

import java.util.Map;



public class BruteForceIntegralStabilityCalculator extends DefaultBinaryRelationProcessor implements IntegralStabilityCalculator {
    private int powerSetSize;
    private Map attributeSetToOccurencesMap;

    public void setRelation(BinaryRelation relation) {
        super.setRelation(relation);
        doCalculateStability();
    }

    private void doCalculateStability() {
        final int rowCount = getRelation().getRowCount();
        PowerSetIterator powerSetIterator = new PowerSetIterator(rowCount);
        attributeSetToOccurencesMap = CollectionFactory.createDefaultMap();
        while (powerSetIterator.hasNext()) {
            Set subset = powerSetIterator.nextSet();
            ModifiableBinaryRelation subrelation = buildSubcontextFromContext(getRelation(), subset);
            Context subcontext = FCAEngineRegistry.makeContext(subrelation);
            ConceptsCollection subcontextLattice = FCAEngineRegistry.buildConceptSet(subcontext);
            subcontextLattice.forEach(new ConceptsCollection.ConceptVisitor() {
                public void visitConcept(Concept c) {
                    Integer occurences = (Integer) attributeSetToOccurencesMap.get(c.getAttribs());
                    int newOccurences = 1;
                    if (null != occurences) {
                        newOccurences = occurences.intValue() + 1;
                    }
                    attributeSetToOccurencesMap.put(c.getAttribs(), new Integer(newOccurences));
                }
            });
        }
        powerSetSize = MathUtil.powerOfTwo(rowCount);
    }

    private static ModifiableBinaryRelation buildSubcontextFromContext(BinaryRelation relation, Set objSubset) {
        ModifiableBinaryRelation ret = relation.makeModifiableCopy();
        for (int objId = objSubset.size(); --objId >= 0;) {
            if (!objSubset.in(objId)) {
                ret.removeRow(objId);
            }
        }
        return ret;
    }


    public double getIntegralStabilityForSet(Set set) {
        Integer occurences = (Integer) attributeSetToOccurencesMap.get(set);
        if (null == occurences) {
            return 0;
        }
        return DoubleUtil.getRate(occurences.intValue(), powerSetSize);
    }
}
