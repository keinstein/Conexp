package conexp.core.compareutils;

/**
 * Insert the type's description here.
 * Creation date: (13.07.01 18:40:40)
 * @author
 */
public class LatticeElementCompareInfoFactory implements CompareInfoFactory {
    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 18:40:40)
     * @param obj java.lang.Object
     * @param type int
     */
    public CompareInfo makeCompareInfo(Object obj, int type) {
        return new LatticeElementCompareInfo(obj, type);
    }
}