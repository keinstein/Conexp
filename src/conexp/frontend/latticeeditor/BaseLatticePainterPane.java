package conexp.frontend.latticeeditor;

import conexp.core.Lattice;
import conexp.frontend.LatticeDrawingProvider;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 23/7/2003
 * Time: 9:51:29
 */

public class BaseLatticePainterPane extends BaseConceptSetCanvas {
    LatticeDrawingProvider latticeSupplier;

    public BaseLatticePainterPane(LatticeDrawingProvider latticeDrawingProvider) {
        super(latticeDrawingProvider.getDrawing().getPainterOptions());
        setLatticeSupplier(latticeDrawingProvider);
    }

    protected LatticeDrawing getLatticeDrawing() {
        return (LatticeDrawing) getConceptSetDrawing();
    }

    public Lattice getLattice() {
        return getLatticeDrawing().getLattice();
    }

    public LatticeDrawingProvider getLatticeSupplier() {
        return latticeSupplier;
    }

    public void initialUpdate() {
        setConceptSetDrawing(getLatticeSupplier().getDrawing());
    }

    protected void setLatticeSupplier(LatticeDrawingProvider supplier) {
        latticeSupplier = supplier;
        setOptions(latticeSupplier.getDrawing().getOptions());
    }
}
