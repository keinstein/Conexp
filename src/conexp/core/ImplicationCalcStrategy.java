/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core;


public interface ImplicationCalcStrategy extends BinaryRelationProcessor {
    void setImplications(ImplicationSet implSet);

    void calcImplications();
}
