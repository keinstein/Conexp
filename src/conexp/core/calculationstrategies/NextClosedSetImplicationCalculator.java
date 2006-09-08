/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.calculationstrategies;

import conexp.core.AttributeExplorationCallback;
import conexp.core.ContextFactoryRegistry;
import conexp.core.Implication;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.ImplicationSet;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import util.Assert;


public class NextClosedSetImplicationCalculator extends NextClosedSetAlgorithmBase implements ImplicationCalcStrategy {
    //-----------------------------------------------------
    private int objInImpl;
    private ImplicationSet implSet;
    private ModifiableSet closedObjects;


    //----------------------------------------------------
    public void setImplications(ImplicationSet implSet) {
        this.implSet = implSet;
    }

    private AttributeExplorationCallback attributeExplorationCallback = new AttributeExplorationCallback() {
        public int acceptImplication(Set premise, Set conclusion) {
            return ACCEPT;
        }
    };

    public void setAttributeExplorationCallback(AttributeExplorationCallback attributeExplorationCallback) {
        this.attributeExplorationCallback = attributeExplorationCallback;
    }
    //-----------------------------------------------------

    /**
     * Constructor for the NextClosedSetCalculator object
     */
    public NextClosedSetImplicationCalculator() {
        super();
    }

    //-----------------------------------------------------
    private int acceptImplication(Set premise, Set conclusion) {
        return attributeExplorationCallback.acceptImplication(premise, conclusion);
    }

    //-----------------------------------------------------
    private void addImplication(Set premise, Set conclusion) {
        implSet.addDependency(new Implication(premise, conclusion, objInImpl));
        //*DBG*/ System.out.println("----------------------------");
        //*DBG*/ System.out.println(implSet.size() + "  " + premise + " ==> " + conclusion);
        //*DBG*/ System.out.println("----------------------------");
    }

    //-----------------------------------------------------
    public void calcImplications() {
        Assert.isTrue(implSet != null);
        startCalc();
        doCalcImplBase();
    }

    //-----------------------------------------------------
    private boolean closureImpl(ModifiableSet possiblePseudoIntent, int j, Set notJ) {
        nextClosure.copy(possiblePseudoIntent);
        //nextClosure = old pseudointent
        possiblePseudoIntent.put(j); //next possible pseudointent
        //assuming, that it can be premise of implication
/*
        int bound = implSet.getSize();

        boolean restartCycle = true;
        while (restartCycle) {
            restartCycle = false;
            for (int k = 0; k < bound; k++) {
                // order of growing k is important !!!
                Implication tmp = implSet.getImplication(k);
                if (Set.SUPERSET == possiblePseudoIntent.compare(tmp.getPremise()) && Set.SUPERSET != possiblePseudoIntent.compare(tmp.getConclusion())) {
                    possiblePseudoIntent.or(tmp.getConclusion());
                    restartCycle = true;
                    break;
                }
            }
        }
*/
        implSet.setClosure(possiblePseudoIntent);
        //todo: unify usage of variable nextClosure (use possible pseudointent instead) and
        //possibly extract common part
        nextElementInLecticalOrder.copy(possiblePseudoIntent);
        nextElementInLecticalOrder.andNot(notJ);
        return nextClosure.equals(nextElementInLecticalOrder);
    }

    //-----------------------------------------------------
    private void doCalcImplBase() {
        int numAttr = rel.getColCount();

        nextClosure.clearSet();
        for (; ;) {
            zeroClosureAttr();
            if (attrSet.isEmpty()) {
                break;
            }
            int acceptRes = acceptImplication(nextClosure, attrSet);
            if (acceptRes == AttributeExplorationCallback.STOP) {
                return;
            }
            if (acceptRes == AttributeExplorationCallback.ACCEPT) {
                addImplication(nextClosure, attrSet);
                break;
            }
            Assert.isTrue(acceptRes == AttributeExplorationCallback.REJECT);
        }

        //attrSet - last closed pseudointent
        ModifiableSet nextClosedSet = ContextFactoryRegistry.createSet(numAttr);
        ModifiableSet notJ = ContextFactoryRegistry.createSet(numAttr);
        int j = numAttr - 1;
        while (j >= 0) {
            //that is a not equal g
            j = numAttr - 1;
            notJ.clearSet();
            for (; j >= 0; j--) {
                notJ.put(j);

                if (!attrSet.in(j)) {
                    nextClosedSet.copy(attrSet);
                    nextClosedSet.andNot(notJ);
                    if (closureImpl(nextClosedSet, j, notJ)) {//isPseudoIntentPart1 //
                        //for all Q \subseteq P  Q" \subseteq P should also hold
                        if (!isAttrSetClosed(nextClosedSet)) {
                            nextClosure.andNot(nextClosedSet);
                            int acceptRes = acceptImplication(nextClosedSet, nextClosure);
                            if (acceptRes == AttributeExplorationCallback.STOP) {
                                return;
                            }
                            if (acceptRes == AttributeExplorationCallback.ACCEPT) {
                                addImplication(nextClosedSet, nextClosure);
                            } else {
                                Assert.isTrue(acceptRes == AttributeExplorationCallback.REJECT);
                                break; // restart after rejection of current implication
                            }
                        }
                        attrSet.copy(nextClosedSet);
                        break;
                    }
                }

            }
        }
    }

    //-----------------------------------------------------
    private boolean isAttrSetClosed(Set set) {
        nextClosure.copy(allAttrSet);
        objInImpl = 0;
        for (int i = rel.getRowCount(); --i >= 0;) {
            Set tmp = rel.getSet(i);
            if (set.isSubsetOf(tmp)) {
                nextClosure.and(tmp);
                objInImpl++;
            }
        }
        return nextClosure.equals(set);
    }

    private int getObjectCount() {
        return rel.getRowCount();
    }

    protected int getAttributeCount() {
        return rel.getColCount();
    }

    //-----------------------------------------------------
    protected void zeroClosureAttr() {
        int numObj = getObjectCount();
        attrSet.copy(allAttrSet);
        for (int j = numObj; --j >= 0;) {
            attrSet.and(rel.getSet(j));
        }
        closedObjects.fill();
        objInImpl = getObjectCount();
    }

    protected void startCalc() {
        super.startCalc();
        closedObjects = ContextFactoryRegistry.createSet(getObjectCount());
    }


    public void tearDown() {
        super.tearDown();
        closedObjects = null;
        implSet = null;
    }


}
