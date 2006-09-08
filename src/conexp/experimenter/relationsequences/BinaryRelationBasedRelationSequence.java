/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.relationsequences;

import conexp.core.BinaryRelation;



public class BinaryRelationBasedRelationSequence extends BaseRelationSequence {

    public BinaryRelationBasedRelationSequence(BinaryRelation rel) {
        relations = new BinaryRelation[1];
        relations[0] = rel;
    }

    public String describeStrategy() {
        return "BinaryRelationSequence";
    }

}
