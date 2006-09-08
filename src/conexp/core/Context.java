/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import util.Assert;
import util.FormatUtil;
import util.collection.CollectionFactory;

import java.beans.PropertyChangeEvent;
import java.util.Iterator;
import java.util.List;


public class Context implements AttributeInformationSupplier, ExtendedContextEditingInterface {
    private List objects = CollectionFactory.createFastIndexAccessList();
    //---------------------------------------------------------------
    private List attributes = CollectionFactory.createFastIndexAccessList();
    //---------------------------------------------------------------
    private ModifiableBinaryRelation rel;

    public ContextEditingInterface makeCopy() {
        return makeNativeCopy();
    }

    public Context makeNativeCopy() {
        Context ret = new Context(0, 0);
        ret.copyFrom(this);
        ret.setArrowCalculator(getArrowCalculator().makeNew());
        return ret;
    }

    private static String formAttributeName(int hintForName) {
        //TODO - extract string into properties
        return FormatUtil.format("Attr {0}", hintForName);
    }

    private static String formObjectName(int hint) {
        //TODO - extract string into properties
        return FormatUtil.format("Obj {0}", hint);
    }


    public String makeUniqueAttributeName() {
        int startValue = getAttributeCount();
        String candName;
        do {
            candName = formAttributeName(startValue++);
        } while (hasAttributeWithName(candName));

        return candName;
    }

    public String makeUniqueObjectName() {
        int startValue = getObjectCount() + 1;
        String candName;
        do {
            candName = formObjectName(startValue++);
        } while (hasObjectWithName(candName));

        return candName;
    }

