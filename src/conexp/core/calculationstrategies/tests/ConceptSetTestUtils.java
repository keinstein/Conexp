package conexp.core.calculationstrategies.tests;

import com.mockobjects.ExpectationSet;
import conexp.core.ConceptsCollection;
import conexp.core.Lattice;
import conexp.core.LatticeElement;

/**
 * Insert the type's description here.
 * Creation date: (02.07.01 19:56:10)
 * @author
 */
public class ConceptSetTestUtils {
    /**
     * ConceptSetTestUtils constructor comment.
     */
    public ConceptSetTestUtils() {
        super();
    }


    /**
     * Insert the method's description here.
     * Creation date: (02.07.01 0:19:10)
     */
    public static void fillExpectationSetByEdgesFromLattice(ExpectationSet expSet, Lattice lat) {
        for (int i = lat.conceptsCount(); --i >= 0;) {
            LatticeElement el = lat.elementAt(i);
            for (int j = el.getPredCount(); --j >= 0;) {
                expSet.addActual(el.getPred(j));
            }
        }
    }

    public static void fillExpectationSetByExtentsFromLattice(ExpectationSet expSet, ConceptsCollection conceptSet) {
        for (int i = conceptSet.conceptsCount(); --i >= 0;) {
            expSet.addActual(conceptSet.conceptAt(i).getObjects());
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (02.07.01 0:19:10)
     */
    public static void fillExpectationSetByIntentsFromLattice(ExpectationSet expSet, ConceptsCollection conceptSet) {
        for (int i = conceptSet.conceptsCount(); --i >= 0;) {
            expSet.addActual(conceptSet.conceptAt(i).getAttribs());
        }
    }
}