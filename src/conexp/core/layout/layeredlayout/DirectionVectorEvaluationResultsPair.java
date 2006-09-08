/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout;

import conexp.core.IPartiallyOrdered;
import util.ArraysUtil;
import util.StringUtil;

import java.util.Arrays;


public class DirectionVectorEvaluationResultsPair implements IPartiallyOrdered {
    double[] directionVectors;
    double[] evaluation;

    public DirectionVectorEvaluationResultsPair() {
    }

    public DirectionVectorEvaluationResultsPair(double[] directionVectors, double[] evaluation) {
        setDirectionVectors(directionVectors);
        setEvaluation(evaluation);
    }

    public double[] getDirectionVectors() {
        return directionVectors;
    }

    public double[] getEvaluation() {
        return evaluation;
    }

    public void setDirectionVectors(double[] directionVectors) {
        this.directionVectors = new double[directionVectors.length];
        System.arraycopy(directionVectors, 0, this.directionVectors, 0, directionVectors.length);
    }

    public void setEvaluation(double[] evaluation) {
        this.evaluation = new double[evaluation.length];
        System.arraycopy(evaluation, 0, this.evaluation, 0, evaluation.length);
    }

    public boolean isLesserThan(IPartiallyOrdered other) {
        if (!(other instanceof DirectionVectorEvaluationResultsPair)) {
            return false;
        }
        DirectionVectorEvaluationResultsPair otherPair = (DirectionVectorEvaluationResultsPair) other;
        double[] otherEvaluation = otherPair.evaluation;
        if (this.evaluation.length != otherEvaluation.length) {
            return false;
        }
        boolean hasBigger = false;
        for (int i = 0; i < evaluation.length; i++) {
            if (evaluation[i] < otherEvaluation[i]) {
                return false;
            }
            if (evaluation[i] > otherEvaluation[i]) {
                hasBigger = true;
            }
        }
        return hasBigger;
    }

    public boolean isEqual(IPartiallyOrdered other) {
        if (!(other instanceof DirectionVectorEvaluationResultsPair)) {
            return false;
        }
        DirectionVectorEvaluationResultsPair otherPair = (DirectionVectorEvaluationResultsPair) other;
        return Arrays.equals(evaluation, otherPair.evaluation);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DirectionVectorEvaluationResultsPair)) {
            return false;
        }

        DirectionVectorEvaluationResultsPair directionVectorEvaluationResultsPair = (DirectionVectorEvaluationResultsPair) obj;

        if (!Arrays.equals(directionVectors, directionVectorEvaluationResultsPair.directionVectors)) {
            return false;
        }
        if (!Arrays.equals(evaluation, directionVectorEvaluationResultsPair.evaluation)) {
            return false;
        }

        return true;
    }


    public int hashCode() {
        if (evaluation == null) {
            return 0;
        }
        return ArraysUtil.arrayHashCode(evaluation);
    }

    public String toString() {
        String DELIMITER = ", ";
        return " directions :" + StringUtil.joinArray(directionVectors, DELIMITER) + " evaluation " + StringUtil.joinArray(evaluation, DELIMITER);
    }
}

