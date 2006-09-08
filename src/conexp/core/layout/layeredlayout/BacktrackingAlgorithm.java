/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout;

import util.NumericInterval;


public class BacktrackingAlgorithm {
    NumericInterval range;
    double step;

    public void setRange(double lowBorder, double highBorder) {
        range = new NumericInterval(lowBorder, highBorder);
    }

    public void setStep(double step) {
        this.step = step;
    }

    public void firstPoint(double[] solutionVector) {
        for (int i = 0; i < solutionVector.length; i++) {
            solutionVector[i] = range.getStart();
        }
    }

    public boolean hasMorePoints(double[] solutionVector) {
        for (int i = solutionVector.length; --i >= 0;) {
            double currentComponent = solutionVector[i];
            double nextComponent = currentComponent + step;
            if (range.includes(nextComponent)) {
                return true;
            }
        }
        return false;
    }

    public void nextPoint(double[] solutionVector) {
        for (int i = solutionVector.length; --i >= 0;) {
            double currentComponent = solutionVector[i];
            double nextComponent = currentComponent + step;
            if (range.includes(nextComponent)) {
                solutionVector[i] = nextComponent;
                return;
            } else {
                solutionVector[i] = range.getStart();
            }
        }
    }
}
