/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor;

import conexp.core.Lattice;
import conexp.frontend.LatticeDrawingProvider;



public class BaseLatticePainterPane extends BaseConceptSetCanvas {
    private LatticeDrawingProvider latticeSupplier;

    public BaseLatticePainterPane() {
        super();
    }

    protected LatticeDrawing getLatticeDrawing() {
        return (LatticeDrawing) getConceptSetDrawing();
    }

    public Lattice getLattice() {
        return getLatticeDrawing().getLattice();
    }

    public void setLatticeSupplier(LatticeDrawingProvider latticeSupplier) {
        this.latticeSupplier = latticeSupplier;
        setDrawingFromSupplier();
    }

    public LatticeDrawingProvider getLatticeSupplier() {
        return latticeSupplier;
    }

    public void initialUpdate() {


    }

    private void setDrawingFromSupplier() {
        setConceptSetDrawing(getLatticeSupplier().getDrawing());
    }

}
