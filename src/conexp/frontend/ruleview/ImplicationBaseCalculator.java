/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.ruleview;

import conexp.core.Context;
import conexp.core.DependencySet;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.ImplicationSet;
import conexp.core.calculationstrategies.NextClosedSetCalculator;


public class ImplicationBaseCalculator extends AbstractDependencySetCalculator {
    public ImplicationBaseCalculator(Context cxt) {
        super(cxt);
    }

    protected DependencySet makeDependencySet() {
        return new ImplicationSet(getContext());
    }

    public ImplicationSet getImplications() {
        return (ImplicationSet) getDependencySet();
    }


    protected void doFindDependencies() {
        ImplicationCalcStrategy implCalculator = new NextClosedSetCalculator();
        doCalculateImplications(implCalculator);
    }

    protected void doCalculateImplications(ImplicationCalcStrategy calc) {
        calc.setRelation(getContext().getRelation());

        ImplicationSet implSet = getImplications();

        implSet.clear();
        calc.setImplications(implSet);
        calc.calcDuquenneGuiguiesSet();
    }
}
