package conexp.core.layout;

/**
 * Insert the type's description here.
 * Creation date: (04.06.01 13:55:38)
 * @author
 */
public abstract class NonIncrementalLayouter extends GenericLayouter {
    /**
     * NonIncrementalLayouter constructor comment.
     */
    public NonIncrementalLayouter() {
        super();
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.06.01 13:55:38)
     * @return boolean
     */
    public boolean isDone() {
        return true;
    }


    /**
     * Insert the method's description here.
     * Creation date: (04.06.01 13:55:38)
     * @return boolean
     */
    public boolean isIncremental() {
        return false;
    }
}