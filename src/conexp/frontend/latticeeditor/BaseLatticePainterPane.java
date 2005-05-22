package conexp.frontend.latticeeditor;

import conexp.core.Lattice;
import conexp.frontend.LatticeDrawingProvider;
import canvas.DefaultCanvasScheme;
import canvas.CanvasScheme;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 23/7/2003
 * Time: 9:51:29
 */

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
        setDrawingFromSupplier();
    }

    private void setDrawingFromSupplier() {
        setConceptSetDrawing(getLatticeSupplier().getDrawing());
    }

}
