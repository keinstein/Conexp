/*
 * User: Serhiy Yevtushenko
 * Date: Jun 10, 2002
 * Time: 9:49:29 PM
 */
package conexp.frontend.latticeeditor.queries;

import conexp.core.ExtendedContextEditingInterface;
import conexp.core.LatticeElement;
import conexp.core.Set;

import java.util.Iterator;

public class ConceptNodeQuery extends QueryBase {
    LatticeElement concept;
    boolean innermost;

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

    public boolean isInnermost() {
        return innermost;
    }
}
