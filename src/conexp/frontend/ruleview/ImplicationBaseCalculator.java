/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.ruleview;

import conexp.core.Context;
import conexp.core.DependencySet;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.ImplicationSet;


public class ImplicationBaseCalculator extends AbstractDependencySetCalculator {
    private ImplicationCalcStrategy lastCalc;

    public ImplicationBaseCalculator(Context cxt, ImplicationCalcStrategyFactory implicationsCalculatorFactory) {
        super(cxt);
        setImplicationCalcStrategyFactory(implicationsCalculatorFactory);
    }

    ImplicationCalcStrategyFactory factory;

    public void setImplicationCalcStrategyFactory(ImplicationCalcStrategyFactory factory) {
        this.factory = factory;
    }

    protected DependencySet makeDependencySet() {
        return new ImplicationSet(getContext());
    }

    public ImplicationSet getImplications() {
        return (ImplicationSet) getDependencySet();
    }


    protected void doFindDependencies() {
        lastCalc = makeImplicationCalcStrategy();
        doCalculateImplications(lastCalc);
    }

    public ImplicationCalcStrategy getLastCalc() {
        return lastCalc;
    }

    private ImplicationCalcStrategy makeImplicationCalcStrategy() {
        return factory.makeImplicationCalcStrategy();
    }

    protected void doCalculateImplications(ImplicationCalcStrategy calc) {
        calc.setRelation(getContext().getRelation());

        ImplicationSet implSet = getImplications();

        implSet.clear();
        calc.setImplications(implSet);
        calc.calcImplications();
    }

}
