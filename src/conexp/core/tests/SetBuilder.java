/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.tests;

import conexp.core.*;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import conexp.core.calculationstrategies.LatticeNeedingCalcStrategy;
import conexp.core.calculationstrategies.NextClosedSetCalculator;
import conexp.core.calculationstrategies.ReferenceDepthSearchCalculator;
import conexp.core.enumcallbacks.ConceptSetCallback;
import util.Assert;
import util.collection.CollectionFactory;
import util.testing.UniqueExpectationSet;

import java.util.Collection;
import java.util.List;


public class SetBuilder {
    public static ModifiableBinaryRelation makeRelation(int[][] arrRelation) {
        ModifiableBinaryRelation rel;
        if (arrRelation.length > 0) {
            rel = ContextFactoryRegistry.createRelation(arrRelation.length, arrRelation[0].length);
            for (int i = arrRelation.length; --i >= 0;) {
                rel.getModifiableSet(i).or(makeSet(arrRelation[i]));
            }
        } else {
            rel = ContextFactoryRegistry.createRelation(0, 0);
        }

        return rel;
    }

    public static ModifiableSet makeSet(int[] arSet) {
        ModifiableSet set = ContextFactoryRegistry.createSet(arSet.length);
        for (int i = arSet.length; --i >= 0;) {
            if (arSet[i] != 0) {
                set.put(i);
            }
        }
        return set;
    }

    public static List makeListOfSets(int[][] sets){
        return (List)fillCollection(sets, CollectionFactory.createDefaultList());
    }

    public static java.util.Set makeSetOfSets(int[][] sets){
        return (java.util.Set)fillCollection(sets, CollectionFactory.createDefaultSet());
    }


    private static Collection fillCollection(int[][] sets, Collection ret) {
        for(int i=0;i<sets.length; i++){
            ret.add(makeSet(sets[i]));
        }
        return ret;
    }

    protected static void doMakeLattice(Lattice lat, BinaryRelation rel, LatticeNeedingCalcStrategy strat) {
        strat.setRelation(rel);
        strat.setLattice(lat);
        strat.buildLattice();
        Context cxt = new Context(rel.makeModifiableCopy());
        lat.calcHeight();
        //todo: clean up this
        cxt.locateElementsConcepts(lat);
    }


    public static Implication makeImplication(int[] premise, int[] concslusion, int support) {
        return new Implication(SetBuilder.makeSet(premise), SetBuilder.makeSet(concslusion), support);
    }

    public static Implication makeImplication(int[] premise, int[] concslusion) {
        return makeImplication(premise, concslusion, 0);
    }

    public static Implication makeImplication(int[][] implication){
        return makeImplication(implication[0], implication[1]);
    }

    public static ImplicationSet makeImplicationSet(AttributeInformationSupplier attrInfo, int[][][] implicationDescriptions) {
        ImplicationSet ret = new ImplicationSet(attrInfo);
        for (int i = 0; i < implicationDescriptions.length; i++) {
            ret.addImplication(SetBuilder.makeImplication(implicationDescriptions[i][0], implicationDescriptions[i][1]));
        }
        return ret;
    }

    public static LatticeElement makeConcept(int[] extent, int[] intent) {
        return LatticeElement.makeLatticeElementFromSets(makeSet(extent), makeSet(intent));
    }


    public static conexp.core.ConceptsCollection makeConceptSet(int[][] rel) {
        NextClosedSetCalculator strat = new NextClosedSetCalculator();
        strat.setRelation(SetBuilder.makeRelation(rel));
        conexp.core.ConceptsCollection ret = new conexp.core.ConceptsCollection();
        strat.setCallback(new ConceptSetCallback(ret));
        strat.calculateConceptSet();
        return ret;
    }


    public static Context makeContext(int[][] relation) {
        final Context ret = new Context(makeRelation(relation));
        ret.setArrowCalculator(FCAEngineRegistry.makeArrowCalculator());
        return ret;
    }

    public static ExtendedContextEditingInterface makeContext(String[] objectNames, String[] attrNames, int[][] relation) {
        ExtendedContextEditingInterface ret = makeContext(relation);
        setObjectNames(ret, objectNames);
        setAttributesNames(ret, attrNames);
        return ret;
    }

    public static Context makeContextWithAttributeNames(String[] attrNames, int[][] relation){
        Context ret = makeContext(relation);
        setAttributesNames(ret, attrNames);
        return ret;
    }

