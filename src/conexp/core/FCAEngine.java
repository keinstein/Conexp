/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Jan 7, 2002
 * Time: 9:12:16 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core;

public interface FCAEngine {
    ArrowCalculator makeArrowCalculator();
    Context makeContext(int rows, int columns);
    Context makeContext(ModifiableBinaryRelation relation);

    Lattice makeLatticeForContext(Context cxt);
    Lattice buildLattice(Context cxt);
    Lattice buildPartialLattice(Context cxt, Set featureMask);
    Lattice buildIcebergLattice(Context cxt, int minSupport);

    ConceptsCollection buildIcebergConceptSet(ExtendedContextEditingInterface cxt, int minSupport);

    void buildIcebergConceptSet(ExtendedContextEditingInterface cxt, int minSupport, ConceptEnumCallback callback);
}
