/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import util.Assert;
import util.collection.IteratorWrapperBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LatticeElement extends Concept {
    //--------------------------------------------
    public List predessors = new ArrayList();
    public List successors = new ArrayList();


    protected static abstract class EdgeIteratorWrapper extends IteratorWrapperBase implements ConceptIterator {
        protected EdgeIteratorWrapper(Iterator edgeIterator) {
            super(edgeIterator);
        }

        public Object next() {
            return nextConcept();
        }
    }

    private LatticeElementCollection predecessorsNodes = new LatticeElementCollectionBase(predessors) {
        protected LatticeElement doGet(Object collElement) {
            return ((Edge) collElement).getStart();
        }

        protected void removeFromOtherCollection(Object lastObject) {
            doGet(lastObject).successors.remove(lastObject);
        }
    };

    private LatticeElementCollection successorsNodes = new LatticeElementCollectionBase(successors) {
        protected LatticeElement doGet(Object collElement) {
            return ((Edge) collElement).getEnd();
        }

        protected void removeFromOtherCollection(Object lastObject) {
            doGet(lastObject).predessors.remove(lastObject);
        }
    };


    private int height = -1;

//--------------------------------------------

    public LatticeElement(ModifiableSet _obj, ModifiableSet _attr) {
        super(_obj, _attr);
    }
//--------------------------------------------

    public void addPred(LatticeElement pred) {
        Edge edge = new Edge(pred, this);
        predessors.add(edge);
        pred.successors.add(edge);
        height = Math.max(height, pred.height + 1);
    }
//--------------------------------------------

    public void addSucc(LatticeElement succ) {
        Edge edge = new Edge(this, succ);
        successors.add(edge);
        succ.predessors.add(edge);
        succ.height = Math.max(this.height + 1, succ.height);
    }

    public void addParent(LatticeElement parent) {
        addSucc(parent);
    }

    public void addChild(LatticeElement child) {
        addPred(child);
    }

    public void removeParent(LatticeElement parent) {
        removeSucc(parent);
    }

    public void removeSucc(LatticeElement succ) {
        removeFromLatticeElementCollection(getSuccessors(), succ);
    }

    private static void removeFromLatticeElementCollection(LatticeElementCollection latticeElementCollection, LatticeElement toRemove) {
        for (Iterator iterator = latticeElementCollection.iterator(); iterator.hasNext();) {
            LatticeElement element = (LatticeElement) iterator.next();
            if (element == toRemove) {
                iterator.remove();
                break;
            }
        }
    }

    public void removePred(LatticeElement parent) {
        removeFromLatticeElementCollection(getPredecessors(), parent);
    }


    public int degree() {
        return getPredCount() + getSuccCount();
    }
//--------------------------------------------

    public int getHeight() {
        return height;
    }
//-------------------------------------------

    public void setHeight(int h) {
        Assert.isTrue(h >= 0, "Height of lattice element should be greater or equal zero");
        height = h;
    }

    public LatticeElementCollection getPredecessors() {
        return predecessorsNodes;
    }

    public LatticeElementCollection getChildren() {
        return getPredecessors();
    }

    public LatticeElementCollection getSuccessors() {
        return successorsNodes;
    }

    public LatticeElement getPred(int pos) {
        return predecessorsNodes.get(pos);
    }

    public Edge getPredEdge(int pos) {
        return (Edge) predessors.get(pos);
    }


    public int getPredCount() {
        return getPredecessors().getSize();
    }

    public LatticeElement getSucc(int i) {
        return successorsNodes.get(i);
    }

    public int getParentCount() {
        return getSuccCount();
    }

    public LatticeElementCollection getParents() {
        return getSuccessors();
    }

    public int getSuccCount() {
        return successorsNodes.getSize();
    }
//----------------------------------------------

    public boolean isVirtual() {
        return false;
    }
//--------------------------------------------

    public Iterator predessorsEdges() {
        return predessors.iterator();
    }

    public Iterator successorsEdges() {
        return successors.iterator();
    }

    /**
     * @deprecated
     */
//----------------------------------------------
    public void replacePred(Edge pred, Edge newPred) {
        predessors.set(predessors.indexOf(pred), newPred);
    }

    /**
     * @deprecated
     */
//----------------------------------------------
    public void replaceSucc(Edge succ, Edge newSucc) {
        successors.set(successors.indexOf(succ), newSucc);
    }
//--------------------------------------------

    /**
     * not full equals;
     * compares only concepts, and don't compare in and out edge sets
     * for test purposes only
     */
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof LatticeElement)) {
            return true;
        }
        LatticeElement that = (LatticeElement) obj;
        if (isVirtual()) {
            if (!that.isVirtual()) {
                return false;
            }
            return that == this;
        }
        // comparison of predecessors // successors are difficult due to recursive structure
/*
        //commented out for now due to problems of testing edges
        if(this.getPredCount() != that.getPredCount()){
            return false;
        }
        if(this.getSuccCount() != that.getSuccCount()){
            return false;
        }
*/
        return true;
    }

    public static LatticeElement makeFromSetsCopies(Set extent, Set intent) {
        return new LatticeElement(extent.makeModifiableSetCopy(), intent.makeModifiableSetCopy());
    }


    public static LatticeElement makeLatticeElementFromSets(ModifiableSet extent, ModifiableSet intent) {
        return new LatticeElement(extent, intent);
    }


}
