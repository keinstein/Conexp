package conexp.core.layout.layeredlayout;

import conexp.core.layout.layeredlayout.IEvaluationFunction;
import conexp.core.layout.ConceptCoordinateMapper;
import conexp.core.Lattice;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public abstract class LatticeBasedEvaluationFunctionBase implements IEvaluationFunction {
    protected Lattice lattice;
    protected ConceptCoordinateMapper conceptCoordinateMapper;

    public LatticeBasedEvaluationFunctionBase() {
    }

    public LatticeBasedEvaluationFunctionBase(Lattice lattice, ConceptCoordinateMapper conceptCoordinateMapper) {
        this.lattice = lattice;
        this.conceptCoordinateMapper = conceptCoordinateMapper;
    }

    public void setLattice(Lattice lattice) {
        this.lattice = lattice;
    }

    public void setConceptCoordinateMapper(ConceptCoordinateMapper conceptCoordinateMapper) {
        this.conceptCoordinateMapper = conceptCoordinateMapper;
    }

    //the bigger value of evaluation function is better
    public abstract double getEvaluationForLattice();
}
