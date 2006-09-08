/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

public interface FCAEngine {
    ArrowCalculator makeArrowCalculator();

    Context makeContext(int rows, int columns);

    Context makeContext(ModifiableBinaryRelation relation);

    Lattice makeLatticeForContext(Context cxt);

    Lattice buildLattice(Context cxt);

    ConceptsCollection buildConceptSet(Context cxt);

    Lattice buildPartialLattice(Context cxt, Set attributeMask, Set objectsMask);

    Lattice buildIcebergLattice(Context cxt, int minSupport);

    ConceptsCollection buildIcebergConceptSet(ExtendedContextEditingInterface cxt, int minSupport);

    void buildIcebergConceptSet(ExtendedContextEditingInterface cxt, int minSupport, ConceptEnumCallback callback);
}
