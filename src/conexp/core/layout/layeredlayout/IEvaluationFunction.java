/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout;


public interface IEvaluationFunction {
    //the bigger value of evaluation function is better
    double getEvaluationForLattice();
}
