/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.components;

import conexp.core.Context;
import conexp.core.FCAEngineRegistry;
import conexp.core.Lattice;
import conexp.core.Set;
import conexp.core.layout.LayouterProvider;
import conexp.frontend.LatticeCalculator;
import conexp.frontend.LatticeDrawingProvider;
import conexp.frontend.SetProvidingAttributeMask;
import conexp.frontend.latticeeditor.LatticeDrawing;
import conexp.frontend.latticeeditor.LayoutEngine;
import util.BasePropertyChangeSupplier;


public class LatticeComponent extends BasePropertyChangeSupplier implements LatticeDrawingProvider, LatticeCalculator {
    protected Context context;
    protected LatticeDrawing latticeDrawing;
    protected ContextAttributeMask attributeMask;
    protected Lattice lattice;

    public LatticeComponent() {
        this(FCAEngineRegistry.makeContext(0, 0));
    }

    public LatticeComponent(Context cxt) {
        setContext(cxt);
    }

    public void setContext(Context cxt) {
        this.context = cxt;
        attributeMask = new ContextAttributeMask(cxt);
        clearLattice();
    }

    public SetProvidingAttributeMask getAttributeMask() {
        return attributeMask;
    }

    protected Context getContext() {
        return context;
    }

    public void clearLattice() {
        lattice = null;
        fireLatticeCleared();
    }

    public boolean isEmpty() {
        return getLattice().isEmpty();
    }

    public synchronized Lattice getLattice() {
        if (null == lattice) {
            createNewLattice();
        }
        return lattice;
    }

    private void createNewLattice() {
        lattice = new Lattice();
    }

    LayoutEngine layoutEngine;

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
        lattice = FCAEngineRegistry.buildLattice(getContext());
        getDrawing().setLattice(getLattice());
        fireLatticeRecalced();
    }

    public void calculatePartialLattice() {
        lattice = FCAEngineRegistry.buildPartialLattice(getContext(), getSelectedFeaturesSet());
        getDrawing().setLattice(getLattice());
        fireLatticeRecalced();
    }

    public void calculateAndLayoutPartialLattice() {
        calculatePartialLattice();
        getDrawing().layoutLattice();
    }

    private Set getSelectedFeaturesSet() {
        return attributeMask.toSet();
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

}
