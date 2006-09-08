package conexp.frontend.components;

/**
 * User: sergey
 * Date: 23/5/2005
 * Time: 16:30:28
 */
public class LatticeComponentDuplicatorService {
    static ILatticeComponentDuplicator ourInstance = new LatticeComponentSerializationDuplicator();

    public static ILatticeComponentDuplicator getInstance() {
        return ourInstance;
    }

}
