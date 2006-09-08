/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.enumcallbacks;

import conexp.core.Set;

public class ConceptNumCallback extends DefaultConceptEnumCallback {
    protected int conCnt;

//-----------------------------------------------------------------

    public int getConceptCount() {
        return conCnt;
    }
//-----------------------------------------------------------------

    public void startCalc() {
        conCnt = 0;
    }

//-----------------------------------------------------------------

    public void addConcept(Set obj, Set attr) {
        conCnt++;
        //*DBG*/ System.out.println("Added conexp j="+j+" ("+obj+"  " +attr+")");
    }
}
