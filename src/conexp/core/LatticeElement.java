package conexp.core;

import util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LatticeElement extends Concept {
    //--------------------------------------------
    public List predessors = new ArrayList();
    public List successors = new ArrayList();


    private int height = -1;

//--------------------------------------------
    public LatticeElement(Set _obj, Set _attr) {
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

    public int degree() {
        return getPredCount() + getSuccCount();
    }
//--------------------------------------------
    public int getHeight() {
        return height;
    }

    static abstract class EdgeIteratorWrapper implements ConceptIterator{

        protected Iterator edgeIterator;

        EdgeIteratorWrapper(Iterator edgeIterator){
            this.edgeIterator = edgeIterator;
        }

        public Object next() {
            return nextConcept();
        }

        public boolean hasNext() {
            return edgeIterator.hasNext();
        }

        public void remove() {
            edgeIterator.remove();
        }


    }

    public ConceptIterator predecessorElements(){
        return new EdgeIteratorWrapper(predessors()){
            public LatticeElement nextConcept() {
                return ((Edge)edgeIterator.next()).getStart();
            }
        };
    }

    public ConceptIterator successorElements(){
        return new EdgeIteratorWrapper(successors()){
            public LatticeElement nextConcept() {
                return ((Edge)edgeIterator.next()).getEnd();
            }
        };
    }

    public LatticeElement getPred(int pos) {
        return ((Edge) predessors.get(pos)).getStart();
    }

    public int getPredCount() {
        return predessors.size();
    }

    public LatticeElement getSucc(int i) {
        return ((Edge) successors.get(i)).getEnd();
    }

    public int getSuccCount() {
        return successors.size();
    }
//----------------------------------------------
    public boolean isVirtual() {
        return false;
    }
//--------------------------------------------
    public Iterator predessors() {
        return predessors.iterator();
    }
    /**
     @deprecated
     */
//----------------------------------------------
    public void replacePred(Edge pred, Edge newPred) {
        predessors.set(predessors.indexOf(pred), newPred);
    }
    /**
     @deprecated
     */
//----------------------------------------------
    public void replaceSucc(Edge succ, Edge newSucc) {
        successors.set(successors.indexOf(succ), newSucc);
    }
//-------------------------------------------
    public void setHeight(int h) {
        Assert.isTrue(h >= 0, "Height of lattice element should be greater or equal zero");
        height = h;
    }
//--------------------------------------------
    public Iterator successors() {
        return successors.iterator();
    }

    /**
     * not full equals;
     * compares only concepts, and don't compare in and out edge sets
     * for test purposes only
     */
    public boolean equals(Object obj) {
        if (!super.equals(obj)){
            return false;
        }
        if(!(obj instanceof LatticeElement)){
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
        return new LatticeElement((Set) extent.clone(), (Set) intent.clone());
    }


    public static LatticeElement makeLatticeElementFromSets(Set extent, Set intent) {
        return new LatticeElement(extent, intent);
    }
}