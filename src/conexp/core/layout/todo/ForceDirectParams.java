package conexp.core.layout.todo;

/**
 * Insert the type's description here.
 * Creation date: (03.03.01 18:54:34)
 * @author
 */
public class ForceDirectParams {
    protected int D = 20;
    private conexp.core.Lattice lattice;

    /**
     * ForceDirectParams constructor comment.
     */
    public ForceDirectParams() {
        super();
    }

    /**
     * Insert the method's description here.
     * Creation date: (03.03.01 19:01:55)
     * @return double
     */
    public static double ac() {
        return 1.0;
    }

    /**
     * Insert the method's description here.
     * Creation date: (03.03.01 19:17:25)
     * @return double
     */
    public static double ae() {
        return 1.0;
    }

    /**
     * Insert the method's description here.
     * Creation date: (03.03.01 18:56:32)
     * @return double
     */
    public double k() {
        return D * Math.sqrt(lattice.conceptsCount());
    }

    /**
     * Insert the method's description here.
     * Creation date: (03.03.01 19:59:48)
     * @return double
     */
    public static double minTemp() {
        //from 0 to 10
        return 0;
    }

    /**
     * Insert the method's description here.
     * Creation date: (03.03.01 19:17:46)
     * @return double
     */
    public static double rc() {
        return 3.0;
    }

    /**
     * Insert the method's description here.
     * Creation date: (03.03.01 19:18:45)
     * @return double
     */
    public static double re() {
        return 2.0;
    }

    /**
     * Insert the method's description here.
     * Creation date: (11.03.01 2:43:42)
     * @param newLattice conexp.core.Lattice
     */
    public void setLattice(conexp.core.Lattice newLattice) {
        lattice = newLattice;
    }
}