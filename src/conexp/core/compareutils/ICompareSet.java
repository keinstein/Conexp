package conexp.core.compareutils;

/**
 * Insert the type's description here.
 * Creation date: (13.07.01 19:04:22)
 * @author
 */
public interface ICompareSet {

    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 19:08:13)
     * @return conexp.core.compareutils.KeyValuePair
     * @param index int
     */
    KeyValuePair get(int index);


    /**
     * Insert the method's description here.
     * Creation date: (13.07.01 19:04:49)
     * @return int
     */
    int getSize();
}