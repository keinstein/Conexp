package conexp.core.enumcallbacks;

import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.Set;

public class LatticeBuilderCallback extends DefaultConceptEnumCallback {

    private Lattice lat;
    private LatticeElement last;

    public LatticeBuilderCallback(Lattice _lat) {
        super();
        lat = _lat;
    }

    private LatticeElement addToLatElem(Set obj, Set attr) {
        last = LatticeElement.makeFromSetsCopies(obj, attr);
        lat.addElementSetLinks(last);
        return last;
    }

    /**
     *
     */
    public void finishCalc() {
        if (null != last) {
            lat.setOne(last);
        }
        //*DBG*/lat.printDebugData();
    }

    /**
     *
     * @param obj <description>
     * @param attr <description>
     */
    public void setZeroElement(Set obj, Set attr) {
        last = LatticeElement.makeFromSetsCopies(obj, attr);
        //*DBG*/ System.out.println(last);
        //*DBG*/ System.out.println(lat);
        lat.addElement(last);
        lat.setZero(last);
    }

    /**
     *
     * @param obj <description>
     * @param attr <description>
     * @param j <description>
     */
    public void addConcept(Set obj, Set attr) {
        addToLatElem(obj, attr);
    }
}