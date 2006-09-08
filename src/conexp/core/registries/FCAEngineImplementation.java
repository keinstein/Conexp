/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.registries;

import conexp.core.ArrowCalculator;
import conexp.core.ConceptEnumCallback;
import conexp.core.ConceptsCollection;
import conexp.core.Context;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.FCAEngine;
import conexp.core.Lattice;
import conexp.core.ModifiableBinaryRelation;
import conexp.core.Set;
import conexp.core.calculationstrategies.DepthSearchArrowCalculator;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import conexp.core.calculationstrategies.DepthSearchCalculatorWithFeatureMask;
import conexp.core.enumcallbacks.ConceptSetCallback;
import conexp.core.searchconstraints.MinSupportConstrainer;
import util.Assert;

public class FCAEngineImplementation implements FCAEngine {
    public ArrowCalculator makeArrowCalculator() {
        return new DepthSearchArrowCalculator();
    }

    public Lattice buildLattice(Context cxt) {
        DepthSearchCalculator calc = new DepthSearchCalculator();
        Lattice result = makeLatticeForContext(cxt);
        calc.setLattice(result);
        calc.setRelation(cxt.getRelation());
        calc.buildLattice();
        cxt.locateElementsConcepts(result);
        result.calcHeight();
        return result;
    }

    public ConceptsCollection buildConceptSet(Context cxt) {
        ConceptsCollection conceptSet = new ConceptsCollection();
        ConceptEnumCallback callback = new ConceptSetCallback(conceptSet);
        DepthSearchCalculator calc = new DepthSearchCalculator();
        calc.setCallback(callback);
        calc.setRelation(cxt.getRelation());
        calc.calculateConceptSet();
        return conceptSet;
    }

    public Lattice buildPartialLattice(Context cxt, Set attributeMask, Set objectsMask) {
        Assert.isTrue(cxt.getAttributeCount() == attributeMask.size());
        DepthSearchCalculatorWithFeatureMask calc = new DepthSearchCalculatorWithFeatureMask();
        calc.setFeatureMasks(attributeMask, objectsMask);

        Lattice result = makeLatticeForContext(cxt);
        calc.setLattice(result);
        calc.setRelation(cxt.getRelation());
        calc.buildLattice();
        cxt.locateElementsConcepts(result, attributeMask, objectsMask);
        result.calcHeight();
        return result;
    }


    public Lattice buildIcebergLattice(Context cxt, int minSupport) {
        DepthSearchCalculatorWithFeatureMask latticeCalc = new DepthSearchCalculatorWithFeatureMask();
        latticeCalc.setSearchConstrainter(new MinSupportConstrainer(minSupport));
        Lattice result = makeLatticeForContext(cxt);
        latticeCalc.setRelation(cxt.getRelation());
        latticeCalc.setLattice(result);
        latticeCalc.buildLattice();
        return result;
    }

    public ConceptsCollection buildIcebergConceptSet(ExtendedContextEditingInterface cxt, int minSupport) {
        ConceptsCollection conceptSet = new ConceptsCollection();
        ConceptEnumCallback callback = new ConceptSetCallback(conceptSet);
        buildIcebergConceptSet(cxt, minSupport, callback);
        return conceptSet;
    }

    public void buildIcebergConceptSet(ExtendedContextEditingInterface cxt, int minSupport, ConceptEnumCallback callback) {
        DepthSearchCalculatorWithFeatureMask latticeCalc = new DepthSearchCalculatorWithFeatureMask();
        latticeCalc.setSearchConstrainter(new MinSupportConstrainer(minSupport));
        latticeCalc.setRelation(cxt.getRelation());
        latticeCalc.setCallback(callback);
        latticeCalc.calculateConceptSet();
    }

    public Lattice makeLatticeForContext(Context cxt) {
        Lattice ret = new Lattice();
        ret.setContext(cxt);
        return ret;
    }

    public Context makeContext(int rows, int columns) {
        return prepareContext(new Context(rows, columns));
    }

    public Context makeContext(ModifiableBinaryRelation relation) {
        return prepareContext(new Context(relation));
    }

    private Context prepareContext(Context ret) {
        ret.setArrowCalculator(makeArrowCalculator());
        return ret;
    }

}
