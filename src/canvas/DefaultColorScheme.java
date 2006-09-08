/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import javax.swing.UIManager;
import java.awt.Color;


public class DefaultColorScheme implements CanvasColorScheme {
    Color edgeColor = Color.black;
    Color nodeFillColor = Color.blue;
    Color nodeColor = Color.white;
    Color nodeBorderColor = Color.black;
    Color textColor = Color.black;
    Color highlightColor = Color.blue;
    Color collisionColor = Color.red;

    public DefaultColorScheme() {
        super();
    }


    public Color getEdgeColor() {
        return edgeColor;
    }

    public Color getNodeFillColor() {
        return nodeFillColor;
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

    public Color getCollisionColor() {
        return collisionColor;
    }

    public Color getSelectedTextBackground() {
        return UIManager.getColor("TextField.selectionBackground");
    }

    public CanvasColorScheme makeCopy() {
        final DefaultColorScheme ret = new DefaultColorScheme();
        ret.edgeColor = edgeColor;
        ret.nodeFillColor = nodeFillColor;
        ret.nodeColor = nodeColor;
        ret.nodeBorderColor = nodeBorderColor;
        ret.textColor = textColor;
        ret.highlightColor = highlightColor;
        ret.collisionColor = collisionColor;
        return ret;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DefaultColorScheme)) {
            return false;
        }

        final DefaultColorScheme defaultColorScheme = (DefaultColorScheme) obj;

        if (collisionColor != null ? !collisionColor.equals(defaultColorScheme.collisionColor) : defaultColorScheme.collisionColor != null)
        {
            return false;
        }
        if (edgeColor != null ? !edgeColor.equals(defaultColorScheme.edgeColor) : defaultColorScheme.edgeColor != null)
        {
            return false;
        }
        if (highlightColor != null ? !highlightColor.equals(defaultColorScheme.highlightColor) : defaultColorScheme.highlightColor != null)
        {
            return false;
        }
        if (nodeBorderColor != null ? !nodeBorderColor.equals(defaultColorScheme.nodeBorderColor) : defaultColorScheme.nodeBorderColor != null)
        {
            return false;
        }
        if (nodeColor != null ? !nodeColor.equals(defaultColorScheme.nodeColor) : defaultColorScheme.nodeColor != null)
        {
            return false;
        }
        if (nodeFillColor != null ? !nodeFillColor.equals(defaultColorScheme.nodeFillColor) : defaultColorScheme.nodeFillColor != null)
        {
            return false;
        }
        if (textColor != null ? !textColor.equals(defaultColorScheme.textColor) : defaultColorScheme.textColor != null)
        {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = edgeColor != null ? edgeColor.hashCode() : 0;
        result = 29 * result + (nodeFillColor != null ? nodeFillColor.hashCode() : 0);
        result = 29 * result + (nodeColor != null ? nodeColor.hashCode() : 0);
        result = 29 * result + (nodeBorderColor != null ? nodeBorderColor.hashCode() : 0);
        result = 29 * result + (textColor != null ? textColor.hashCode() : 0);
        result = 29 * result + (highlightColor != null ? highlightColor.hashCode() : 0);
        result = 29 * result + (collisionColor != null ? collisionColor.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "DefaultColorScheme{" +
                "edgeColor=" + edgeColor +
                ", nodeFillColor=" + nodeFillColor +
                ", nodeColor=" + nodeColor +
                ", nodeBorderColor=" + nodeBorderColor +
                ", textColor=" + textColor +
                ", highlightColor=" + highlightColor +
                ", collisionColor=" + collisionColor +
                '}';
    }
}
