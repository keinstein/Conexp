/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import util.Assert;
import util.collection.CollectionFactory;
import util.collection.NullIterator;

import java.util.Collection;
import java.util.Iterator;


public class Concept implements ItemSet {

    private ModifiableSet attribs;
    private ModifiableSet objects;

    /**
     * identifier in lattice; if >= 0 then element is in lattice *
     */
    private int index = -1;


    private static final int HAS_ATTRIBS = 0x2;

    private static final int HAS_OBJECTS = 0x1;


    private Collection ownAttribs;


    private Collection ownObjects;

    /**
     * descriptor of conexp type *
     */
    private int conceptType = 0;

    /**
     * Insert the method's description here.
     * Creation date: (02.07.01 18:49:42)
     *
     * @param extent conexp.core.Set
     * @param intent conexp.core.Set
     */
    public Concept(ModifiableSet extent, ModifiableSet intent) {
        Assert.isTrue(null != extent);
        Assert.isTrue(null != intent);
        objects = extent;
        attribs = intent;
    }


    /**
     * Insert the method's description here.
     * Creation date: (22.12.00 1:25:08)
     *
     * @param obj conexp.core.ContextEntity
     */
    public void addOwnAttrib(ContextEntity obj) {
        if (null == ownAttribs) {
            ownAttribs = CollectionFactory.createDefaultList();
        }
        ownAttribs.add(obj);
        incrOwnAttrCnt();
    }


    /**
     * Insert the method's description here.
     * Creation date: (22.12.00 1:33:17)
     *
     * @param obj conexp.core.ContextEntity
     */
    public void addOwnObject(ContextEntity obj) {
        if (null == ownObjects) {
            ownObjects = CollectionFactory.createDefaultList();
        }
        ownObjects.add(obj);
        incrOwnObjCnt();
    }

//--------------------------------------------

    public int compare(ItemSet that) {
        int cmp = getAttribs().compare(that.getAttribs());
        switch (cmp) {
            case Set.EQUAL:
                return EQUAL;
            case Set.SUBSET:
                return GREATER;
            case Set.SUPERSET:
                return LESS;
            default:  //FALLTHROUGH
            case Set.NOT_COMPARABLE:
                return NOT_COMPARABLE;
        }
    }


    /**
     * for test purposes only
     */
    public boolean equals(Object obj) {
        if (null == obj ||
                !(obj instanceof ItemSet)) {
            return false;
        }
        Concept that = (Concept) obj;
        return isConceptEqual(that);
    }

    protected boolean isConceptEqual(Concept that) {
        if (this.getAttribs() == null) {
            if (that.getAttribs() != null) {
                return false;
            }
        } else {
            if (!getAttribs().equals(that.getAttribs())) {
                return false;
            }
        }
        if (this.getObjects() == null) {
            if (that.getObjects() != null) {
                return false;
            }
        } else {
            if (!this.getObjects().equals(that.getObjects())) {
                return false;
            }
        }
        return true;
    }

    private int cachedObjectCount = -1;

    public int getObjCnt() {
        if (-1 == cachedObjectCount) {
            cachedObjectCount = getObjects().elementCount();
        }
        return cachedObjectCount;
    }

    public int getOwnAttrCnt() {
        return null == ownAttribs ? 0 : ownAttribs.size();
    }

    public int getOwnObjCnt() {
        return null == ownObjects ? 0 : ownObjects.size();
    }

    public int hashCode() {
        return getAttribs().hashCode() ^ getObjects().hashCode();
    }

    public boolean hasOwnAttribs() {
        return (conceptType & HAS_ATTRIBS) != 0;
    }


    private void hasOwnAttribs(boolean hasAttr) {
        if (hasAttr) {
            conceptType |= HAS_ATTRIBS;
        } else {
            conceptType &= ~HAS_ATTRIBS;
        }
    }


    private void hasOwnObject(boolean hasObjects) {
        if (hasObjects) {
            conceptType |= HAS_OBJECTS;
        } else {
            conceptType &= ~HAS_OBJECTS;
        }
    }

//----------------------------------------------

    public boolean hasOwnObjects() {
        return (conceptType & HAS_OBJECTS) != 0;
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.10.00 23:31:33)
     */
    private void incrOwnAttrCnt() {
        hasOwnAttribs(true);
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.10.00 23:39:05)
     */
    private void incrOwnObjCnt() {
        hasOwnObject(true);
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.05.01 21:49:09)
     *
     * @param intent conexp.core.Set
     * @param extent conexp.core.Set
     * @return conexp.core.LatticeElement
     */
    public static Concept makeFromSets(ModifiableSet extent, ModifiableSet intent) {
        return new Concept(extent, intent);
    }


    /**
     * Insert the method's description here.
     * Creation date: (22.12.00 1:27:03)
     *
     * @return java.util.Iterator
     */
    public Iterator ownAttribsIterator() {
        return null == ownAttribs ? NullIterator.makeNull() : ownAttribs.iterator();
    }


    /**
     * Insert the method's description here.
     * Creation date: (22.12.00 1:27:03)
     *
     * @return java.util.Iterator
     */
    public Iterator ownObjectsIterator() {
        return null == ownObjects ? NullIterator.makeNull() : ownObjects.iterator();
    }


    public Iterator intentIterator(ExtendedContextEditingInterface cxt) {
        return new AttributeIterator(cxt, getAttribs());
    }

    public Iterator extentIterator(ExtendedContextEditingInterface cxt) {
        return new ObjectIterator(cxt, getObjects());
    }

    /**
     * Insert the method's description here.
     * Creation date: (09.03.01 3:45:26)
     *
     * @return java.lang.String
     */
    public String toString() {
        return "Intent " + getAttribs() + "  Extent " + getObjects();
    }

    public Set getAttribs() {
        return attribs;
    }

    public Set getObjects() {
        return objects;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public ModifiableSet getModifiableAttribs() {
        return attribs;
    }

    /*
     * @@modifier
    */

    public void includeObjectsInConcept(Set objects) {
        //refactor to modifiable conexp
        ((ModifiableSet) this.getObjects()).or(objects);
    }
    /*
     * @@modifier
    */

    public void addObject(int i) {
        ((ModifiableSet) this.getObjects()).put(i);
    }

}
