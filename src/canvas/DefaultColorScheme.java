package canvas;

import javax.swing.*;
import java.awt.Color;

/**
 * Insert the type's description here.
 * Creation date: (06.10.00 23:00:52)
 * @author
 */
public class DefaultColorScheme implements CanvasColorScheme{
    Color edgeColor = Color.black;
    Color fillColor = Color.blue;
    Color nodeColor = Color.white;
    Color nodeBorderColor = Color.black;
    Color textColor = Color.black;
    Color highlightColor = Color.blue;

    public DefaultColorScheme() {
        super();
    }


    public Color getEdgeColor() {
        return edgeColor;
    }

    public Color getNodeFillColor() {
        return fillColor;
    }

    public Color getHighlightColor() {
        return highlightColor;
    }

    public Color getNodeBorderColor() {
        return nodeBorderColor;
    }

    public Color getNodeColor() {
        return nodeColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Color getTextBackColor() {
        return Color.white;
    }

    public Color getSelectedTextBackground() {
        return UIManager.getColor("TextField.selectionBackground");
    }
}