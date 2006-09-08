/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core.calculationstrategies;

import conexp.core.AttributeInformationSupplier;
import conexp.core.ContextFactoryRegistry;
import conexp.core.DefaultBinaryRelationProcessor;
import conexp.core.Implication;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.ImplicationSet;
import conexp.core.ModifiableSet;
import conexp.core.Set;

import java.util.Comparator;


public class AttributeIncrementalImplicationCalculator
        extends DefaultBinaryRelationProcessor
        implements ImplicationCalcStrategy {

    public void setImplications(ImplicationSet implSet) {
        m_isBasis = implSet;
    }

    public void calcImplications() {
        //Assert.isTrue(null != m_isBasis);

        AttributeInformationSupplier ais = m_isBasis.getAttributesInformation();
        m_isConceptsAndBasis = new ImplicationSet(ais);
        m_isNewStableImpl = new ImplicationSet(ais);
        m_isMinModImpl = new ImplicationSet(ais);
        m_isNonMinModImpl = new ImplicationSet(ais);
        m_isNonMinModElements = new ImplicationSet(ais);

        int nAttributeCount = rel.getColCount();
        m_sEmptyAttributeSet = ContextFactoryRegistry.createSet(nAttributeCount);

        ModifiableSet msEntireObjectSet = ContextFactoryRegistry.createSet(rel.getRowCount());
        msEntireObjectSet.fill();
        m_isConceptsAndBasis.addImplication(
                new ImplicationEx(msEntireObjectSet, m_sEmptyAttributeSet, m_sEmptyAttributeSet));

        m_msCurrentAttributes = m_sEmptyAttributeSet.makeModifiableSetCopy();
        for (int i = 0; i < nAttributeCount; i++) {
            m_msCurrentAttributes.put(i);
            addAttribute(i);
        }
    }

    public void tearDown() {
        super.tearDown();
        m_isBasis = null;
        m_isConceptsAndBasis = null;
        m_isMinModImpl = null;
        m_isNewStableImpl = null;
        m_isNonMinModElements = null;
        m_isNonMinModImpl = null;
        m_msCurrentAttributes = null;
        m_sEmptyAttributeSet = null;
    }


    private void addAttribute(int nAttributeNumber) {
        m_isBasis.clear();
        m_isNewStableImpl.clear();
        m_isMinModImpl.clear();
        m_isNonMinModImpl.clear();
        m_isNonMinModElements.clear();

        Set sAttributeExtent = getAttributeExtent(nAttributeNumber);

        int nNumberOfItems = m_isConceptsAndBasis.getSize();
        for (int i = 0; i < nNumberOfItems;) {
            ImplicationEx ieItem = (ImplicationEx) m_isConceptsAndBasis.getImplication(i);

            boolean fKeepItemAtI = true;
            if (ieItem.getExtent().isSubsetOf(sAttributeExtent)) {
                if (ieItem.isConcept()) {
                    fKeepItemAtI = processModifiedConcept(ieItem, nAttributeNumber, i);
                } else {
                    fKeepItemAtI = processModifiedImplication(ieItem, nAttributeNumber, i);
                }
            } else {
                if (ieItem.isConcept()) {
                    processStableConcept(sAttributeExtent, nAttributeNumber, ieItem);
                } else {
                    processStableImplication(ieItem);
                }
            }

            if (fKeepItemAtI) {
                i++;
            } else {
                nNumberOfItems--;
            }
        }

        m_isBasis.addAll(m_isNewStableImpl);
        m_isBasis.addAll(m_isMinModImpl);
        fuse();
    }

    private void fuse() {
        while (m_isNonMinModImpl.getSize() > 0) {
            Implication iImplication = m_isNonMinModImpl.getImplication(0);
            m_isNonMinModImpl.removeDependency(0);
            if (computeSaturation(iImplication, m_isNonMinModImpl)) {
                iImplication.makeDisjoint();
                m_isBasis.addDependency(iImplication);
                m_isNonMinModElements.addImplication(iImplication);
            }
        }
        m_isNonMinModElements.sort(CIC);
        m_isConceptsAndBasis.addAll(m_isNonMinModElements);
    }

    private boolean computeSaturation(Implication iImplication, ImplicationSet isExtraImplications) {
        ImplicationSet isAllImplications = new ImplicationSet(m_isBasis.getAttributesInformation());
        isAllImplications.addAll(m_isBasis);
        isAllImplications.addAll(isExtraImplications);

        ModifiableSet msPremise = iImplication.getModifiablePremise();
        Set sConclusion = iImplication.getConclusion();
        //naive but robust implementation
        //TBD to change it on linear closure
        if (0 == isAllImplications.getSize()) {
            return true;
        }
        boolean quit = false;
        while (!quit) {
            quit = true;
            for (int i = isAllImplications.getSize(); --i >= 0;) {
                Implication imp = isAllImplications.getImplication(i);
                if (msPremise.isSupersetOf(imp.getPremise())) {
                    int cmp2 = msPremise.compare(imp.getConclusion());
                    if (Set.SUBSET == cmp2
                            || Set.NOT_COMPARABLE == cmp2) {
                        msPremise.or(imp.getConclusion());
                        if (sConclusion.isSubsetOf(msPremise)) {
                            return false;
                        }
                        quit = false;
                    } // end of if ()
                } // end of if ()
            } // end of for ()
        } // end of while ()
        return true;
    }


    private void processStableConcept(Set sAttributeExtent, int nAttributeNumber,
                                      ImplicationEx ieConcept) {
        //Assert.isTrue(ieConcept.isConcept());
        ModifiableSet msNewExtent = ieConcept.getExtent().makeModifiableSetCopy();
        msNewExtent.and(sAttributeExtent);
        ModifiableSet msNewPremise = ieConcept.getPremise().makeModifiableSetCopy();
        msNewPremise.put(nAttributeNumber);

        ModifiableSet msNewConclusion = getIntent(msNewExtent);
        msNewConclusion.andNot(msNewPremise);
        if (msNewConclusion.isEmpty()) {
            m_isConceptsAndBasis.addImplication(new ImplicationEx(msNewExtent, msNewPremise, msNewConclusion));
        } else if (m_isNewStableImpl.isRespectedBySet(msNewPremise)) {
            ImplicationEx ieNewImplication = new ImplicationEx(msNewExtent, msNewPremise, msNewConclusion);
            m_isNewStableImpl.addImplication(ieNewImplication);
            m_isConceptsAndBasis.addImplication(ieNewImplication);
        }
    }

    private ModifiableSet getIntent(Set sExtent) {
        ModifiableSet msIntent = ContextFactoryRegistry.createSet(rel.getColCount());
        msIntent.fill();
        int i = sExtent.firstIn();
        while (i >= 0) {
            msIntent.and(rel.getSet(i));
            i = sExtent.nextIn(i);
        }
        msIntent.and(m_msCurrentAttributes);
        return msIntent;
    }

    private void processStableImplication(Implication iImplication) {
        m_isBasis.addImplication(iImplication);
    }

    private boolean processModifiedConcept(ImplicationEx ieConcept, int nAttributeNumber, int nConceptIndex) {
        //Assert.isTrue(m_isConceptsAndBasis.getImplication(nConceptIndex) == ieConcept);
        //Assert.isTrue(ieConcept.getConclusion().isEmpty());

        Set sIntent = ieConcept.getPremise();
        boolean fNewImplication = isMinimalPremise(sIntent, m_isMinModImpl);
        if (fNewImplication) {
            ImplicationEx ieNewImplication = new ImplicationEx(ieConcept.getExtent(), sIntent, m_sEmptyAttributeSet);
            ieNewImplication.addToConclusion(nAttributeNumber);

            m_isMinModImpl.addDependency(ieNewImplication);
            m_isConceptsAndBasis.setDependency(nConceptIndex, ieNewImplication);
        } else {
            m_isConceptsAndBasis.removeDependency(nConceptIndex);
        }
        ieConcept.addToPremise(nAttributeNumber);
        m_isNonMinModElements.addImplication(ieConcept);
        return fNewImplication;
    }

    private boolean isMinimalPremise(Set sPremise, ImplicationSet isImplications) {
        int nSize = isImplications.getSize();
        for (int i = 0; i < nSize; i++) {
            if (isImplications.getDependency(i).getPremise().isSubsetOf(sPremise)) {
                return false;
            }
        }
        return true;
    }

    private boolean processModifiedImplication(ImplicationEx ieImplication, int nAttributeNumber,
                                               int nImplicationIndex) {
        //Assert.isTrue(m_isConceptsAndBasis.getImplication(nImplicationIndex) == ieImplication);
        //Assert.isTrue(ieImplication.getConclusion().out(nAttributeNumber));
        boolean fKeepIt = isMinimalPremise(ieImplication.getPremise(), m_isMinModImpl);
        if (fKeepIt) {
            ieImplication.addToConclusion(nAttributeNumber);
            m_isMinModImpl.addImplication(ieImplication);
        } else {
            m_isConceptsAndBasis.removeDependency(nImplicationIndex);
            ieImplication.addToPremise(nAttributeNumber);
            m_isNonMinModImpl.addImplication(ieImplication);
        }
        return fKeepIt;
    }

    private Set getAttributeExtent(int nAttributeNumber) {
        int nObjectCount = rel.getRowCount();
        ModifiableSet msExtent = ContextFactoryRegistry.createSet(nObjectCount);
        for (int i = 0; i < nObjectCount; i++) {
            if (rel.getRelationAt(i, nAttributeNumber)) {
                msExtent.put(i);
            }
        }
        return msExtent;
    }

    private ImplicationSet m_isBasis;
    private ImplicationSet m_isConceptsAndBasis;

    private ModifiableSet m_msCurrentAttributes;
    private Set m_sEmptyAttributeSet;

    private static ConceptImplicationComparator CIC = new ConceptImplicationComparator();

    private ImplicationSet m_isNewStableImpl;
    private ImplicationSet m_isMinModImpl;
    private ImplicationSet m_isNonMinModImpl;
    private ImplicationSet m_isNonMinModElements;

    private static class ImplicationEx extends Implication {
        ImplicationEx(Set sExtent, Set sPremise, Set sConclusion) {
            super(sPremise, sConclusion, sExtent.elementCount());
            m_sExtent = sExtent;
            m_fIsConcept = sConclusion.isEmpty();
        }

        void addToPremise(int nAttributeNumber) {
            premise.put(nAttributeNumber);
        }

        void addToConclusion(int nAttributeNumber) {
            conclusion.put(nAttributeNumber);
            m_fIsConcept = false;
        }

        Set getExtent() {
            return m_sExtent;
        }

        boolean isConcept() {
            return m_fIsConcept;
        }

        private final Set m_sExtent;
        private boolean m_fIsConcept;
    }

    private static class ConceptImplicationComparator implements Comparator {
        public int compare(Object oLeft, Object oRight) {
            //Assert.isTrue(oLeft instanceof ImplicationEx);
            //Assert.isTrue(oRight instanceof ImplicationEx);
            return ((Implication) oLeft).getPremise().lexCompareGanter(((Implication) oRight).getPremise());
        }
    }
}
