/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.calculationstrategies;

import conexp.core.*;
import util.Assert;


public class NextClosedSetCalculator extends AbstractConceptCalcStrategy implements ImplicationCalcStrategy, LatticeCalcStrategy {
    //-----------------------------------------------------
    int objInImpl;
    private ModifiableSet attrSet;
    private ModifiableSet closedObjects;
    private ModifiableSet tempAttrSet;
    private ModifiableSet tempAttrSet2;
    private ModifiableSet allAttrSet;


    protected ImplicationSet implSet;

    //----------------------------------------------------
    public void setImplications(ImplicationSet implSet) {
        this.implSet = implSet;
    }

    protected AttributeExplorationCallback attributeExplorationCallback = new AttributeExplorationCallback() {
        public int acceptImplication(Set premise, Set conclusion) {
            return ACCEPT;
        }
    };

    public void setAttributeExplorationCallback(AttributeExplorationCallback attributeExplorationCallback) {
        this.attributeExplorationCallback = attributeExplorationCallback;
    }
    //-----------------------------------------------------
    /**
     *  Constructor for the NextClosedSetCalculator object
     */
    public NextClosedSetCalculator() {
        super();
    }

    //-----------------------------------------------------
    private int acceptImplication(Set premise, Set conclusion) {
        return attributeExplorationCallback.acceptImplication(premise, conclusion);
    }

    //-----------------------------------------------------
    void addImplication(Set premise, Set conclusion) {
        implSet.addDependency(new Implication(premise, conclusion, objInImpl));
        //*DBG*/ System.out.println("----------------------------");
        //*DBG*/ System.out.println(implSet.size() + "  " + premise + " ==> " + conclusion);
        //*DBG*/ System.out.println("----------------------------");
    }

    //-----------------------------------------------------
    public void calcDuquenneGuiguiesSet() {
        util.Assert.isTrue(implSet != null);
        startCalc();
        doCalcImplBase();
    }
/*

    //-----------------------------------------------------
    private boolean closure(ModifiableSet set, int j) {
        boolean ret = true;
        int i = set.length();
        Assert.isTrue(i > 0);
        //calculating dash operator
        i--;
        attrSet.copy(rel.getSet(i));
        i--;
        for (; i >= 0; i--) {
            if (set.in(i)) {
                attrSet.and(rel.getSet(i));
            }
        }
        //calculating reverse dash operator
        int numObj = rel.getRowCount();
        i = 0;
        while (i < numObj) {
            if (attrSet.isSubsetOf(rel.getSet(i))) {
                if (!set.in(i)) {
                    set.put(i);
                    if (i < j) {
                        ret = false;
                    }
                }
            }
            i++;
        }
        return ret;
    }
*/

    //-----------------------------------------------------
    private boolean closureAttr(conexp.core.ModifiableSet set, int j, conexp.core.ModifiableSet notJ) {
        tempAttrSet.copy(allAttrSet);
        set.put(j);
        closedObjects.clearSet();
        for (int i = rel.getRowCount(); --i >= 0;) {
            Set tmp = rel.getSet(i);
            if (set.isSubsetOf(tmp)) {
                tempAttrSet.and(tmp);
                closedObjects.put(i);
            }
        }
        tempAttrSet2.copy(tempAttrSet);
        tempAttrSet2.andNot(notJ);
        set.remove(j);
        return set.equals(tempAttrSet2);
    }

    //-----------------------------------------------------
    private boolean closureImpl(ModifiableSet possiblePseudoIntent, int j, Set notJ) {
        tempAttrSet.copy(possiblePseudoIntent);
        //tempAttrSet = old pseudointent
        possiblePseudoIntent.put(j); //next possible pseudointent
        //assuming, that it can be premise of implication
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
        tempAttrSet2.copy(possiblePseudoIntent);
        tempAttrSet2.andNot(notJ);
        return tempAttrSet.equals(tempAttrSet2);
    }

