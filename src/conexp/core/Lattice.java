/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core;

import util.Assert;

import java.util.Iterator;

public class Lattice extends ConceptsCollection {
    public interface LatticeElementVisitor {
        void visitNode(LatticeElement node);
    }

    public void forEach(LatticeElementVisitor visitor) {
        final int bound = conceptsCount();
        for (int i = bound; --i >= 0;) {
            visitor.visitNode((LatticeElement) elements.get(i));
        }
    }

    public interface TopSortBlock {
        public void elementAction(LatticeElement curr, LatticeElement lastPred);

        public void assignTopSortNumberToElement(LatticeElement el, int topSortNumber);
    };

    public static class DefaultTopSortBlock implements TopSortBlock {
        public void elementAction(LatticeElement curr, LatticeElement lastPred) {
        }

        public void assignTopSortNumberToElement(LatticeElement el, int topSortNumber) {
        }
    };

    static class CalcHeightTopSortBlock extends DefaultTopSortBlock {
        public void elementAction(LatticeElement curr, LatticeElement lastPred) {
            curr.setHeight(lastPred.getHeight() + 1);
        }
    };

//------------------------------------------
    protected LatticeElement one;
    protected LatticeElement zero;

    private Set featureMask;

    public Set getFeatureMask() {
        return featureMask;
    }

    public void setFeatureMask(Set featureMask) {
        this.featureMask = featureMask;
    }

    //------------------------------------------
    public void clear() {
        super.clear();
        zero = null;
        one = null;
    }


    /**
     * Insert the method's description here.
     * Creation date: (22.05.01 23:45:47)
     */
    public Lattice() {
    }


//------------------------------------------
    public void addElementSetLinks(LatticeElement el) {
        addElement(el);
        Assert.isTrue(zero != null);
        setLinks(zero, el);
    }


//------------------------------------------------
    public void calcHeight() {
        doTopSort(new CalcHeightTopSortBlock());
    }


//------------------------------------------
    public LatticeElement elementAt(int index) {
        return ((LatticeElement) conceptAt(index));
    }


    /**
     * Insert the method's description here.
     * Creation date: (07.03.01 0:28:07)
     * @return conexp.core.Lattice
     * @param intent conexp.core.Set
     * //todo: move fully to set builder
     */
    public LatticeElement findElementWithIntent(Set intent) {
        return (LatticeElement) findConceptWithIntent(intent);
    }


//------------------------------------------
    public LatticeElement findLatticeElementForAttr(int attr) {
        LatticeElement curr = getZero();
        Assert.isTrue(null != curr, "Zero in findElement can't be null");
        boolean find = false;
        while (!find) {
            ConceptIterator enum = curr.successorElements();
            outer:{
                while (enum.hasNext()) {
                    LatticeElement succ = enum.nextConcept();
                    if (succ.getAttribs().in(attr)) {
                        curr = succ;
                        break outer;
                    }
                }//while(enum.hasMoreElements())
                find = true;
            }//outer
        }
        return curr.getAttribs().in(attr) ? curr : null;
    }


//-----------------------------------------------------------------
    /****************************************************************
     *  this function is strongly connected with used order, it should be used
     *  after fully building lattice
     ****************************************************************/
    public LatticeElement findLatticeElementFromOne(Set attribs) {
        LatticeElement curr = getOne();
        Assert.isTrue(null != curr, "One in findElement can't be null");
        //*DBG*/System.out.println("curr="+(BitSet)_curr.attribs);
        //*DBG*/System.out.println("compare="+_attribs+" "+_curr.attribs+"["+_attribs.compare(_curr.attribs)+"]");
        if (null != getFeatureMask()) {
            ModifiableSet maskedAttribs = attribs.makeModifiableSetCopy();
            maskedAttribs.and(getFeatureMask());
            attribs = maskedAttribs;
        }
        boolean find = (Set.EQUAL == attribs.compare(curr.getAttribs()));

        while (!find) {
            ConceptIterator enum = curr.predecessorElements();
            Assert.isTrue(curr.getPredCount() > 0);
            outer:{
                while (enum.hasNext()) {
                    LatticeElement pred = enum.nextConcept();
                    switch (attribs.compare(pred.getAttribs())) {
                        case Set.SUPERSET:
                            curr = pred;
                            break outer;
                        case Set.EQUAL:
                            return pred;
                        case Set.SUBSET:
                            Assert.isTrue(false, "Non closed set in findLatticeElement attribs [" + attribs + "] pred[" + pred.getAttribs() + "]");
                            return pred;
                        case Set.NOT_COMPARABLE:
                            // continue cycle
                            break;
                        default:
                            Assert.isTrue(false, "Shouldn't get here in any case");
                            break;
                    }//switch(_objects.compare(_succ.objects))
                }//while(enum.hasMoreElements())
                return null;
                //todo: set something here
            }//outer
        }
        return curr.getAttribs().equals(attribs) ? curr : null;
    }


//------------------------------------------
    /** This method returns height of lattice, if lattice is builded(one is set)
     and -1 otherwise
     */
    public int getHeight() {
        return null == one ? -1 : one.getHeight();
    }


//------------------------------------------
    public LatticeElement getOne() {
        return one;
    }


//------------------------------------------
    public LatticeElement getZero() {
        return zero;
    }


