/*
 * User: Serhiy Yevtushenko
 * Date: Oct 21, 2002
 * Time: 12:14:23 PM
 */

package canvas.figures;

import java.awt.Color;

public interface ColorTransformer {
    Color getColor(Color selectionColor, Color normalColor, boolean isSelected, boolean hasSelection);
}
