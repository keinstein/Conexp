/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;


public class AbstractConceptCalcStrategy extends DefaultBinaryRelationProcessor implements ConceptCalcStrategy {

    protected ConceptEnumCallback callback;

    public void calculateConceptSet() {
    }

    public void setCallback(ConceptEnumCallback _callback) {
        this.callback = _callback;
        this.callback.setRelation(rel);
    }

    public void setRelation(BinaryRelation rel) {
        super.setRelation(rel);
        //SMELL to refactor
        if (null != callback) {
            callback.setRelation(rel);
        }
    }

    public void buildLattice() {
    }

    public void tearDown() {
        callback = null;
    }
}
