/*
 * Date: Feb 26, 2002
 * Time: 12:48:53 AM
 */
package conexp.frontend.latticeeditor;

import conexp.core.Lattice;
import conexp.core.layout.LayouterProvider;
import conexp.core.layoutengines.LayoutListener;

public interface LayoutEngine {
    void init(LayouterProvider provider);
    void shutdown();
    void startLayout(Lattice lattice, DrawParameters drawParameters);
    void addLayoutListener(LayoutListener listener);
    void removeLayoutListener(LayoutListener listener);
}
