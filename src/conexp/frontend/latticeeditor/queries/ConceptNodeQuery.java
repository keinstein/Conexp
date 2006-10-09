/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.queries;

import conexp.core.ExtendedContextEditingInterface;
import conexp.core.LatticeElement;
import conexp.core.Set;
import conexp.core.Lattice;

import java.util.Iterator;

public class ConceptNodeQuery extends QueryBase {
    LatticeElement concept;
    boolean innermost;

    public static ConceptNodeQuery createNodeQuery(Lattice lattice, LatticeElement concept){
        return new ConceptNodeQuery(lattice.getContext(), concept, lattice.getAttributesMask());
    }

    public ConceptNodeQuery(ExtendedContextEditingInterface cxt, LatticeElement concept, Set attributeMask) {
        this(cxt, concept, true, attributeMask);
    }

    public ConceptNodeQuery(ExtendedContextEditingInterface cxt, LatticeElement concept, boolean isInnermost, Set attributeMask) {
        super(cxt, attributeMask);
        this.concept = concept;
        this.innermost = isInnermost;
    }


    public LatticeElement getConcept() {
        return concept;
    }

    public Set getQueryIntent() {
        return concept.getAttribs();
    }

    public boolean hasOwnObjects() {
        return concept.hasOwnObjects();
    }

    public int getOwnAttribsCount() {
        return concept.getOwnAttrCnt();
    }

    public boolean hasOwnAttribs() {
        return concept.hasOwnAttribs();
    }

    public Iterator ownAttribsIterator() {
        return concept.ownAttribsIterator();
    }

    public int getExtentSize() {
        return concept.getObjCnt();
    }

    public int getOwnObjectsCount() {
        return concept.getOwnObjCnt();
    }

    public Iterator ownObjectsIterator() {
        return concept.ownObjectsIterator();
    }

    public Iterator extentIterator() {
        return concept.extentIterator(getContext());
    }

    public Iterator intentIterator() {
        return concept.intentIterator(getContext());
    }

    public boolean isInnermost() {
        return innermost;
    }
}
