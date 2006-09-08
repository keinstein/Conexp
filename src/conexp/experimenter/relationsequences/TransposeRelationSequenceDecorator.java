/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.relationsequences;

import conexp.core.BinaryRelation;
import conexp.core.BinaryRelationUtils;
import conexp.experimenter.framework.RelationSequence;


public class TransposeRelationSequenceDecorator extends BaseRelationGenerationStrategy {
    protected RelationSequence sourceSequence;

    /**
     * Insert the method's description here.
     * Creation date: (28.07.01 23:50:39)
     *
     * @param sourceSequence conexp.core.experimenter.framework.RelationSequence
     */
    public TransposeRelationSequenceDecorator(RelationSequence sourceSequence) {
        super(sourceSequence.getRelationCount());
        this.sourceSequence = sourceSequence;
        createRelations();
    }


    /**
     * Insert the method's description here.
     * Creation date: (28.07.01 23:47:21)
     *
     * @return java.lang.String
     */
    public String describeStrategy() {
        return "Transposed " + sourceSequence.describeStrategy();
    }


    /**
     * Insert the method's description here.
     * Creation date: (28.07.01 23:49:49)
     *
     * @param relNo int
     * @return conexp.core.BinaryRelation
     */
    public BinaryRelation makeRelation(int relNo) {
        return BinaryRelationUtils.makeTransposedRelation(sourceSequence.getRelation(relNo));
    }
}
