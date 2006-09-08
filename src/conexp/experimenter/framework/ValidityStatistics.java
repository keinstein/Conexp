/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.framework;

import java.io.PrintWriter;



public interface ValidityStatistics {
    /**
     * Insert the method's description here.
     * Creation date: (14.07.01 7:24:06)
     *
     * @param obj java.lang.Object
     * @return boolean
     */
    boolean equals(Object obj);


    /**
     * Insert the method's description here.
     * Creation date: (14.07.01 7:23:27)
     *
     * @param pw java.io.PrintWriter
     */
    void printOn(PrintWriter pw);
}
