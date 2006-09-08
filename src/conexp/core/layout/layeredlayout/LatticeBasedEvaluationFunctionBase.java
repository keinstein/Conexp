/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout;

import conexp.core.Lattice;
import conexp.core.layout.ConceptCoordinateMapper;


public abstract class LatticeBasedEvaluationFunctionBase implements IEvaluationFunction {
    protected Lattice lattice;
    protected ConceptCoordinateMapper conceptCoordinateMapper;

    protected LatticeBasedEvaluationFunctionBase() {
    }

    protected LatticeBasedEvaluationFunctionBase(Lattice lattice, ConceptCoordinateMapper conceptCoordinateMapper) {
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
