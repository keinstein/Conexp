package conexp.frontend;

import com.gargoylesoftware.base.collections.NotificationList;
import com.gargoylesoftware.base.collections.NotificationListListener;
import conexp.core.*;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import conexp.core.enumcallbacks.ConceptNumCallback;
import conexp.frontend.components.LatticeComponent;
import util.collection.CollectionFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * User: sergey
 * Date: 18/4/2005
 * Time: 21:10:14
 */
public class ContextDocumentModel {
    private Context context;

    ContextDocumentModel(Context cxt) {
        setContext(cxt);
    }

    public void setContext(Context cxt) {
        this.context = cxt;
        cxt.setArrowCalculator(FCAEngineRegistry.makeArrowCalculator());
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

    public void removeLatticeComponentsLisneter(NotificationListListener listener) {
        latticeComponents.removeNotificationListListener(listener);
    }

    public Collection getLatticeComponents() {
        return Collections.unmodifiableList(latticeComponents);
    }

    private LatticeComponent makeLatticeComponentForDoc() {
        return new LatticeComponent(getContext());
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


    public LatticeComponent getLatticeComponent(int index) {
        if (index < latticeComponents.size()) {
            return (LatticeComponent) latticeComponents.get(index);
        } else if (index == 0) { //first component is created by default
            LatticeComponent ret = makeLatticeComponentForDoc();
            ret.getDrawing().restorePreferences();
            getContext().addContextListener(getLatticeRecalcPolicy());
            latticeComponents.add(ret);
            return ret;
        } else {
            throw new IndexOutOfBoundsException("Bad request for lattice component");
        }
    }

    public void resetLatticeComponent() {
        latticeComponents.clear();
        //todo: check for cleaning up listeners
        latticeComponents.add(makeLatticeComponentForDoc());
    }

    public void makeLatticeSnapshot(int index) {
        LatticeComponent old = getLatticeComponent(index);
        LatticeComponent newComponent = old.makeCopy();
        latticeComponents.add(newComponent);
    }
}
