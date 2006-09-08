/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.relationsequences;

import conexp.core.BinaryRelation;


public abstract class BaseRelationGenerationStrategy extends BaseRelationSequence {


    protected int count;

    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 13:50:59)
     *
     * @param relNo int
     * @return conexp.core.BinaryRelation
     */
    public abstract BinaryRelation makeRelation(int relNo);


    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 13:51:23)
     */
    protected BaseRelationGenerationStrategy(int count) {
        this.count = count;
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 17:58:34)
     */
    protected void createRelations() {
        relations = new BinaryRelation[count];
        for (int i = 0; i < count; i++) {
            relations[i] = makeRelation(i);
        }
    }

    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 18:04:23)
     *
     * @param relNo int
     */
    public int interpolateSize(int relNo, int min, int max) {
        return min + (count > 1 ? (max - min) * relNo / (count - 1) : 0);
    }
}
