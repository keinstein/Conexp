package canvas;

/**
 * Insert the type's description here.
 * Creation date: (13.01.01 19:33:37)
 * @author Serhiy Yevtushenko
 */
public interface FigureDrawingListener extends java.util.EventListener {

    void dimensionChanged(java.awt.Dimension newDim);
    void needUpdate();
}