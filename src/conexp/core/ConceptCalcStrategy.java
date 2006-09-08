/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;


public interface ConceptCalcStrategy extends BinaryRelationProcessor {
    void setCallback(ConceptEnumCallback _callback);

    void calculateConceptSet();
}
