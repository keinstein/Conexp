/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.components;

import conexp.core.Context;
import conexp.core.FCAEngineRegistry;
import conexp.core.ItemSet;
import conexp.core.Lattice;
import conexp.core.Set;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.layout.LayouterProvider;
import conexp.core.layoutengines.LayoutEngine;
import conexp.frontend.LatticeCalculator;
import conexp.frontend.SetProvidingEntitiesMask;
import conexp.frontend.latticeeditor.LatticeDrawing;
import util.BasePropertyChangeSupplier;

import java.awt.geom.Point2D;


public class LatticeComponent extends BasePropertyChangeSupplier implements LatticeCalculator, LatticeSupplierAndCalculator {
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
    }

    private void setContextAndMasks(Context cxt, final ContextAttributeMask contextAttributeMask, final ContextObjectMask contextObjectMask) {
        cleanUp();
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

    boolean recalcLatticeOnMaskChange = false;

    public boolean isRecalcLatticeOnMaskChange() {
        return recalcLatticeOnMaskChange;
    }

    public void setUpLatticeRecalcOnMasksChange() {
        recalcLatticeOnMaskChange = true;
        EntityMaskChangeController entityMaskChangeController = new EntityMaskChangeController(this);
        getAttributeMask().addPropertyChangeListener(entityMaskChangeController);
        getObjectMask().addPropertyChangeListener(entityMaskChangeController);
    }


    /**
     * correctly clean ups the lattice component
     * prevents memory leaks when lattice component is being used with several contexts
     */
    public synchronized void cleanUp() {
        context = null;
        if (null != attributeMask) {
            attributeMask.cleanUp();
        }
        if (null != objectMask) {
            objectMask.cleanUp();
        }
        clearLattice();
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
//            lattice.setFeatureMasks(attributeMask.toSet(), objectMask.makeCopy());
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

    public void restorePreferences() {
        getDrawing().restorePreferences();
    }


    public void calculateLattice() {
        Lattice lattice;
        if (getAttributeMask().hasUnselected() || getObjectMask().hasUnselected()) {
            lattice = FCAEngineRegistry.buildPartialLattice(getContext(), getSelectedAttributes(), getSelectedObjects());
        } else {
            lattice = FCAEngineRegistry.buildLattice(getContext());
        }
        doSetLatticeAndFireRecalced(lattice);
    }

    protected void doSetLatticeAndFireRecalced(final Lattice otherLattice) {
        doSetLattice(otherLattice);
        fireLatticeRecalced();

    }

    protected void doSetLattice(final Lattice otherLattice) {
        synchronized (this) {
            lattice = otherLattice;
            getDrawing().setLattice(otherLattice);
        }
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
    public void fireLatticeRecalced() {
        getPropertyChangeSupport().firePropertyChange(LatticeCalculator.LATTICE_DRAWING_CHANGED, null, getDrawing());
    }

    public boolean isEqualByContent(final LatticeComponent latticeComponent) {
        if (null == latticeComponent) {
            return false;
        }
        if (context != null ? !context.equals(latticeComponent.context) : latticeComponent.context != null) {
            return false;
        }
        if (lattice != null ? !lattice.equals(latticeComponent.lattice) : latticeComponent.lattice != null) {
            return false;
        }
        if (objectMask != null ? !objectMask.equals(latticeComponent.objectMask) : latticeComponent.objectMask != null)
        {
            return false;
        }
        if (attributeMask != null ? !attributeMask.equals(latticeComponent.attributeMask) : latticeComponent.attributeMask != null)
        {
            return false;
        }
        if (recalcLatticeOnMaskChange != latticeComponent.recalcLatticeOnMaskChange) {
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

    public synchronized LatticeComponent makeCopy() {
        final LatticeComponent other = new LatticeComponent(this.context, attributeMask.makeCopy(), objectMask.makeCopy());
        if (isRecalcLatticeOnMaskChange()) {
            other.setUpLatticeRecalcOnMasksChange();
        }
        //this code is written is such a way due to need to skip the lazy initialization
        //mechanizom of the
        if (null != latticeDrawing) {
            other.latticeDrawing = latticeDrawing.makeSetupCopy();
        }
        if (null != layoutEngine) {
            other.setLayoutEngine(layoutEngine.newInstance());
        }
        LatticeComponentDuplicatorService.getInstance().duplicateContent(this, other);
/*
        if (lattice != null) {
            other.doSetLattice(getLattice().makeCopy());
            if (null != latticeDrawing) {
                final LatticeDrawing drawing = other.getDrawing();
                copyConceptFigureCoordinatesToDrawing(drawing);
            }
            //what's about foreground figures in the  drawing ?
        }
*/

        return other;
    }

    public void copyConceptFigureCoordinatesToDrawing(final LatticeDrawing drawing) {
        drawing.setCoordinatesFromMapper(new ConceptCoordinateMapper() {
            public void setCoordsForConcept(ItemSet c, Point2D coords) {
                coords.setLocation(latticeDrawing.getFigureForConcept(c).getCenter());
            }
        });
    }
}
