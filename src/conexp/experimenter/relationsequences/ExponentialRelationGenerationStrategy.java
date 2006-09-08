/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.relationsequences;

import conexp.core.BinaryRelation;



public class ExponentialRelationGenerationStrategy extends BaseRelationGenerationStrategy {
    protected int minSize;
    protected int maxSize;

    /**
     * ExponentialRelationGenerationStrategy constructor comment.
     *
     * @param count int
     */
    public ExponentialRelationGenerationStrategy(int minSize, int maxSize, int count) {
        super(count);
        this.minSize = minSize;
        this.maxSize = maxSize;
        createRelations();
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 18:20:28)
     *
     * @return java.lang.String
     */
    public String describeStrategy() {
        return "Exponential;MinSize;" + minSize + ";MaxSize;" + maxSize + ';';
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 18:02:47)
     *
     * @param relNo int
     * @return conexp.core.BinaryRelation
     */
    public BinaryRelation makeRelation(int relNo) {
        return RelationGenerator.makeExponential(interpolateSize(relNo, minSize, maxSize));
    }
}
