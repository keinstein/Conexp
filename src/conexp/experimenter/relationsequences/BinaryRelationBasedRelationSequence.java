package conexp.experimenter.relationsequences;

import conexp.core.BinaryRelation;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 26/8/2003
 * Time: 16:47:36
 */

public class BinaryRelationBasedRelationSequence extends BaseRelationSequence {

    public BinaryRelationBasedRelationSequence(BinaryRelation rel) {
        relations = new BinaryRelation[1];
        relations[0]=rel;
    }

    public String describeStrategy() {
        return "BinaryRelationSequence";
    }

}
