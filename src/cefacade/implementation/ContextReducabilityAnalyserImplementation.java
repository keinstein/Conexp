/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package cefacade.implementation;

import cefacade.IContextReducabilityAnalyser;
import cefacade.IEntityReducibilityInfo;
import cefacade.ISimpleContext;
import conexp.core.BinaryRelation;
import conexp.core.Context;
import conexp.core.DefaultContextListener;
import conexp.core.Set;
import util.collection.CollectionFactory;
import util.collection.mutimaps.ListMultiMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




public class ContextReducabilityAnalyserImplementation implements IContextReducabilityAnalyser {
    protected SimpleContextImplementation context;

    public void setContext(ISimpleContext context) {
        this.context = (SimpleContextImplementation) context;
        this.context.getContext().addContextListener(new DefaultContextListener() {
            public void relationChanged() {
                recalcReducabilityInfo();
            }

            public void contextTransposed() {
                recalcReducabilityInfo();
            }

            public void contextStructureChanged() {
                recalcReducabilityInfo();
            }
        });
        recalcReducabilityInfo();
    }

    protected Context getNativeContext() {
        return context.getContext();
    }

    protected Map objectsToReducibilityInfo;

    private void setObjectsToReducibilityInfo(Map objectsToReducibilityInfo) {
        this.objectsToReducibilityInfo = objectsToReducibilityInfo;
    }

    public IEntityReducibilityInfo getObjectReducabilityInfo(int objectId) {
        return (IEntityReducibilityInfo) objectsToReducibilityInfo.get(new Integer(objectId));
    }

    protected Map attributesToReducibilityInfo;

    private void setAttributesToReducibilityInfo(Map attributesToReducibilityInfo) {
        this.attributesToReducibilityInfo = attributesToReducibilityInfo;
    }

    public IEntityReducibilityInfo getAttributeReducabilityInfo(int attributeId) {
        return (IEntityReducibilityInfo) attributesToReducibilityInfo.get(new Integer(attributeId));
    }

    private void recalcReducabilityInfo() {
        Map objectIdToReducabilityInfoMap = buildObjectReducabilityInfoMap(getNativeContext());
        Context transposed = getNativeContext().makeNativeCopy();
        transposed.transpose();

        Map attributeIdReducabilityInfo = buildObjectReducabilityInfoMap(transposed);

        setObjectsToReducibilityInfo(objectIdToReducabilityInfoMap);
        setAttributesToReducibilityInfo(attributeIdReducabilityInfo);
    }

    private static Map buildObjectReducabilityInfoMap(Context cxt) {
        BinaryRelation relation = cxt.getRelation();
        ListMultiMap classesOfEquivalence = findObjectClassesOfEquivalence(relation);
        BinaryRelation downArrow = cxt.getDownArrow();
        Map objectIdToReducabilityInfoMap = CollectionFactory.createDefaultMap();
        List reducibleClasses = CollectionFactory.createDefaultList();
        List irreducibleClasses = CollectionFactory.createDefaultList();
        for (Iterator iterator = classesOfEquivalence.keySet().iterator(); iterator.hasNext();) {
            Integer objId = (Integer) iterator.next();
            List equivalentObjects = classesOfEquivalence.getListForKey(objId);
            boolean irreducible = !downArrow.getSet(objId.intValue()).isEmpty();

            EntityReducabilityInfo info = new EntityReducabilityInfo(equivalentObjects, irreducible);
            if (irreducible) {
                irreducibleClasses.add(info);
            } else {
                reducibleClasses.add(info);
            }
            for (Iterator equivalentObjectIterator = equivalentObjects.iterator(); equivalentObjectIterator.hasNext();)
            {
                Integer equiObjId = (Integer) equivalentObjectIterator.next();
                objectIdToReducabilityInfoMap.put(equiObjId, info);
            }
        }

        for (Iterator reducibleClassesIter = reducibleClasses.iterator(); reducibleClassesIter.hasNext();) {
            EntityReducabilityInfo reducabilityInfo = (EntityReducabilityInfo) reducibleClassesIter.next();
            Set reducibleSet = getSetForClass(relation, reducabilityInfo);

            List reducingClasses = CollectionFactory.createDefaultList();
            for (Iterator irreducible = irreducibleClasses.iterator(); irreducible.hasNext();) {
                EntityReducabilityInfo irreducableClassInfo = (EntityReducabilityInfo) irreducible.next();
                Set irreducibleSet = getSetForClass(relation, irreducableClassInfo);
                if (reducibleSet.isSubsetOf(irreducibleSet)) {
                    reducingClasses.add(irreducableClassInfo);
                }
            }

            reducabilityInfo.setReducingClasses(reducingClasses);
        }
        return objectIdToReducabilityInfoMap;
    }

    private static Set getSetForClass(BinaryRelation relation, EntityReducabilityInfo reducabilityInfo) {
        return relation.getSet(((Integer) reducabilityInfo.getClassOfEquivalence().get(0)).intValue());
    }

    private static ListMultiMap findObjectClassesOfEquivalence(BinaryRelation relation) {
        ListMultiMap classesOfEquivalence = new ListMultiMap();
        Map intentObjectIdMap = CollectionFactory.createDefaultMap();
        int objectCount = relation.getRowCount();
        for (int i = 0; i < objectCount; i++) {
            Set objectIntent = relation.getSet(i);
            Integer objId = (Integer) intentObjectIdMap.get(objectIntent);
            if (null != objId) {
                classesOfEquivalence.getListForKey(objId).add(new Integer(i));
            } else {
                objId = new Integer(i);
                intentObjectIdMap.put(objectIntent, objId);
                classesOfEquivalence.getListForKey(objId).add(objId);
            }
        }
        return classesOfEquivalence;
    }

    static class EntityReducabilityInfo implements IEntityReducibilityInfo {
        List equivalentObjectIds;
        List reducingClasses = Collections.EMPTY_LIST;
        boolean irreducible;


        public EntityReducabilityInfo(List equivalentObjects, boolean irreducible) {
            this.equivalentObjectIds = equivalentObjects;
            this.irreducible = irreducible;
        }

        public boolean isIrreducible() {
            return irreducible;
        }

        public List getClassOfEquivalence() {
            return Collections.unmodifiableList(equivalentObjectIds);
        }

        void setReducingClasses(List reducingClasses) {
            this.reducingClasses = reducingClasses;
        }

        public Collection getReducingClasses() {
            return Collections.unmodifiableList(reducingClasses);
        }
    }

}
