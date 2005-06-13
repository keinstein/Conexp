package conexp.frontend;

import com.gargoylesoftware.base.collections.NotificationList;
import com.gargoylesoftware.base.collections.NotificationListEvent;
import com.gargoylesoftware.base.collections.NotificationListListener;
import conexp.core.*;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import conexp.core.enumcallbacks.ConceptNumCallback;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.LatticeSupplier;
import conexp.frontend.ruleview.*;
import util.collection.CollectionFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * User: sergey
 * Date: 18/4/2005
 * Time: 21:10:14
 */
public class ContextDocumentModel {
    private Context context;
    private ContextModifiedListener contextModifiedListener;
    private PropertyChangeListener latticeComponentListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent evt) {
            final String propertyName = evt.getPropertyName();
            if (LatticeCalculator.LATTICE_DRAWING_CHANGED.equals(propertyName) ||
                    LatticeCalculator.LATTICE_CLEARED.equals(propertyName)) {
                markDirty();
            }
        }
    };

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

    public void setContext(Context cxt) {
        cleanUpListeners();
        doResetLatticeComponents();

        this.context = cxt;
        cxt.setArrowCalculator(FCAEngineRegistry.makeArrowCalculator());
        cxt.addContextListener(getContextModifiedListener());
        cxt.addContextListener(getLatticeRecalcPolicy());
    }


    private void cleanUpListeners() {
        if (this.context != null) {
            this.context.removeContextListener(getContextModifiedListener());
            this.context.removeContextListener(getLatticeRecalcPolicy());
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

    private NotificationList latticeComponents = new NotificationList(CollectionFactory.createDefaultList());

    public void addLatticeComponentsListener(NotificationListListener listener) {
        latticeComponents.addNotificationListListener(listener);
    }

    public void removeLatticeComponentsListener(NotificationListListener listener) {
        latticeComponents.removeNotificationListListener(listener);
    }

    public List getLatticeComponents() {
        return Collections.unmodifiableList(latticeComponents);
    }

    private LatticeSupplier makeLatticeComponentForDoc() {
        final LatticeComponent result = LatticeComponentFactory.makeLatticeComponent(getContext());
        result.addPropertyChangeListener(latticeComponentListener);
        result.setUpLatticeRecalcOnMasksChange();
        result.restorePreferences();
        return result;
    }

    private ContextListener latticeContextListener;

    private ContextListener getLatticeRecalcPolicy() {
        if (null == latticeContextListener) {
            latticeContextListener = new DefaultContextListener() {
                public void contextStructureChanged() {
                    clearLattices();
                }

                public void relationChanged() {
                    clearLattices();
                }

            };
        }
        return latticeContextListener;
    }

    private ContextListener getContextModifiedListener() {
        if (null == contextModifiedListener) {
            contextModifiedListener = new ContextModifiedListener();
        }
        return contextModifiedListener;
    }


    private void clearLattices() {
        for (Iterator iterator = latticeComponents.iterator(); iterator.hasNext();) {
            LatticeComponent latticeComponent = (LatticeComponent) iterator.next();
            latticeComponent.clearLattice();
        }
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
        for (Iterator iterator = latticeComponents.iterator(); iterator.hasNext();) {
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

    boolean modified;

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

    private AssociationRuleCalculator associationMiner;

    public AssociationRuleCalculator getAssociationMiner() {
        if (null == associationMiner) {
            associationMiner = new AssociationRuleCalculator(getContext());
            getContext().addContextListener(getAssociationRulesRecalcPolicy());
        }
        return associationMiner;
    }

    private ContextListener associationsContextListener;


    private ContextListener getAssociationRulesRecalcPolicy() {
        if (null == associationsContextListener) {
            associationsContextListener = new DependencySetRecalcPolicy(getAssociationMiner());
        }
        return associationsContextListener;
    }

    public void findAssociations() {
        getAssociationMiner().findDependencies();
        markDirty();
    }

    public DependencySet getAssociationRules() {
        return getAssociationMiner().getDependencySet();
    }

    static class DependencySetRecalcPolicy extends DefaultContextListener {
        final DependencySetCalculator supplier;

        public DependencySetRecalcPolicy(DependencySetCalculator supplier) {
            this.supplier = supplier;
        }

        public void contextStructureChanged() {
            supplier.clearDependencySet();
        }

        public void relationChanged() {
            supplier.clearDependencySet();
        }
    }

    public void findImplications(){
        getImplicationBaseCalculator().findDependencies();
        markDirty();
    }

    public ImplicationBaseCalculator getImplicationBaseCalculator() {
        if (null == implicationBaseCalculator) {
            implicationBaseCalculator = new ImplicationBaseCalculator(
                    getContext(),
                    getAvailableImplicationStrategiesFactory());
            getContext().addContextListener(getImplicationBaseRecalcPolicy());

        }
        return implicationBaseCalculator;
    }

    private ImplicationBaseCalculator implicationBaseCalculator;


    private static ImplicationCalcStrategyFactory[] getAvailableImplicationStrategiesFactory() {
        return new ImplicationCalcStrategyFactory[]{
            AttributeIncrementalImplicationCalculatorFactory.getInstance(),
            NextClosedSetImplicationCalculatorFactory.getInstance()};
    }

    private ContextListener implicationBaseRecalcPolicy;

    private ContextListener getImplicationBaseRecalcPolicy() {
        if (null == implicationBaseRecalcPolicy) {
            implicationBaseRecalcPolicy =
                    new ContextDocumentModel.DependencySetRecalcPolicy(
                            getImplicationBaseCalculator());
        }
        return implicationBaseRecalcPolicy;
    }

}