    public int indexOfAttribute(String name) {
        final int attributeCount = getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            if (getAttribute(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public boolean hasAttributeWithName(String cand) {
        return indexOfAttribute(cand) != -1;
    }


    public boolean hasObjectWithName(String cand) {
        for (int i = 0; i < getObjectCount(); i++) {
            if (getObject(i).getName().equals(cand)) {
                return true;
            }
        }
        return false;
    }


    private ContextEntity makeAttributeObject(int hintForName) {
        return ContextEntity.createContextAttribute(formAttributeName(hintForName));
    }


    private transient ContextListenerSupport contextListenersSupport;


    private transient ContextEntityListener objectNameListener = new DefaultContextObjectListener() {
        public void nameChanged(PropertyChangeEvent evt) {
            getContextListenersSupport().fireObjectNameChanged(evt);
        }
    };

    private transient ContextEntityListener attributeNameListener = new DefaultContextObjectListener() {
        public void nameChanged(PropertyChangeEvent evt) {
            getContextListenersSupport().fireAttributeNameChanged(evt);
        }
    };

    public Context(int objCnt, int arrCnt) {
        this(ContextFactoryRegistry.createRelation(objCnt, arrCnt));
    }


    public Context(ModifiableBinaryRelation rel) {
        this.rel = rel;
        createDummyObjectsAndAttribs(rel.getRowCount(), rel.getColCount());
        addContextListener(arrowRelationUpdator);
    }

    //---------------------------------------------------------------
    private void ensureRelationsSizes(int rows, int cols) {
        rel.setDimension(rows, cols);
    }

    //---------------------------------------------------------------
    public ContextEntity getAttribute(int index) {
        return (ContextEntity) attributes.get(index);
    }

    //---------------------------------------------------------------
    public int getAttributeCount() {
        return attributes.size();
    }

    //---------------------------------------------------------------
    public ContextEntity getObject(int index) {
        return (ContextEntity) objects.get(index);
    }

    //---------------------------------------------------------------
    public int getObjectCount() {
        return objects.size();
    }

    //---------------------------------------------------------------
    public BinaryRelation getRelation() {
        return rel;
    }

    //---------------------------------------------------------------
    public boolean getRelationAt(int objectId, int attrId) {
        return rel.getRelationAt(objectId, attrId);
    }

    //---------------------------------------------------------------
    public void increaseAttributes(int incrAttr) {
        Assert.isTrue(incrAttr > 0, "Attrib increment should be greater than zero");
        int oldColCnt = rel.getColCount();
        int newColCnt = oldColCnt + incrAttr;
        ensureRelationsSizes(rel.getRowCount(), newColCnt);
        addAttributesWithDefaultNamesInRange(oldColCnt, newColCnt);
        getContextListenersSupport().fireContextStructureChanged();
    }

    public void addAttribute(ContextEntity newAttrib) {
        final int newAttributeIndex = rel.getColCount();
        ensureRelationsSizes(rel.getRowCount(), newAttributeIndex + 1);
        addAttributeToAttributeList(newAttrib, newAttributeIndex);
        getContextListenersSupport().fireContextStructureChanged();
    }

    public void addObject(ContextEntity newObject) {
        final int newObjectIndex = rel.getRowCount();
        ensureRelationsSizes(newObjectIndex + 1, rel.getColCount());
        addObjectToObjectList(newObject, newObjectIndex);
        getContextListenersSupport().fireContextStructureChanged();
    }

    public void addObjectWithNameAndIntent(String name, Set intent) {
        int objIndex = getObjectCount();
        increaseObjects(1);
        getObject(objIndex).setName(name);
        rel.getModifiableSet(objIndex).copy(intent);
        getContextListenersSupport().fireRelationChanged();
    }

    private void addAttributesWithDefaultNamesInRange(int from, int till) {
        for (int j = from; j < till; j++) {
            addAttributeToAttributeList(makeAttributeObject(j + 1), j);
        }
    }

    private void addAttributeToAttributeList(ContextEntity newAttribute, int attributeIndex) {
        Assert.isTrue(!newAttribute.isObject());
        newAttribute.setContextEntityListener(attributeNameListener);
        attributes.add(newAttribute);
        getContextListenersSupport().fireAttributeInserted(attributeIndex);
    }

    //---------------------------------------------------------------
    public void increaseObjects(int incrObjects) {
        Assert.isTrue(incrObjects > 0, "Objects increment should be greater than zero");
        int oldRowCnt = rel.getRowCount();
        int newRowCnt = oldRowCnt + incrObjects;
        ensureRelationsSizes(newRowCnt, rel.getColCount());
        addObjectsWithDefaultNamesInRange(oldRowCnt, newRowCnt);
        getContextListenersSupport().fireContextStructureChanged();
    }

    private void addObjectsWithDefaultNamesInRange(int from, int till) {
        for (int j = from; j < till; j++) {
            int hint = j + 1;
            ContextEntity newObject = ContextEntity.createContextObject(formObjectName(hint));
            addObjectToObjectList(newObject, j);
        }
    }

    private void addObjectToObjectList(ContextEntity newObject, int index) {
        Assert.isTrue(newObject.isObject());
        newObject.setContextEntityListener(objectNameListener);
        objects.add(newObject);
        getContextListenersSupport().fireObjectInserted(index);
    }

    //---------------------------------------------------------------
    public void clarifyAttributes() {
        doClarifyAttributes();
        getContextListenersSupport().realisePostponedStructureChange();
    }

    private void doClarifyAttributes() {
        int numObj = rel.getRowCount();
        int numAttr = rel.getColCount();
        ModifiableSet toClear = ContextFactoryRegistry.createSet(numAttr);
        calcRedundantAttributes(numAttr, numObj, toClear);
        for (int j = toClear.length(); --j >= 0;) {
            if (toClear.in(j)) {
                doRemoveAttribute(j);
            }
        }
    }

    private void calcRedundantAttributes(int numAttr, int numObj, ModifiableSet toClear) {
        ModifiableSet outer = ContextFactoryRegistry.createSet(numAttr);
        ModifiableSet inner = ContextFactoryRegistry.createSet(numAttr);
        ModifiableSet allAttr = ContextFactoryRegistry.createSet(numAttr);
        allAttr.fill();
        for (int j = 0; j < numAttr; j++) {
            if (!toClear.in(j)) {
                inner.copy(allAttr);
                outer.clearSet();
                for (int i = numObj; --i >= 0;) {
                    Set curr = rel.getSet(i);
                    if (curr.in(j)) {
                        inner.and(curr);
                    } else {
                        outer.or(curr);
                    }
                }

                //(inner &~outer) \j = set of all attributes, equivalent to j
                inner.andNot(outer);
                inner.remove(j);
                toClear.or(inner);
            }
        }
    }

    //---------------------------------------------------------------
    public void clarifyObjects() {
        doClarifyObjects();
        getContextListenersSupport().realisePostponedStructureChange();

    }

    private void doClarifyObjects() {
        int bound = rel.getRowCount();
        for (int i = 0; i < bound; i++) {
            Set curr = rel.getSet(i);
            for (int j = i + 1; j < bound; j++) {
                Set other = rel.getSet(j);
                if (curr.equals(other)) {
                    doRemoveObject(j);
                    j--;
                    bound--;
                }
            }
        }
    }

    //---------------------------------------------------------------
    public void removeAttribute(int index) {
        doRemoveAttribute(index);
        getContextListenersSupport().fireContextStructureChanged();
    }

    private void doRemoveAttribute(int index) {
        rel.removeCol(index);
        ContextEntity attr = (ContextEntity) attributes.get(index);
        attr.setContextEntityListener(null);
        attributes.remove(index);

        getContextListenersSupport().fireAttributeRemoved(index);
        getContextListenersSupport().madePostponedStructureChange();
    }

    //---------------------------------------------------------------
    public void removeObject(int index) {
        doRemoveObject(index);
        getContextListenersSupport().fireContextStructureChanged();
    }

    private void doRemoveObject(int index) {
        rel.removeRow(index);
        ContextEntity obj = getObject(index);
        obj.setContextEntityListener(null);
        objects.remove(index);
        getContextListenersSupport().fireObjectRemoved(index);
        getContextListenersSupport().madePostponedStructureChange();
    }

    //---------------------------------------------------------------
    public void setDimension(int numObj, int numAttr) {
        int oldNumObj = getObjectCount();
        if (numObj > oldNumObj) {
            increaseObjects(numObj - oldNumObj);
        } else {
            for (int k = oldNumObj; --k >= numObj;) {
                doRemoveObject(k);
            }
        }
        int oldNumAttr = getAttributeCount();
        if (numAttr > oldNumAttr) {
            increaseAttributes(numAttr - oldNumAttr);
        } else {
            for (int k = oldNumAttr; --k >= numAttr;) {
                doRemoveAttribute(k);
            }
        }
        getContextListenersSupport().realisePostponedStructureChange();
    }


    private void createDummyObjectsAndAttribs(int objCnt, int attrCnt) {
        addObjectsWithDefaultNamesInRange(0, objCnt);
        addAttributesWithDefaultNamesInRange(0, attrCnt);
    }

//----------------------------------------------------------------

    //todo: move from context, the only dependent part from context is addition of context entities
    public void locateElementsConcepts(Lattice lattice) {
        if (!lattice.isEmpty()) {
            findObjectsConcepts(lattice);
            findAttributesConcepts(lattice);
        }
    }


    public void locateElementsConcepts(Lattice lattice, Set attributeMask, Set objectMask) {
        if (!lattice.isEmpty()) {
            findObjectsConcepts(lattice, objectMask);
            findAttributesConcepts(lattice, attributeMask);
        }
    }
//-----------------------------------------------------------------

    private void findAttributesConcepts(Lattice lattice) {
        Assert.isTrue(null != lattice);
        for (int j = getAttributeCount(); --j >= 0;) {
            LatticeElement concept = lattice.findLatticeElementForAttr(j);
            Assert.isTrue(null != concept);
            concept.addOwnAttrib(getAttribute(j));
        }
    }

    private void findAttributesConcepts(Lattice lattice, Set attributeMask) {
        Assert.isTrue(null != lattice);
        Assert.isTrue(attributeMask.size() == getAttributeCount());
        for (int j = getAttributeCount(); --j >= 0;) {
            if (attributeMask.in(j)) {
                LatticeElement concept = lattice.findLatticeElementForAttr(j);
                Assert.isTrue(null != concept);
                concept.addOwnAttrib(getAttribute(j));
            }
        }
    }

//----------------------------------------------------------------

    /**
     * ***************************************************************
     * for objects from core find correpondent concepts
     * ***************************************************************
     */
    private void findObjectsConcepts(Lattice lattice) {
        Assert.isTrue(null != lattice);
        for (int j = getObjectCount(); --j >= 0;) {
            LatticeElement concept = lattice.findLatticeElementFromOne(rel.getSet(j));
            Assert.isTrue(null != concept);
            concept.addOwnObject(getObject(j));
        }
    }

    //---------------------------------------------------------------
    private void findObjectsConcepts(Lattice lattice, Set objectMask) {
        Assert.isTrue(null != lattice);
        for (int j = getObjectCount(); --j >= 0;) {
            if (objectMask.in(j)) {
                LatticeElement concept = lattice.findLatticeElementFromOne(rel.getSet(j));
                Assert.isTrue(null != concept);
                concept.addOwnObject(getObject(j));
            }
        }
    }

    //---------------------------------------------------------------
    public void setRelationAt(int objectId, int attrId, boolean value) {
        if (rel.getRelationAt(objectId, attrId) != value) {
            rel.setRelationAt(objectId, attrId, value);
            getContextListenersSupport().fireRelationChanged();
        }
    }

    public void addContextListener(ContextListener lst) {
        getContextListenersSupport().addContextListener(lst);
    }


    private synchronized ContextListenerSupport getContextListenersSupport() {
        if (null == contextListenersSupport) {
            contextListenersSupport = new ContextListenerSupport(this);
        }
        return contextListenersSupport;
    }


    public synchronized void removeContextListener(ContextListener lst) {
        if (null == contextListenersSupport) {
            return;
        }
        contextListenersSupport.removeContextListener(lst);
    }

    public synchronized int getContextListenersCount() {
        if (null == contextListenersSupport) {
            return 0;
        }
        return contextListenersSupport.getListeners().size();
    }


    public synchronized boolean hasContextListener(ContextListener lst) {
        if (null == contextListenersSupport) {
            return false;
        }
        return contextListenersSupport.hasListener(lst);
    }


    public synchronized void transpose() {
        ModifiableBinaryRelation newRel = BinaryRelationUtils.makeTransposedRelation(rel);
        List tmp = objects;
        objects = attributes;
        attributes = tmp;
        for (Iterator iterator = objects.iterator(); iterator.hasNext();) {
            ContextEntity contextEntity = (ContextEntity) iterator.next();
            contextEntity.makeObject();
        }
        for (Iterator iterator = attributes.iterator(); iterator.hasNext();) {
            ContextEntity contextEntity = (ContextEntity) iterator.next();
            contextEntity.makeAttrib();
        }
        setRelation(newRel);
        getContextListenersSupport().fireContextTransposed();
        getContextListenersSupport().fireContextStructureChanged();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ExtendedContextEditingInterface)) {
            return false;
        }
        Context that = (Context) obj;
        if (!this.getRelation().equals(that.getRelation())) {
            return false;
        }
        if (!this.attributes.equals(that.attributes)) {
            return false;
        }
        if (!this.objects.equals(that.objects)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int ret = rel.hashCode();
        ret = 29 * ret + attributes.hashCode();
        ret = 29 * ret + objects.hashCode();
        return ret;
    }

    public String toString() {
        return "Attributes[" + attributes + "] Objects[" + objects + "] Relation [\n" + getRelation() + ']';
    }

    //---------------------------------------------------------------

    private ArrowCalculator arrowCalc;

    public ArrowCalculator getArrowCalculator() {
        return arrowCalc;
    }

    //---------------------------------------------------------------
    public void setArrowCalculator(ArrowCalculator calc) {
        arrowCalc = calc;
        arrowCalc.setRelation(getRelation());
    }

    // Arrow relation related operation
    //---------------------------------------------------------------
    public void reduceAttributes() {
        doClarifyAttributes();
        int numAttr = rel.getColCount();
        ModifiableSet notToClear = ContextFactoryRegistry.createSet(numAttr);
        BinaryRelation up = getUpArrow();
        for (int i = rel.getRowCount(); --i >= 0;) {
            notToClear.or(up.getSet(i));
        }
        for (int j = numAttr; --j >= 0;) {
            if (!notToClear.in(j)) {
                doRemoveAttribute(j);
            }
        }
        getContextListenersSupport().realisePostponedStructureChange();
    }

    //---------------------------------------------------------------
    public void reduceObjects() {
        doClarifyObjects();
        BinaryRelation down = getDownArrow();
        for (int i = rel.getRowCount(); --i >= 0;) {
            if (down.getSet(i).isEmpty()) {
                doRemoveObject(i);
            }
        }
        getContextListenersSupport().realisePostponedStructureChange();
    }


    public BinaryRelation getUpArrow() {
        if (null == upArrow) {
            upArrow = ContextFactoryRegistry.createRelation(getAttributeCount(), getObjectCount());
            setUpArrowUpdate(true);
        }
        if (upArrowNeedUpdate()) {
            calcUpArrow();
        }
        return upArrow;
    }

    private boolean upArrowNeedUpdate() {
        return updateUp;
    }

    //---------------------------------------------------------------
    public boolean hasDownArrow(int row, int col) {
        return getDownArrow().getRelationAt(row, col);
    }

    //---------------------------------------------------------------
    public boolean hasUpArrow(int row, int col) {
        return getUpArrow().getRelationAt(row, col);
    }

    //---------------------------------------------------------------
    public BinaryRelation getDownArrow() {
        if (null == downArrow) {
            downArrow = ContextFactoryRegistry.createRelation(getObjectCount(), getAttributeCount());
            setDownArrowUpdate(true);
        }
        if (downArrowNeedUpdate()) {
            calcDownArrow();
        }
        return downArrow;
    }

    //---------------------------------------------------------------
    private void calcDownArrow() {
        Assert.isTrue(arrowCalc != null, "For calculating arrows calculator should be set!");
        Assert.isTrue(downArrow != null);
        arrowCalc.calcDownArrow(downArrow);
        setDownArrowUpdate(false);
    }

    //---------------------------------------------------------------
    private void calcUpArrow() {
        Assert.isTrue(arrowCalc != null, "For calculating arrows calculator should be set!");
        Assert.isTrue(upArrow != null);
        arrowCalc.calcUpArrow(upArrow);
        setUpArrowUpdate(false);
    }

    //---------------------------------------------------------------
    private boolean downArrowNeedUpdate() {
        return updateDown;
    }

    //---------------------------------------------------------------
    private void setDownArrowUpdate(boolean val) {
        updateDown = val;
    }

    //---------------------------------------------------------------
    private void setUpArrowUpdate(boolean val) {
        updateUp = val;
    }

    private boolean updateDown = true;
    //---------------------------------------------------------------
    private boolean updateUp;
    //---------------------------------------------------------------
    private ModifiableBinaryRelation upArrow;
    //---------------------------------------------------------------
    private ModifiableBinaryRelation downArrow;

    class ArrowRelationUpdatingContextListener extends DefaultContextListener {
        public void relationChanged() {
            setUpArrowUpdate(true);
            setDownArrowUpdate(true);
        }

        public void contextStructureChanged() {
            final int objectCount = getObjectCount();
            final int attributeCount = getAttributeCount();
            if (null != upArrow) {
                upArrow.setDimension(objectCount, attributeCount);
                setUpArrowUpdate(true);
            }
            if (null != downArrow) {
                downArrow.setDimension(objectCount, attributeCount);
                setDownArrowUpdate(true);
            }

        }
    }

    private ArrowRelationUpdatingContextListener arrowRelationUpdator = new ArrowRelationUpdatingContextListener();

    public void copyFrom(ContextEditingInterface cxt) {
        setDimension(cxt.getObjectCount(), cxt.getAttributeCount());
        for (int i = 0; i < cxt.getObjectCount(); i++) {
            getObject(i).setName(cxt.getObject(i).getName());
        }
        for (int i = 0; i < cxt.getAttributeCount(); i++) {
            getAttribute(i).setName(cxt.getAttribute(i).getName());
        }
        if (!rel.equals(cxt.getRelation())) {
            setRelation(cxt.getRelation().makeModifiableCopy());
            getContextListenersSupport().fireRelationChanged();
        }
    }

    private void setRelation(ModifiableBinaryRelation modifiableBinaryRelation) {
        this.rel = modifiableBinaryRelation;
        if (null != arrowCalc) {
            arrowCalc.setRelation(modifiableBinaryRelation);
        }
    }


}
