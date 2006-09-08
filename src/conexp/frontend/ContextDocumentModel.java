/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend;

import com.gargoylesoftware.base.collections.NotificationList;
import com.gargoylesoftware.base.collections.NotificationListEvent;
import com.gargoylesoftware.base.collections.NotificationListListener;
import conexp.core.ConceptCalcStrategy;
import conexp.core.Context;
import conexp.core.ContextChangeEvent;
import conexp.core.ContextListener;
import conexp.core.DefaultContextListener;
import conexp.core.DependencySet;
import conexp.core.FCAEngineRegistry;
import conexp.core.ImplicationSet;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import conexp.core.enumcallbacks.ConceptNumCallback;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.LatticeSupplier;
import conexp.frontend.ruleview.AbstractDependencySetCalculator;
import conexp.frontend.ruleview.AssociationRuleCalculator;
import conexp.frontend.ruleview.AttributeIncrementalImplicationCalculatorFactory;
import conexp.frontend.ruleview.ImplicationBaseCalculator;
import conexp.frontend.ruleview.ImplicationCalcStrategyFactory;
import conexp.frontend.ruleview.NextClosedSetImplicationCalculatorFactory;
import conexp.util.GenericStrategy;
import conexp.util.gui.strategymodel.GrowingStrategyModel;
import conexp.util.gui.strategymodel.StrategyValueItem;
import util.BasePropertyChangeSupplier;
import util.collection.CollectionFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class ContextDocumentModel extends BasePropertyChangeSupplier {
    public static final String CLEAR_DEPENDENT_POLICY_KEY = "Clear";
    public static final String RECOMPUTE_DEPENDENT_POLICY_KEY = "Recompute";
    public static final String RECALCULATION_POLICY_PROPERTY = "RecalculationPolicy";

    private Context context;
    private ContextModifiedListener contextModifiedListener;


    private ContextListener getContextModifiedListener() {
        if (null == contextModifiedListener) {
            contextModifiedListener = new ContextModifiedListener();
        }
        return contextModifiedListener;
    }

    public void setRecomputeDependentRecalcPolicy() {
        getRecalculationPolicy().setValueByKey(RECOMPUTE_DEPENDENT_POLICY_KEY);
    }

    private class ContextModifiedListener extends DefaultContextListener {
        public void contextStructureChanged() {
            markDirty();
        }

        public void relationChanged() {
            markDirty();
        }

        public void objectNameChanged(PropertyChangeEvent evt) {
            markDirty();
        }

        public void attributeNameChanged(PropertyChangeEvent evt) {
            markDirty();
        }

        public void attributeChanged(ContextChangeEvent changeEvent) {
            markDirty();
        }

        public void objectChanged(ContextChangeEvent changeEvent) {
            markDirty();
        }

        public void contextTransposed() {
            markDirty();
        }
    }


    private boolean modified;

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean newValue) {
        modified = newValue;
    }

    public void markClean() {
        setModified(false);
    }

    public void markDirty() {
        setModified(true);
    }

    public ContextDocumentModel(Context cxt) {
        addLatticeComponentsListener(new NotificationListListener() {
            public void listElementsAdded(NotificationListEvent event) {
                markDirty();
            }

            public void listElementsRemoved(NotificationListEvent event) {
                markDirty();
            }

            public void listElementsChanged(NotificationListEvent event) {
                markDirty();
            }
        });
        setContext(cxt);
    }

    private PropertyChangeListener latticeComponentListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent evt) {
            final String propertyName = evt.getPropertyName();
            if (LatticeCalculator.LATTICE_DRAWING_CHANGED.equals(propertyName) ||
                    LatticeCalculator.LATTICE_CLEARED.equals(propertyName)) {
                markDirty();
            }
        }
    };


    public void setContext(Context cxt) {
        cleanUpListeners();
        doResetLatticeComponents();

        this.context = cxt;
        cxt.setArrowCalculator(FCAEngineRegistry.makeArrowCalculator());
        cxt.addContextListener(getContextModifiedListener());
        cxt.addContextListener(getRecalcPolicy());
    }


    private void cleanUpListeners() {
        if (this.context != null) {
            this.context.removeContextListener(getContextModifiedListener());
            this.context.removeContextListener(getRecalcPolicy());
        }
    }

    public Context getContext() {
        return context;
    }

    //----------------------------------------
    public int getConceptCount() {
        DepthSearchCalculator calc = new DepthSearchCalculator();
        return doCalculateConceptCount(calc);
    }

    //----------------------------------------
    private int doCalculateConceptCount(ConceptCalcStrategy calc) {
        calc.setRelation(getContext().getRelation());
        ConceptNumCallback cnt = new ConceptNumCallback();
        calc.setCallback(cnt);
        calc.calculateConceptSet();
        return cnt.getConceptCount();
    }

    private NotificationList latticeComponents = new NotificationList(
            CollectionFactory.createDefaultList());

    public void addLatticeComponentsListener(NotificationListListener listener) {
        latticeComponents.addNotificationListListener(listener);
    }

    public void removeLatticeComponentsListener(
            NotificationListListener listener) {
        latticeComponents.removeNotificationListListener(listener);
    }

    public List getLatticeComponents() {
        return Collections.unmodifiableList(latticeComponents);
    }

    private LatticeSupplier makeLatticeComponentForDoc() {
        final LatticeComponent result = LatticeComponentFactory.makeLatticeComponent(
                getContext());
        result.addPropertyChangeListener(latticeComponentListener);
        result.setUpLatticeRecalcOnMasksChange();
        result.restorePreferences();
        return result;
    }

    public void addLatticeComponent() {
        latticeComponents.add(makeLatticeComponentForDoc());
    }

    public LatticeComponent getLatticeComponent(int index) {
        return (LatticeComponent) latticeComponents.get(index);
    }

    public void resetLatticeComponents() {
        doResetLatticeComponents();
        //do we really need this ?
        latticeComponents.add(makeLatticeComponentForDoc());
    }

    private void doResetLatticeComponents() {
        for (Iterator iterator = latticeComponents.iterator();
             iterator.hasNext();) {
            LatticeSupplier latticeSupplier = (LatticeSupplier) iterator.next();
            latticeSupplier.cleanUp();
        }
        latticeComponents.clear();
    }

    public int makeLatticeSnapshot(int index) {
        LatticeComponent old = getLatticeComponent(index);
        LatticeComponent newComponent = old.makeCopy();
        latticeComponents.add(newComponent);
        return latticeComponents.size() - 1;
    }

    public int findLatticeComponent(LatticeSupplier latticeComponent) {
        int i = getLatticeComponentsCount();
        while (--i >= 0) {
            if (getLatticeComponent(i) == latticeComponent) {
                return i;
            }
        }
        return -1;
    }

    public int getLatticeComponentsCount() {
        return getLatticeComponents().size();
    }

    public void removeLatticeComponent(LatticeSupplier component) {
        component.removePropertyChangeListener(latticeComponentListener);
        latticeComponents.remove(component);
    }

    private StrategyValueItem recalculationPolicy;

    public StrategyValueItem getRecalculationPolicy() {
        if (null == recalculationPolicy) {
            StrategyValueItem strategyValueItem = new StrategyValueItem(
                    RECALCULATION_POLICY_PROPERTY,
                    createRecalcStrategyModel(), getPropertyChangeSupport());
            strategyValueItem.getPropertyChange().addPropertyChangeListener(
                    RECALCULATION_POLICY_PROPERTY,
                    new PropertyChangeListener() {
                        public void propertyChange(PropertyChangeEvent evt) {
                            context.removeContextListener(
                                    (RecalcPolicy) evt.getOldValue());
                            context.addContextListener(
                                    (RecalcPolicy) evt.getNewValue());
                        }
                    });
            recalculationPolicy = strategyValueItem;

        }
        return recalculationPolicy;
    }

    private GrowingStrategyModel createRecalcStrategyModel() {
        GrowingStrategyModel strategyModel = new GrowingStrategyModel();
        strategyModel.addStrategy(CLEAR_DEPENDENT_POLICY_KEY,
                "Clear dependent", makeClearDependentRecalcPolicy());
        strategyModel.addStrategy(RECOMPUTE_DEPENDENT_POLICY_KEY,
                "Recalculate dependent", makeRecomputeDependentRecalcPolicy());
        return strategyModel;
    }

    private RecalcPolicy getRecalcPolicy() {
        return (RecalcPolicy) getRecalculationPolicy().getStrategy();
    }

    public void setClearDependentRecalcPolicy() {
        getRecalculationPolicy().setValueByKey(CLEAR_DEPENDENT_POLICY_KEY);
    }

    private RecalcPolicy makeClearDependentRecalcPolicy() {
        return new ClearDependentRecalcPolicy();
    }

    private RecalcPolicy makeRecomputeDependentRecalcPolicy() {
        return new RecomputeDependentRecalcPolicy();
    }

    private AssociationRuleCalculator associationMiner;

    public AssociationRuleCalculator getAssociationMiner() {
        if (null == associationMiner) {
            associationMiner = new AssociationRuleCalculator(getContext());
            //getContext().addContextListener(getAssociationRulesRecalcPolicy());
        }
        return associationMiner;
    }


    public void findAssociations() {
        getAssociationMiner().findDependencies();
        markDirty();
    }

    public DependencySet getAssociationRules() {
        return getAssociationMiner().getDependencySet();
    }


    private ImplicationBaseCalculator implicationBaseCalculator;

    public ImplicationBaseCalculator getImplicationBaseCalculator() {
        if (null == implicationBaseCalculator) {
            implicationBaseCalculator = new ImplicationBaseCalculator(
                    getContext(),
                    getAvailableImplicationStrategiesFactory());
        }
        return implicationBaseCalculator;
    }

    public ImplicationSet getImplications() {
        return getImplicationBaseCalculator().getImplications();
    }

    private static ImplicationCalcStrategyFactory[] getAvailableImplicationStrategiesFactory() {
        return new ImplicationCalcStrategyFactory[]{
                AttributeIncrementalImplicationCalculatorFactory.getInstance(),
                NextClosedSetImplicationCalculatorFactory.getInstance()};
    }

    public void findImplications() {
        getImplicationBaseCalculator().findDependencies();
        markDirty();
    }


    interface RecalcPolicy extends GenericStrategy, ContextListener {
    }

    ;

    public abstract class AbstractRecalcPolicy extends DefaultContextListener
            implements RecalcPolicy {
        public void contextStructureChanged() {
            updateDependent();
        }

        public void contextTransposed() {
            //is left intentionally empty as after
            //contextTransposed always
            //contextStructureChanges is called
        }

        public void relationChanged() {
            updateDependent();
        }

        private void updateDependent() {
            updateLatticeCollection();
            updateDependencySetCalculatorIfRequired(associationMiner);
            updateDependencySetCalculatorIfRequired(implicationBaseCalculator);
        }

        private void updateLatticeCollection() {
            for (Iterator iterator = latticeComponents.iterator();
                 iterator.hasNext();) {
                LatticeComponent latticeComponent = (LatticeComponent) iterator.next();
                updateLatticeComponent(latticeComponent);
            }
        }

        private void updateDependencySetCalculatorIfRequired(
                AbstractDependencySetCalculator dependencySetCalculator) {
            if (null != dependencySetCalculator &&
                    dependencySetCalculator.isComputed()) {
                updateDependencySetCalculator(dependencySetCalculator);
            }
        }

        protected abstract void updateDependencySetCalculator(
                AbstractDependencySetCalculator dependencySetCalculator);

        protected abstract void updateLatticeComponent(
                LatticeComponent latticeComponent);

        public abstract String getName();

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AbstractRecalcPolicy)) {
                return false;
            }
            return getClass().equals(obj.getClass());
        }

        public int hashCode() {
            return getClass().hashCode();
        }
    }


    public class ClearDependentRecalcPolicy extends AbstractRecalcPolicy {

        protected void updateDependencySetCalculator(
                AbstractDependencySetCalculator dependencySetCalculator) {
            dependencySetCalculator.clearDependencySet();
        }

        protected void updateLatticeComponent(
                LatticeComponent latticeComponent) {
            latticeComponent.clearLattice();
        }


        public String getName() {
            return "Clear dependent";
        }
    }

    public class RecomputeDependentRecalcPolicy extends AbstractRecalcPolicy {
        protected void updateDependencySetCalculator(
                AbstractDependencySetCalculator dependencySetCalculator) {
            dependencySetCalculator.findDependencies();
        }

        protected void updateLatticeComponent(
                LatticeComponent latticeComponent) {
            latticeComponent.calculateAndLayoutLattice();
        }

        public String getName() {
            return "Recompute dependent";
        }
    }
}
