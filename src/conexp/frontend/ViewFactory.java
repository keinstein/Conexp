package conexp.frontend;

/**
 * Insert the type's description here.
 * Creation date: (14.05.2001 14:06:02)
 * @author
 */
public interface ViewFactory {

    /**
     * Insert the method's description here.
     * Creation date: (14.05.2001 14:06:53)
     * @return javax.swing.JComponent
     * @param viewType java.lang.String
     */
    View makeView(String viewType);
}