    //-----------------------------------------------------
    public void doCalcImplBase() {
        int numAttr = rel.getColCount();

        tempAttrSet.clearSet();
        for (; ;) {
            zeroClosureAttr();
            if (attrSet.isEmpty()) {
                break;
            }
            int acceptRes = acceptImplication(tempAttrSet, attrSet);
            if (acceptRes == AttributeExplorationCallback.STOP) {
                return;
            }
            if (acceptRes == AttributeExplorationCallback.ACCEPT) {
                addImplication(tempAttrSet, attrSet);
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
            getNextImplicationLoop:{
                for (; j >= 0; j--) {
                    notJ.put(j);

                    if (!attrSet.in(j)) {
                        nextClosedSet.copy(attrSet);
                        nextClosedSet.andNot(notJ);
                        if (closureImpl(nextClosedSet, j, notJ)) {//isPseudoIntentPart1 //
                            //for all Q \subseteq P  Q" \subseteq P should also hold
                            if (!isAttrSetClosed(nextClosedSet)) {
                                tempAttrSet.andNot(nextClosedSet);
                                int acceptRes = acceptImplication(nextClosedSet, tempAttrSet);
                                if (acceptRes == AttributeExplorationCallback.STOP) {
                                    return;
                                }
                                if (acceptRes == AttributeExplorationCallback.ACCEPT) {
                                    addImplication(nextClosedSet, tempAttrSet);
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
    }

    //-----------------------------------------------------
    public void calculateConceptSet() {
        startCalc();
        //*DBG*/ rel.printDebugData();
        callback.startCalc();
        nextClosedSetAttr();
        callback.finishCalc();
    }

    //-----------------------------------------------------
    private boolean isAttrSetClosed(conexp.core.Set set) {
        tempAttrSet.copy(allAttrSet);
        objInImpl = 0;
        for (int i = rel.getRowCount(); --i >= 0;) {
            Set tmp = rel.getSet(i);
            if (set.isSubsetOf(tmp)) {
                tempAttrSet.and(tmp);
                objInImpl++;
            }
        }
        return tempAttrSet.equals(set);
    }

    /*   //-----------------------------------------------------
       private void nextClosedSet() {
           int numObj = rel.getRowCount();
           conexp.core.ModifiableSet A = zeroClosure();
           callback.addConcept(A, attrSet);

           conexp.core.ModifiableSet b = ContextFactoryRegistry.createSet(numObj);
           conexp.core.ModifiableSet notJ = ContextFactoryRegistry.createSet(numObj);

           int j = numObj - 1;
           while (j >= 0) {
               //that is a not equal g
               j = numObj - 1;
               notJ.clearSet();
               for (; j >= 0; j--) {
                   notJ.put(j);
                   if (!A.in(j)) {
                       //DBG System.out.println("****************************");
                       //DBG System.out.println("Next closed set A="+A);
                       b.copy(A);
                       b.andNot(notJ);
                       b.put(j);
                       boolean res = closure(b, j);
                       //DBG System.out.println("J="+j+" : after closure b "+b);
                       if (res) {
                           callback.addConcept(b, attrSet);
                           A.copy(b);
                           break;
                       }
                   }
               }
           }
       }
   */
    //-----------------------------------------------------
    private void nextClosedSetAttr() {
        final int numAttr = rel.getColCount();
        conexp.core.ModifiableSet b = ContextFactoryRegistry.createSet(numAttr);
        conexp.core.ModifiableSet notJ = ContextFactoryRegistry.createSet(numAttr);
        zeroClosureAttr();
        callback.addConcept(closedObjects, attrSet);
        int j = numAttr - 1;
        while (j >= 0) {
            //that is a not equal g
            j = numAttr - 1;
            notJ.clearSet();
            for (; j >= 0; j--) {
                notJ.put(j);
                if (!attrSet.in(j)) {
                    b.copy(attrSet);
                    b.andNot(notJ);
                    if (closureAttr(b, j, notJ)) {
                        attrSet.copy(tempAttrSet);
                        callback.addConcept(closedObjects, tempAttrSet);
                        break;
                    }
                }
            }
        }
    }
    //-----------------------------------------------------
    /**
     *  Description of the Method
     */
    void startCalc() {
        int col = rel.getColCount();
        closedObjects = ContextFactoryRegistry.createSet(rel.getRowCount());
        attrSet = ContextFactoryRegistry.createSet(col);
        allAttrSet = ContextFactoryRegistry.createSet(col);
        allAttrSet.fillByOne(rel.getColCount());
        tempAttrSet = ContextFactoryRegistry.createSet(col);
        tempAttrSet2 = ContextFactoryRegistry.createSet(col);
    }

/*
    //-----------------------------------------------------
    private ModifiableSet zeroClosure() {
        int numObj = rel.getRowCount();
        conexp.core.ModifiableSet ret = ContextFactoryRegistry.createSet(numObj);
        attrSet.copy(allAttrSet);
        for (int i = 0; i < numObj; i++) {
            if (attrSet.isSubsetOf(rel.getSet(i))) {
                ret.put(i);
            }
        }
        return ret;
    }

*/
    //-----------------------------------------------------
    private void zeroClosureAttr() {
        int numObj = rel.getRowCount();
        objInImpl = numObj;
        closedObjects.fillByOne(numObj);
        attrSet.copy(allAttrSet);
        for (int j = numObj; --j >= 0;) {
            attrSet.and(rel.getSet(j));
        }
    }

    //-----------------------------------------------------
    public void buildLattice() {
        Assert.isTrue(callback instanceof conexp.core.enumcallbacks.NextClosedSetLatticeBuilderCallback);
        calculateConceptSet();
    }

    public void tearDown() {
        super.tearDown();
        implSet = null;
        attrSet = null;
        closedObjects = null;
        tempAttrSet = null;
        tempAttrSet2 = null;
        allAttrSet = null;
    }
}
