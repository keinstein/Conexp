/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview;

import conexp.core.Context;
import conexp.core.DependencySet;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.ImplicationSet;
import util.Assert;


public class ImplicationBaseCalculator extends AbstractDependencySetCalculator {
    private ImplicationCalcStrategy lastCalc;

    public ImplicationBaseCalculator(Context cxt, ImplicationCalcStrategyFactory[] implicationsCalculatorFactory) {
        super(cxt);
        setImplicationCalcStrategyFactory(implicationsCalculatorFactory);
    }

    public ImplicationBaseCalculator(Context cxt, ImplicationCalcStrategyFactory implicationsCalculatorFactory) {
        this(cxt, new ImplicationCalcStrategyFactory[]{implicationsCalculatorFactory});
    }


    ImplicationCalcStrategyFactory[] factory;

    public void setImplicationCalcStrategyFactory(ImplicationCalcStrategyFactory[] factory) {
        Assert.isTrue(factory.length >= 1);
        this.factory = factory;

    }

    protected DependencySet makeDependencySet() {
        return new ImplicationSet(getContext());
    }

    public ImplicationSet getImplications() {
        return (ImplicationSet) getDependencySet();
    }


    protected void doFindDependencies() {
        int currentFactory = 0;
        boolean success = false;
        while (!success) {
            try {
                lastCalc = factory[currentFactory].makeImplicationCalcStrategy();
                doCalculateImplications(lastCalc);
                success = true;
            } catch (OutOfMemoryError error) {
                currentFactory++;
                if (currentFactory >= factory.length) {
                    throw new OutOfMemoryError("Not enough memory " + error.getMessage());
                }
                getImplications().clear();
                lastCalc = null;
                System.gc();
            }
        }
    }

    public ImplicationCalcStrategy getLastCalc() {
        return lastCalc;
    }

    protected void doCalculateImplications(ImplicationCalcStrategy calc) {
        calc.setRelation(getContext().getRelation());

        ImplicationSet implSet = getImplications();

        implSet.clear();
        calc.setImplications(implSet);
        calc.calcImplications();
    }

}
