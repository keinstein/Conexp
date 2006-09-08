/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import java.util.Comparator;
import java.util.Iterator;

public class ImplicationSet extends DependencySet {

    public interface ImplicationProcessor {
        void processImplication(Implication implication);
    }

    static class DependencyProcessorAdapter implements DependencyProcessor {
        final ImplicationProcessor implProcessor;

        public DependencyProcessorAdapter(ImplicationProcessor implProcessor) {
            this.implProcessor = implProcessor;
        }

        public void processDependency(Dependency dependency) {
            implProcessor.processImplication((Implication) dependency);
        }
    }

    public static class SupportDescComparator implements Comparator {
        public int compare(Object o1, Object o2) {
            return ((Dependency) o2).getRuleSupport() - ((Dependency) o1).getRuleSupport();
        }
    }

    public static final SupportDescComparator supportDescComparator = new SupportDescComparator();

    //---------------------------------------
    public ImplicationSet(AttributeInformationSupplier attributeInformationSupplier) {
        super(attributeInformationSupplier);
    }

    protected DependencySet makeDependencySet(AttributeInformationSupplier attributesInformation) {
        return new ImplicationSet(attributesInformation);
    }

    //---------------------------------------
    public void addImplication(Implication dep) {
        super.addDependency(dep);
    }

    public void addAll(ImplicationSet other) {
        for (Iterator iterator = other.iterator(); iterator.hasNext();) {
            addImplication((Implication) iterator.next());
        }
    }

    //---------------------------------------
    public Implication getImplication(int i) {
        return (Implication) getDependency(i);
    }

    //---------------------------------------
    public boolean isDerived(Implication imp) {
        return isSetDerivedToConclusion(imp.getPremise().makeModifiableSetCopy(), imp.conclusion);
    }

    //---------------------------------------
    private boolean isDGDerived(Implication imp) {
        return isSetDerivedToConclusion(imp.getModifiablePremise(), imp.conclusion);
    }

    //---------------------------------------
    private boolean isSetDerivedToConclusion(ModifiableSet set, Set conclusion) {
        setClosure(set);
        return conclusion.isSubsetOf(set);
    }

    //---------------------------------------
    public void makeDisjoint() {
        Iterator it = dependencies.iterator();
        while (it.hasNext()) {
            ((Implication) it.next()).makeDisjoint();
        }
    }

    //---------------------------------------
    public void makeDuquenneGuigues() {
        removeRedundantAndFindClosure();
        makeDisjoint();
    }

    private void removeRedundantAndFindClosure() {
        for (int j = dependencies.size(); --j >= 0;) {
            Implication imp = getImplication(j);
            imp.conclusion.or(imp.getPremise());
            setClosure(imp.conclusion);
        }
        for (int j = dependencies.size(); --j >= 0;) {
            Implication imp = getImplication(j);
            dependencies.remove(j);
            if (!isDGDerived(imp)) {
                dependencies.add(imp);
            } // end of if ()
        } // end of for ()
    }

    //---------------------------------------
    public void makeNonRedundant() {
        for (int j = dependencies.size(); --j >= 0;) {
            Implication imp = getImplication(j);
            dependencies.remove(j);
            if (!isDerived(imp)) {
                dependencies.add(imp);
            } // end of if ()
        } // end of for ()
    }

    //---------------------------------------

    /**
     * @param toClose
     * @return whether set to close was modified
     */
    public boolean setClosure(ModifiableSet toClose) {
        //naive but robust implementation
        //TBD to change it on linear closure
        if (dependencies.isEmpty()) {
            return false;
        }
        boolean quit = false;
        boolean modified = false;
        while (!quit) {
            quit = true;
            for (int i = dependencies.size(); --i >= 0;) {
                Implication imp = (Implication) dependencies.get(i);
                if (toClose.isSupersetOf(imp.getPremise())) {
                    int cmp2 = toClose.compare(imp.getConclusion());
                    if (Set.SUBSET == cmp2 ||
                            Set.NOT_COMPARABLE == cmp2) {
                        toClose.or(imp.getConclusion());
                        quit = false;
                        modified = true;
                    } // end of if ()
                } // end of if ()
            } // end of for ()
        } // end of while ()
        return modified;
    }

    public boolean isRespectedBySet(Set set) {
        for (int i = dependencies.size(); --i >= 0;) {
            if (!getImplication(i).isRespectedBySet(set)) {
                return false;
            }
        }
        return true;
    }

    //---------------------------------------
    public void addDependency(Dependency dep) {
        if (dep instanceof Implication) {
            super.addDependency(dep);
        } else {
            throw new IllegalArgumentException("ImplicationSet can contain only implications");
        }
    }

    public boolean equalsToIsomorphism(ImplicationSet other) {
        if (this.getSize() != other.getSize()) {
            return false;
        }
        return isAllImplicationsContainedInOtherImplicationSet(other) &&
                other.isAllImplicationsContainedInOtherImplicationSet(this);
    }

    public void forEach(ImplicationProcessor block) {
        super.forEach(new DependencyProcessorAdapter(block));
    }

    private boolean isAllImplicationsContainedInOtherImplicationSet(ImplicationSet other) {
        for (int i = 0; i < getSize(); i++) {
            found:
            {
                Implication currImplication = getImplication(i);
                for (int j = 0; j < other.getSize(); j++) {
                    if (currImplication.equals(other.getImplication(j))) {
                        break found;
                    }
                }
                return false;
            }//found
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ImplicationSet)) {
            return false;
        }
        ImplicationSet that = (ImplicationSet) obj;

        return equalsToIsomorphism(that);
    }
}