    /**
     * Insert the method's description here.
     * Creation date: (09.07.01 23:59:07)
     * @return boolean
     * @param other conexp.core.Lattice
     */
    public boolean isEqual(Lattice other) {
        if (other.conceptsCount() != conceptsCount()) {
            return false;
        }
        if (!this.isValid()) {
            if (other.isValid()) {
                return false;
            }
            return true; //two notValid lattices
        }
        if (!other.one.equals(one)) {
            return false;
        }
        if (!other.zero.equals(zero)) {
            return false;
        }
        for (int i = conceptsCount(); --i >= 0;) {
            LatticeElement el = elementAt(i);
            LatticeElement otherEl = other.findElementWithIntent(el.getAttribs());
            if (null == otherEl) {
                return false;
            }
            if (el.predessors.size() != otherEl.predessors.size()) {
                return false;
            }
            if (el.successors.size() != otherEl.successors.size()) {
                return false;
            }
        }
        return true;
    }


    /**
     * Insert the method's description here.
     * Creation date: (02.07.01 18:55:41)
     * @return conexp.core.Concept
     * @param extent conexp.core.Set
     * @param intent conexp.core.Set
     */
    public Concept makeConcept(Set extent, Set intent) {
        return LatticeElement.makeLatticeElementFromSets(extent, intent);
    }

//------------------------------------------

    /** this function adds find all predessors for element toSet
     *   and adds it to their successors list
     */
    private void setLinks(LatticeElement start, LatticeElement toSet) {
        boolean findNext = false;
        //*DBG*/System.out.println("start element index="+start.index);
        Iterator enum = start.successors();
        while (enum.hasNext()) {
            LatticeElement curr = ((Edge) enum.next()).getEnd();
            switch (toSet.compare(curr)) {
                case LatticeElement.GREATER:
                    findNext = true;
                    setLinks(curr, toSet);
                    break;
                case LatticeElement.EQUAL:
                    return;
                case LatticeElement.LESS:
                default:
                    // continue cycle
                    break;
            }
        }
        if (!findNext) {
            start.addSucc(toSet);
        }
    }


//------------------------------------------
    public void setOne(LatticeElement el) {
        Assert.isTrue(el.getIndex() != -1, "Before setting one add to elements");
        one = el;
    }


//------------------------------------------
    public void setZero(LatticeElement el) {
        Assert.isTrue(el.getIndex() != -1, "Before setting zero add to elements");
        zero = el;
        zero.setHeight(0);
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.03.01 0:21:11)
     * @return java.util.ArrayList
     */
    public LatticeElement[] topologicallySortedElements() {
        int size = conceptsCount();
        final LatticeElement[] ret = new LatticeElement[size];
        doTopSort(new DefaultTopSortBlock() {
            public void assignTopSortNumberToElement(LatticeElement el, int topSortNo) {
                ret[topSortNo] = el;
            }
        });
        return ret;
    }


    /**
     * Insert the method's description here.
     * Creation date: (11.08.01 20:13:43)
     */
    public void doTopSort(TopSortBlock block) {
        final int conceptsCount = conceptsCount();
        int[] workArray = new int[conceptsCount];
        for (int i = workArray.length; --i >= 0;) {
            workArray[i] = elementAt(i).getPredCount();
        }

        LatticeElement tmp = getZero();
        int queueEnd = tmp.getIndex();
        int currNo = 0;

        while (tmp != one) {
            Iterator succ = tmp.successors();
            block.assignTopSortNumberToElement(tmp, currNo++);
            while (succ.hasNext()) {
                LatticeElement tmp2 = ((Edge) succ.next()).getEnd();
                if (0 == --workArray[tmp2.getIndex()]) {
                    workArray[elementAt(queueEnd).getIndex()] = tmp2.getIndex();
                    queueEnd = tmp2.getIndex();
                    block.elementAction(tmp2, tmp);
                }
            }
            tmp = elementAt(workArray[tmp.getIndex()]);
        }
        block.assignTopSortNumberToElement(one, currNo);
    }


    /**
     * Insert the method's description here.
     * Creation date: (12.07.01 12:49:04)
     * @return int
     */
    public int edgeCount() {
        int sum = 0;
        for (int i = conceptsCount(); --i >= 0;) {
            sum += elementAt(i).getPredCount();
        }
        return sum;
    }


    /**
     * Insert the method's description here.
     * Creation date: (11.08.01 21:24:33)
     * @return boolean
     */
    public boolean isValid() {
        if (0 == conceptsCount()) {
            return false;
        }
        if (null == one) {
            return false;
        }
        if (null == zero) {
            return false;
        }
        if (zero.getHeight() != 0) {
            return false;
        }
        if (getHeight() < 0) {
            return false;
        }
        if (getHeight() == 0) {
            if (zero != one) {
                return false;
            }
            if (conceptsCount() != 1) {
                return false;
            }
        }
        return true;
    }
}
