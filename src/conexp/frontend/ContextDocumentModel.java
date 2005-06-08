package conexp.frontend;

import com.gargoylesoftware.base.collections.NotificationList;
import com.gargoylesoftware.base.collections.NotificationListListener;
import conexp.core.*;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import conexp.core.enumcallbacks.ConceptNumCallback;
import conexp.frontend.components.LatticeComponent;
import conexp.frontend.components.LatticeSupplier;
import util.collection.CollectionFactory;

import java.util.Collection;
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

    public ContextDocumentModel(Context cxt) {
        setContext(cxt);
    }

    public void setContext(Context cxt) {
        cleanUpListeners();
        doResetLatticeComponents();

        this.context = cxt;
        cxt.setArrowCalculator(FCAEngineRegistry.makeArrowCalculator());
        cxt.addContextListener(getLatticeRecalcPolicy());
    }

    private void cleanUpListeners() {
        if (this.context != null) {
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
        return latticeComponents.size()-1;
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

    public void removeLatticeComponent(LatticeSupplier component){
        latticeComponents.remove(component);
    }


}
