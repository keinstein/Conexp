/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import util.Assert;
import util.collection.CollectionFactory;

import java.util.Iterator;
import java.util.List;

public class ConceptsCollection {

    public interface ConceptVisitor {
        void visitConcept(Concept c);
    }

    public void forEach(ConceptVisitor visitor) {
        final int bound = conceptsCount();
        for (int i = bound; --i >= 0;) {
            visitor.visitConcept(conceptAt(i));
        }
    }

    protected List elements = CollectionFactory.createDefaultList();

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

    public void clear() {
        elements.clear();
    }

//------------------------------------------

    public Concept conceptAt(int index) {
        return (Concept) elements.get(index);
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


    public Concept makeConcept(ModifiableSet extent, ModifiableSet intent) {
        return Concept.makeFromSets(extent, intent);
    }

    public Concept makeConceptFromSetsCopies(Set intent, Set extent) {
        return makeConcept(intent.makeModifiableSetCopy(), extent.makeModifiableSetCopy());
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

    public boolean equalsAsSets(ConceptsCollection other) {
        if (this.conceptsCount() != other.conceptsCount()) {
            return false;
        }
        return toSet(this.elements).equals(toSet(other.elements));
    }

    private static java.util.Set toSet(List elements) {
        return CollectionFactory.createDefaultSet(elements);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConceptsCollection)) {
            return false;
        }

        final ConceptsCollection conceptsCollection = (ConceptsCollection) obj;

        if (context != null ? !context.equals(conceptsCollection.context) : conceptsCollection.context != null) {
            return false;
        }
        //todo: think, maybe set comparison is more appropriate
        if (!elements.equals(conceptsCollection.elements)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = elements.hashCode();
        result = 29 * result + (context != null ? context.hashCode() : 0);
        return result;
    }

}
