/*
 * Date: Feb 27, 2002
 * Time: 3:13:04 PM
 */
package canvas;

import java.awt.Color;

public interface CanvasColorScheme {
    Color getTextBackColor();

    Color getNodeBorderColor();

    Color getNodeColor();

    Color getEdgeColor();

    Color getNodeFillColor();

    Color getHighlightColor();

    Color getTextColor();

    Color getSelectedTextBackground();
}
