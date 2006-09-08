/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public class DefaultBinaryRelationProcessor implements BinaryRelationProcessor {
    protected BinaryRelation rel;

    public void setRelation(BinaryRelation relation) {
        this.rel = relation;
    }

    protected BinaryRelation getRelation() {
        return rel;
    }

    public void tearDown() {
        rel = null;
    }
}
