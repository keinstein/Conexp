/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;

import conexp.core.registries.FCAEngineImplementation;

public class FCAEngineRegistry {

    static FCAEngine fcaEngine;

    public static FCAEngine getFcaEngine() {
        if (null == fcaEngine) {
            fcaEngine = new FCAEngineImplementation();
        }
        return fcaEngine;
    }

    public static ArrowCalculator makeArrowCalculator() {
        return getFcaEngine().makeArrowCalculator();
    }

    public static Lattice buildLattice(Context cxt) {
        return getFcaEngine().buildLattice(cxt);
    }

    public static Lattice buildPartialLattice(Context cxt, Set featureMask) {
        return getFcaEngine().buildPartialLattice(cxt, featureMask);
    }

    public static Lattice buildIcebergLattice(Context cxt, int minSupport) {
        return getFcaEngine().buildIcebergLattice(cxt, minSupport);
    }

    public static ConceptsCollection buildIcebergConceptSet(ExtendedContextEditingInterface cxt, int minSupport) {
        return getFcaEngine().buildIcebergConceptSet(cxt, minSupport);
    }

    public static void buildIcebergConceptSet(ExtendedContextEditingInterface cxt, int minSupport, ConceptEnumCallback callback) {
        getFcaEngine().buildIcebergConceptSet(cxt, minSupport, callback);
    }

    public static Lattice makeLatticeForContext(Context cxt) {
        return getFcaEngine().makeLatticeForContext(cxt);
    }

    public static Context makeContext(int rows, int columns) {
        return getFcaEngine().makeContext(rows, columns);
    }

    public static Context makeContext(ModifiableBinaryRelation relation) {
        return getFcaEngine().makeContext(relation);
    }

}
