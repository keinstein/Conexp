package conexp.core;

/**
 * Insert the type's description here.
 * Creation date: (26.07.01 0:17:34)
 * @author
 */
public class VirtualLatticeElement extends LatticeElement {
    /**
     * VirtualLatticeElement constructor comment.
     */
    VirtualLatticeElement() {
        super(ContextFactoryRegistry.createSet(0), ContextFactoryRegistry.createSet(0));
    }


//----------------------------------------------
    public boolean isVirtual() {
        return true;
    }
}