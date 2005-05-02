/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.components;

import conexp.core.*;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.LayouterProvider;
import conexp.core.layoutengines.LayoutEngine;
import conexp.frontend.LatticeCalculator;
import conexp.frontend.LatticeDrawingProvider;
import conexp.frontend.SetProvidingEntitiesMask;
import conexp.frontend.latticeeditor.LatticeDrawing;
import util.BasePropertyChangeSupplier;

import java.awt.geom.Point2D;


public class LatticeComponent extends BasePropertyChangeSupplier implements LatticeDrawingProvider, LatticeCalculator {
    protected Context context;
    protected LatticeDrawing latticeDrawing;
    protected ContextAttributeMask attributeMask;
    protected ContextObjectMask objectMask;
    protected Lattice lattice;
    LayoutEngine layoutEngine;

    public LatticeComponent() {
        this(FCAEngineRegistry.makeContext(0, 0));
    }

    public LatticeComponent(Context cxt) {
        setContext(cxt);
    }

    protected LatticeComponent(Context cxt, ContextAttributeMask attributeMask, ContextObjectMask objectMask) {
        setContextAndMasks(cxt, attributeMask, objectMask);
    }

    public void setContext(Context cxt) {
        setContextAndMasks(cxt, new ContextAttributeMask(cxt), new ContextObjectMask(cxt));
        clearLattice();
    }

    private void setContextAndMasks(Context cxt, final ContextAttributeMask contextAttributeMask, final ContextObjectMask contextObjectMask) {
        this.context = cxt;
        attributeMask = contextAttributeMask;
        objectMask = contextObjectMask;
    }

    public SetProvidingEntitiesMask getAttributeMask() {
        return attributeMask;
    }

    public SetProvidingEntitiesMask getObjectMask() {
        return objectMask;
    }

    protected Context getContext() {
        return context;
    }

    public synchronized void clearLattice() {
        lattice = null;
        fireLatticeCleared();
    }

    public boolean isEmpty() {
        return getLattice().isEmpty();
    }

    public synchronized Lattice getLattice() {
        if (null == lattice) {
            lattice = new Lattice();
        }
        return lattice;
    }

    public void setLayoutEngine(LayoutEngine layoutEngine) {
        this.layoutEngine = layoutEngine;
        getDrawing().setLayoutEngine(layoutEngine);
    }

    public void setLayouterProvider(LayouterProvider layouterProvider) {
        getDrawing().setLayoutProvider(layouterProvider);
    }

    public LatticeDrawing getDrawing() {
        if (null == latticeDrawing) {
            latticeDrawing = new LatticeDrawing();
            latticeDrawing.setLattice(getLattice());
            if (null != layoutEngine) {
                latticeDrawing.setLayoutEngine(layoutEngine);
            }
        }
        return latticeDrawing;
    }

    public void calculateLattice() {
        doSetLatticeAndFireRecalced(FCAEngineRegistry.buildLattice(getContext()));
    }

    protected void doSetLatticeAndFireRecalced(final Lattice otherLattice) {
        synchronized (this) {
            lattice = otherLattice;
            getDrawing().setLattice(otherLattice);
        }
        fireLatticeRecalced();

    }

    public void calculatePartialLattice() {
        doSetLatticeAndFireRecalced(FCAEngineRegistry.buildPartialLattice(getContext(), getSelectedAttributes(), getSelectedObjects()));
    }

    public void calculateAndLayoutPartialLattice() {
        calculatePartialLattice();
        getDrawing().layoutLattice();
    }

    private Set getSelectedAttributes() {
        return attributeMask.toSet();
    }

    private Set getSelectedObjects() {
        return objectMask.toSet();
    }


    public void calculateAndLayoutLattice() {
        calculateLattice();
        getDrawing().layoutLattice();
    }

    //------------------------------------------------------------
    protected void fireLatticeCleared() {
        getPropertyChangeSupport().firePropertyChange(LatticeCalculator.LATTICE_CLEARED, null, null);
    }

    //------------------------------------------------------------
    protected void fireLatticeRecalced() {
        getPropertyChangeSupport().firePropertyChange(LatticeCalculator.LATTICE_DRAWING_CHANGED, null, getDrawing());
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof LatticeComponent)) {
            return false;
        }

        final LatticeComponent latticeComponent = (LatticeComponent) other;

        if (attributeMask != null ? !attributeMask.equals(latticeComponent.attributeMask) : latticeComponent.attributeMask != null) {
            return false;
        }
        if (context != null ? !context.equals(latticeComponent.context) : latticeComponent.context != null) {
            return false;
        }
        if (lattice != null ? !lattice.equals(latticeComponent.lattice) : latticeComponent.lattice != null) {
            return false;
        }
        if (objectMask != null ? !objectMask.equals(latticeComponent.objectMask) : latticeComponent.objectMask != null) {
            return false;
        }

        if (layoutEngine != null) {
            if (latticeComponent.layoutEngine != null) {
                if (!layoutEngine.getClass().equals(latticeComponent.getClass())) {
                    return false;
                }
            } else {
                return false;
            }
        } else if (latticeComponent.layoutEngine != null) {
            return false;
        }

/*
        if (latticeDrawing != null ? !latticeDrawing.equals(latticeComponent.latticeDrawing) : latticeComponent.latticeDrawing != null) {
            return false;
        }
*/

        return true;
    }

    public int hashCode() {
        int result = attributeMask != null ? attributeMask.hashCode() : 0;
        result = 29 * result + (objectMask != null ? objectMask.hashCode() : 0);
        return result;
    }

    public synchronized LatticeComponent makeCopy() {
        final LatticeComponent other = new LatticeComponent(this.context, attributeMask.makeCopy(), objectMask.makeCopy());
        if (null != latticeDrawing) {
            other.latticeDrawing = latticeDrawing.makeSetupCopy();
        }
        if (null != layoutEngine) {
            other.setLayoutEngine(layoutEngine.newInstance());
        }
        if (lattice != null) {
            other.doSetLatticeAndFireRecalced(getLattice().makeCopy());
            if (null != latticeDrawing) {
                other.getDrawing().setCoordinatesFromMapper(new ConceptCoordinateMapper() {
                    public void setCoordsForConcept(ItemSet c, Point2D coords) {
                        coords.setLocation(latticeDrawing.getFigureForConcept(c).getCenter());
                    }
                });
            }
        }

        return other;
    }
}
