/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core;

import util.StringUtil;
import util.collection.CollectionFactory;

import java.util.*;


public class DependencySet {

    public interface DependencyProcessor {
        void processDependency(Dependency dependency);
    }

    protected AttributeInformationSupplier attrInfo;

    protected List dependencies;

    /**
     * Insert the method's description here.
     * Creation date: (01.05.01 19:18:02)
     */
    public DependencySet(AttributeInformationSupplier attrInfo) {
        dependencies = CollectionFactory.createDefaultList();
        this.attrInfo = attrInfo;
    }

//---------------------------------------
    public void addDependency(Dependency dep) {
        dependencies.add(dep);
    }


//---------------------------------------
    public void clear() {
        dependencies.clear();
    }


    public Collection dependencies() {
        return Collections.unmodifiableCollection(dependencies);
    }


    /**
     * Insert the method's description here.
     * Creation date: (08.05.01 21:45:38)
     * @return conexp.core.Context
     */
    public AttributeInformationSupplier getAttributesInformation() {
        return attrInfo;
    }


//---------------------------------------
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
        dependencies.remove(index);
    }

    public void forEach(DependencyProcessor block) {
        for (int i = 0; i < getSize(); i++) {
            block.processDependency(getDependency(i));
        }
    }


//---------------------------------------
    public int getSize() {
        return dependencies.size();
    }
//---------------------------------------
    /**
     * Insert the method's description here.
     * Creation date: (22.11.00 23:45:53)
     * @param comp java.util.Comparator
     */
    public void sort(Comparator comp) {
        Collections.sort(dependencies, comp);
    }


    public String toString() {
        final StringBuffer ret = new StringBuffer();
        ret.append(StringUtil.extractClassName(getClass().toString()));
        ret.append("\n { \n");
        forEach(new DependencyProcessor() {
            public void processDependency(Dependency dependency) {
                ret.append(dependency.toString());
                ret.append("\n");
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

}
