/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core;

import util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConceptsCollection {

    public interface ConceptVisitor {
        public void visitConcept(Concept c);
    }

    public void forEach(ConceptVisitor visitor) {
        final int bound = conceptsCount();
        for (int i = bound; --i >= 0;) {
            visitor.visitConcept(conceptAt(i));
        }
    }

    protected List elements = new ArrayList();

    public ConceptsCollection() {
        super();
    }


//------------------------------------------
    public void addElement(Concept el) {
        Assert.isTrue(el.getIndex() == -1, "Element is already in lattice");
        el.setIndex(elements.size());
        elements.add(el);
    }


//------------------------------------------

    /**
     *  @deprecated
     */
    public void clear() {
        elements.clear();
    }


//------------------------------------------
    public Concept conceptAt(int index) {
        return ((Concept) elements.get(index));
    }

    public int conceptsCount() {
        return elements.size();
    }


//------------------------------------------
    public Iterator elements() {
        return elements.iterator();
    }


    public ItemSet findConceptWithIntent(Set intent) {
        for (int i = conceptsCount(); --i >= 0;) {
            if (conceptAt(i).getAttribs().equals(intent)) {
                return conceptAt(i);
            }
        }
        return null;
    }


//------------------------------------------
    public boolean isEmpty() {
        return elements.isEmpty();
    }


    public Concept makeConcept(Set extent, Set intent) {
        return Concept.makeFromSets(extent, intent);
    }

    public Concept makeConceptFromSetsCopies(Set intent, Set extent) {
        return makeConcept((Set) intent.clone(), (Set) extent.clone());
    }

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context newContext) {
        context = newContext;
    }

    public String toString() {
        return elements.toString();
    }

}
