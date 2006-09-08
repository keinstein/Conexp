/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies;

import conexp.core.BinaryRelation;
import conexp.core.ContextFactoryRegistry;
import conexp.core.Implication;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.ImplicationSet;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import util.Assert;
import util.Pair;
import util.collection.CollectionFactory;

import java.util.LinkedList;

public class LatticeImplicationCalculator implements ImplicationCalcStrategy {
    private ImplicationSet implications;
    private LinkedList queue;
    private int[] successorCounts;
    private int attrCount;

    private int cacheHits = 0;
    private int callsToFindFromInteraction = 0;
    private int returnsDueNotUnion = 0;

    public int getReturnsDueNotUnion() {
        return returnsDueNotUnion;
    }


    public int getCallsToFindFromInteraction() {
        return callsToFindFromInteraction;
    }

    public int getCacheHits() {
        return cacheHits;
    }


    public void setImplications(ImplicationSet implSet) {
        this.implications = implSet;
    }

    private Lattice lattice;

    public void setLattice(Lattice lat) {
        this.lattice = lat;
        attrCount = lat.getOne().getAttribs().size();
        tempPremise = makeNewSet();
    }


    public void calcImplications() {
        Assert.isTrue(lattice != null, "Lattice should be set for using lattice implications calculator");
        LatticeElement one = lattice.getOne();
        successorCounts = new int[lattice.conceptsCount()];
        for (int i = 0; i < successorCounts.length; i++) {
            successorCounts[i] = lattice.elementAt(i).getSuccCount();
        }

        if (!one.getAttribs().isEmpty()) {
            Set empty = makeNewSet();
            final Implication dep = new Implication(empty, one.getAttribs().makeModifiableSetCopy(), one.getObjCnt());
            implications.addImplication(dep);
        }

        queue = new LinkedList();
        addDownElementsWithAllVisitedParentsToQueue(one);

        while (!queue.isEmpty()) {
            LatticeElement currConcept = (LatticeElement) queue.removeFirst();
            findImplicationFromJoinOfParents(currConcept);
            findImplicationFromProperAttributes(currConcept);
            addDownElementsWithAllVisitedParentsToQueue(currConcept);
        }
    }

    private void findImplicationFromProperAttributes(LatticeElement currConcept) {
        ModifiableSet attributeContingent = currConcept.getAttribs().makeModifiableSetCopy();
        for (int j = 0; j < currConcept.getSuccCount(); j++) {
            attributeContingent.andNot(currConcept.getSucc(j).getAttribs());
        }

        if (!attributeContingent.isEmpty()) {
            //conexp has own attributes, so, the reason for implications in the presence of
            //own attributes
            for (int i = attributeContingent.firstIn(); i != -1; i = attributeContingent.nextIn(i)) {
                tempPremise.clearSet();
                tempPremise.put(i);

                implications.setClosure(tempPremise);
                addImplicationFromConceptWithPremise(tempPremise, currConcept);

            }
        }
    }

    private java.util.Set exploredChains = CollectionFactory.createDefaultSet();

    private void findImplicationFromJoinOfParents(LatticeElement currConcept) {
        //finding implications, that appears from interplay of parents
        int startImplicationCount = implications.getSize();
        Assert.isTrue(exploredChains.isEmpty());

        int succCount = currConcept.getSuccCount();
        for (int i = 0; i < succCount; i++) {
            LatticeElement firstParent = currConcept.getSucc(i);
            for (int j = i + 1; j < succCount; j++) {
                LatticeElement secondParent = currConcept.getSucc(j);
                findImplicationsFromInteractionOfTwoParents(currConcept, firstParent, secondParent);
            }
        }
        exploredChains.clear();

        int endImplicationCount = implications.getSize();

        //assumptions, on which reducing is based:
        // 1) every next rule can't be derived from previous ones
        // 2) we are reducing only rules, that corresponds to one conexp
        for (int i = endImplicationCount; --i >= startImplicationCount;) {
            Implication curr = implications.getImplication(i);
            Set currPremise = curr.getPremise();

            for (int j = i; --j >= startImplicationCount;) {

                Implication other = implications.getImplication(j);
                if (currPremise.isSubsetOf(other.getPremise())) {
                    implications.removeDependency(j);
                    --i; //(next i should be smaller due to removal)
                }
            }
        }
    }


    private ModifiableSet tempPremise;

    private void findImplicationsFromInteractionOfTwoParents(LatticeElement currConcept, LatticeElement firstParent, LatticeElement secondParent) {
        callsToFindFromInteraction++;
        if (!exploredChains.add(new Pair(firstParent, secondParent))) {
            cacheHits++;
            return;
        }
        if (!isUnionEqualsTo(firstParent, secondParent, currConcept)) {
            returnsDueNotUnion++;
            return;
        }
        final int firstSuccCount = firstParent.getSuccCount();
        for (int i = 0; i < firstSuccCount; i++) {
            LatticeElement parentSuccessor = firstParent.getSucc(i);
            findImplicationsFromInteractionOfTwoParents(currConcept, parentSuccessor, secondParent);
        }

        final int secCount = secondParent.getSuccCount();
        for (int i = 0; i < secCount; i++) {
            LatticeElement parentSuccessor = secondParent.getSucc(i);
            findImplicationsFromInteractionOfTwoParents(currConcept, firstParent, parentSuccessor);
        }

        tempPremise.copy(firstParent.getAttribs());
        // ModifiableSet premise = firstParent.getAttribs().makeModifiableSetCopy();
        // premise.or(secondParent.getAttribs());
        tempPremise.or(secondParent.getAttribs());
        implications.setClosure(tempPremise);

        //in order, that this parents were really creating implication, the following condition
        //is also required:
        //the union of this two elements should be equal to currConcept
        //when this condition can be violated
        // when union of two elements of lattice, that have meeting upstairs,
        // would not be equal to m

        addImplicationFromConceptWithPremise(tempPremise, currConcept);
    }

    private void addImplicationFromConceptWithPremise(ModifiableSet premise, LatticeElement currConcept) {
        final Set attribs = currConcept.getAttribs();
        if (!premise.isEquals(attribs)) {
            ModifiableSet conclusion = attribs.makeModifiableSetCopy();
            conclusion.andNot(premise);
            Implication dep = new Implication(premise.makeModifiableSetCopy(),
                    conclusion, currConcept.getObjCnt());
/*
            System.out.println("Adding implication from parents "+firstParent.getAttribs()+" : "+secondParent.getAttribs());
            System.out.println(dep);
*/
            implications.addImplication(dep);
        }
    }

    public boolean isUnionEqualsTo(LatticeElement firstParent, LatticeElement secondParent, LatticeElement currConcept) {
        tempPremise.copy(firstParent.getAttribs());
        tempPremise.or(secondParent.getAttribs());
        for (int i = 0; i < currConcept.getSuccCount(); i++) {
            if (tempPremise.isSubsetOf(currConcept.getSucc(i).getAttribs())) {
                return false;
            }
        }
        return true;
    }

    private ModifiableSet makeNewSet() {
        return ContextFactoryRegistry.createSet(attrCount);
    }

    private void addDownElementsWithAllVisitedParentsToQueue(LatticeElement current) {
        for (int i = 0; i < current.getPredCount(); i++) {
            LatticeElement pred = current.getPred(i);
            if (--successorCounts[pred.getIndex()] == 0) {
                queue.addLast(pred);
            }
        }
    }

    public void setRelation(BinaryRelation relation) {
        //intentionally left blank, don't work with relation
        //this calculator works with lattice
    }

    public void tearDown() {
        implications = null;
    }
}
