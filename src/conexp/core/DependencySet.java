/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import util.BasePropertyChangeSupplier;
import util.StringUtil;
import util.collection.CollectionFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;


public class DependencySet extends BasePropertyChangeSupplier {

    public static final String DEPENDENCY_ADDED = "DependencyAdded";
    public static final String DEPENDENCY_REMOVED = "DependencyRemoved";
    public static final String DEPENDENCY_UPDATED = "DependencyUpdated";
    public static final String DEPENDENCY_SET_CHANGED = "DependencySetChanged";


    public interface DependencyProcessor {
        void processDependency(Dependency dependency);
    }

    private AttributeInformationSupplier attrInfo;

    protected List dependencies;

    public DependencySet(AttributeInformationSupplier attrInfo) {
        dependencies = CollectionFactory.createDefaultList();
        this.attrInfo = attrInfo;
    }

//---------------------------------------

    public void addDependency(Dependency dep) {
        dependencies.add(dep);
        firePropertyChange(DEPENDENCY_ADDED, null, dep);
    }

//---------------------------------------

    public void clear() {
        dependencies.clear();
        firePropertyChange(DEPENDENCY_SET_CHANGED, null, null);
    }


    public Collection dependencies() {
        return Collections.unmodifiableCollection(dependencies);
    }


    public AttributeInformationSupplier getAttributesInformation() {
        return attrInfo;
    }


    public Dependency getDependency(int i) {
        return (Dependency) dependencies.get(i);
    }

//---------------------------------------

    public Iterator iterator() {
        return dependencies.iterator();
    }

//---------------------------------------

    public void removeDependency(Dependency impl) {
        dependencies.remove(impl);

    }

    public void removeDependency(int index) {
        Dependency dependency = (Dependency) dependencies.remove(index);
        firePropertyChange(DEPENDENCY_REMOVED, null, dependency);
    }

    public void removeAll(DependencySet toRemove) {
        for (Iterator iterator = toRemove.iterator(); iterator.hasNext();) {
            Dependency dependency = (Dependency) iterator.next();
            removeDependency(dependency);
        }
    }

    public void forEach(DependencyProcessor block) {
        for (int i = 0; i < getSize(); i++) {
            block.processDependency(getDependency(i));
        }
    }

    public int getSize() {
        return dependencies.size();
    }

    public void sort(Comparator comp) {
        Collections.sort(dependencies, comp);
        firePropertyChange(DEPENDENCY_SET_CHANGED, null, null);
    }


    public String toString() {
        final StringBuffer ret = new StringBuffer();
        ret.append(StringUtil.extractClassName(getClass().toString()));
        ret.append("\n { \n");
        forEach(new DependencyProcessor() {
            public void processDependency(Dependency dependency) {
                ret.append(dependency.toString());
                ret.append('\n');
            }
        });
        ret.append("}\n}");
        return ret.toString();
    }

    public DependencySet makeCompatibleDependencySet() {
        return makeDependencySet(getAttributesInformation());
    }

    protected DependencySet makeDependencySet(AttributeInformationSupplier attributesInformation) {
        return new DependencySet(attributesInformation);
    }


    public boolean equalsAsSet(DependencySet that) {
        if (!attrInfo.equals(that.attrInfo)) {
            return false;
        }
        if (this.getSize() != that.getSize()) {
            return false;
        }
        if (!(new HashSet(dependencies)).equals(new HashSet(that.dependencies))) {
            return false;
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DependencySet)) {
            return false;
        }
        DependencySet that = (DependencySet) obj;

        if (!attrInfo.equals(that.attrInfo)) {
            return false;
        }
        if (!dependencies.equals(that.dependencies)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return dependencies.hashCode();
    }

    public void setDependency(int selectedIndex, Dependency newValue) {
        Dependency oldValue = (Dependency) dependencies.set(selectedIndex, newValue);
        firePropertyChange(DEPENDENCY_UPDATED, oldValue, newValue);
    }

}
