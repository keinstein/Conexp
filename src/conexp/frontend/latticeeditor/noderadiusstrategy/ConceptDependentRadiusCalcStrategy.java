/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.noderadiusstrategy;

import conexp.core.ConceptsCollection;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;
import util.Assert;

public abstract class ConceptDependentRadiusCalcStrategy extends AbstractNodeRadiusCalcStrategy {
    private int deltaRadius;
    private double maximalValue;

    public ConceptDependentRadiusCalcStrategy(DrawParameters opt) {
        super(opt);
    }

    /**
     * calcNodeRadius method comment.
     */
    public int calcNodeRadius(ConceptQuery query) {
        return calculateRadius(calculateRatio(query));
    }

    /**
     * Todo:sye - change back to protected after restructuring
     */
    public abstract double calculateRatio(ConceptQuery query);

    private int calculateRadius(double ratio) {
        return options.getMinNodeRadius() + Math.round((float) Math.sqrt(ratio) * deltaRadius);
    }

    public void initCalc() {
        ConceptsCollection lattice = getConceptSet();
        if (null != lattice) {
            deltaRadius = options.getMaxNodeRadius() - options.getMinNodeRadius();
            maximalValue = calculateMaximalValue(lattice);
            Assert.isTrue(getMaximalValue() >= 0);
        }
    }

    protected abstract double calculateMaximalValue(ConceptsCollection conceptSet);

    protected double getMaximalValue() {
        return maximalValue;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConceptDependentRadiusCalcStrategy)) {
            return false;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        final ConceptDependentRadiusCalcStrategy conceptDependentRadiusCalcStrategy = (ConceptDependentRadiusCalcStrategy) obj;
        if (deltaRadius != conceptDependentRadiusCalcStrategy.deltaRadius) {
            return false;
        }
        if (maximalValue != conceptDependentRadiusCalcStrategy.maximalValue) {
            return false;
        }
        return super.equals(obj);
    }

    public int hashCode() {
        int result;
        long temp;
        result = deltaRadius;
        temp = maximalValue != +0.0d ? Double.doubleToLongBits(maximalValue) : 0l;
        result = 29 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
