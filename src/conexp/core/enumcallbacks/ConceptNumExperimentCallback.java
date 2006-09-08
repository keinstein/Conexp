/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.enumcallbacks;

import conexp.core.Set;
import util.Stopwatch;

public class ConceptNumExperimentCallback extends ConceptNumCallback {
    Stopwatch stopwatch;

    public void startCalc() {
        stopwatch = new Stopwatch();
    }

//---------------------------------------

    public void addConcept(Set obj, Set attr) {
        conCnt++;
        if (conCnt % 100000 == 0) {
            System.err.print("+");
        }
        if (conCnt % 1000000 == 0) {
            System.err.println("\n|" + conCnt + " delay " + stopwatch.getDelayTime());
        }
    }
}
