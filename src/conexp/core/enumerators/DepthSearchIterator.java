/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.enumerators;

import conexp.core.ConceptIterator;
import conexp.core.ContextFactoryRegistry;
import conexp.core.LatticeElement;
import conexp.core.ModifiableSet;
import conexp.core.Set;

import java.util.ArrayList;
import java.util.List;


public abstract class DepthSearchIterator implements ConceptIterator {

    static class PosInfo {
        LatticeElement curr;
        ModifiableSet mask;
        int pos;

        public PosInfo(int setSize) {
            mask = ContextFactoryRegistry.createSet(setSize);
        }
    }

    protected List newStack;
    int setSize;

    protected int depth;
    protected ModifiableSet temp;
    protected LatticeElement next;

    /**
     * Insert the method's description here.
     * Creation date: (06.03.01 1:36:31)
     *
     * @return boolean
     */
    public boolean hasNext() {
        return null != next;
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.03.01 23:48:09)
     */
    protected void initStackVariables(int newDepth, LatticeElement child, int childCount, Set mask) {
        if (newDepth == newStack.size()) {
            newStack.add(new PosInfo(setSize));
        }
        PosInfo info = getStackElement(newDepth);
        info.mask.copy(mask);
        info.curr = child;
        info.pos = childCount;
    }

    /**
     * Insert the method's description here.
     * Creation date: (13.03.01 22:55:41)
     */
    protected void initDepthIterator(LatticeElement start, int startPos) {
        setSize = start.getAttribs().size();
        newStack = new ArrayList();
        temp = ContextFactoryRegistry.createSet(setSize);
        depth = 0;
        initStackVariables(depth, start, startPos, temp); //temp is empty here
        next = start;
    }

    protected DepthSearchIterator.PosInfo getTopElementOfStack() {
        return getStackElement(this.depth);
    }

    private PosInfo getStackElement(final int pos) {
        return (PosInfo) newStack.get(pos);
    }

    /**
     * Insert the method's description here.
     * Creation date: (06.03.01 23:41:44)
     *
     * @return conexp.core.LatticeElement
     */
    protected abstract LatticeElement findNextConcept();


    /**
     * Insert the method's description here.
     * Creation date: (06.03.01 1:36:31)
     *
     * @return conexp.core.LatticeElement
     */
    public LatticeElement nextConcept() {
        LatticeElement ret = next;
        next = findNextConcept();
        return ret;
    }

    public Object next() {
        return nextConcept();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