    private static void setAttributesNames(ExtendedContextEditingInterface ret, String[] attrNames) {
        Assert.isTrue(ret.getAttributeCount() == attrNames.length);
        for (int i = 0; i < attrNames.length; i++) {
            ret.getAttribute(i).setName(attrNames[i]);
        }
    }

    private static void setObjectNames(ExtendedContextEditingInterface ret, String[] objectNames) {
        Assert.isTrue(ret.getObjectCount() == objectNames.length);
        for (int i = 0; i < objectNames.length; i++) {
            ret.getObject(i).setName(objectNames[i]);
        }
    }


    public static Edge makeEdge(int[][] start, int[][] end) {
        Edge ret = new Edge(makeConcept(start[0], start[1]),
                makeConcept(end[0], end[1]));
        util.Assert.isTrue(ret.isValid());
        return ret;
    }


    public static UniqueExpectationSet makeExpectationSetForIntents(String name, int[][] arrContext) {
        UniqueExpectationSet ret = new UniqueExpectationSet(name);
        for (int i = arrContext.length; --i >= 0;) {
            ret.addExpected(makeSet(arrContext[i]));
        }
        return ret;
    }


    public static Lattice makeLattice(int[][] relation) {
        return makeLattice(relation, new DepthSearchCalculator());
    }


    public static Lattice makeLattice(int[][] relation, LatticeNeedingCalcStrategy strat) {
        BinaryRelation rel = makeRelation(relation);
        return makeLattice(rel, strat);
    }

    public static Lattice makeLattice(BinaryRelation relation) {
        return makeLattice(relation, new ReferenceDepthSearchCalculator());
    }

    public static Lattice makeLattice(BinaryRelation rel, LatticeNeedingCalcStrategy strat) {
        Lattice ret = new Lattice();
        doMakeLattice(ret, rel, strat);
        return ret;
    }

    public static Lattice makeLatticeWithContext(int[][] relation) {
        return makeLatticeWithContext(relation, new DepthSearchCalculator());
    }

    public static Lattice makeLatticeWithContextAndFeatureMask(int[][] relation, int[] featureMaskDescription, int[] featureMaskODescription) {
        return FCAEngineRegistry.buildPartialLattice(makeContext(relation), makeSet(featureMaskDescription),makeSet(featureMaskODescription));
    }

    public static Lattice makeLatticeWithContextAndFeatureMask(Context cxt, int[] featureMaskDescription, int[] featureMaskODescription) {
        return FCAEngineRegistry.buildPartialLattice(cxt, makeSet(featureMaskDescription), makeSet(featureMaskODescription));
    }


    public static Lattice makeIcebergLattice(int[][] relation, int minSupport) {
        return makeIcebergLattice(makeContext(relation), minSupport);
    }

    public static Lattice makeIcebergLattice(Context cxt, int minSupport) {
        return FCAEngineRegistry.buildIcebergLattice(cxt, minSupport);
    }

    public static Lattice makeLatticeWithContext(int[][] relation, LatticeNeedingCalcStrategy strat) {
        Context cxt = makeContext(relation);
        return makeLatticeFromContext(cxt, strat);
    }

    public static LatticeElement findLatticeElementWithIntent(Lattice lat, int[] intent) {
        final LatticeElement elementWithIntent = lat.findElementWithIntent(SetBuilder.makeSet(intent));
        Assert.isTrue(null != elementWithIntent);
        return elementWithIntent;
    }

    public static Lattice makeLattice(Context cxt) {
        return FCAEngineRegistry.buildLattice(cxt);
    }


    public static Lattice makeLatticeFromContext(Context cxt) {
        return makeLatticeFromContext(cxt, new DepthSearchCalculator());
    }

    public static Lattice makeLatticeFromContext(Context cxt, LatticeNeedingCalcStrategy strat) {
        cxt.setArrowCalculator(FCAEngineRegistry.makeArrowCalculator());
        Lattice lattice = FCAEngineRegistry.makeLatticeForContext(cxt);
        doMakeLattice(lattice, cxt.getRelation(), strat);
        cxt.locateElementsConcepts(lattice);
        return lattice;
    }

    public static BinaryRelation makeReduced(ModifiableBinaryRelation rel) {
        Context cxt = new Context(rel);
        cxt.setArrowCalculator(FCAEngineRegistry.makeArrowCalculator());
        cxt.reduceObjects();
        cxt.reduceAttributes();
        return cxt.getRelation();
    }

    public static LatticeElement makeLatticeElement(int[] extent, int[] intent) {
        return LatticeElement.makeLatticeElementFromSets(
                        makeSet(extent),
                        makeSet(intent));
    }

}
