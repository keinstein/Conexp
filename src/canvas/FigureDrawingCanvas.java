/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas;

import canvas.tools.SelectionTool;
import com.visibleworkings.trace.Trace;
import util.Assert;
import util.StringUtil;
import util.collection.CollectionFactory;
import util.collection.IndexedSet;
import util.gui.GraphicObjectsFactory;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class FigureDrawingCanvas extends ZoomableCanvas {
    public static final String SELECTION_PROPERTY = "Selection";
    public static final String DRAWING="Drawing";


    private CanvasFigureDrawingListener figureDrawingChangeListener = new CanvasFigureDrawingListener();
    protected FigureDrawing drawing;


    Point2D viewPoint = GraphicObjectsFactory.makePoint2D(0, 0);

    public FigureDrawingCanvas() {
        translatingTransform = makeTranslatingTransform(viewPoint);

        setActiveTool(getDefaultTool());

        MouseHandler mouseHandler = new MouseHandler();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    private PaintBlock paintBlock;

    protected void setFigureDrawing(FigureDrawing drawing) {
        setDrawing(drawing);
        clearSelection();
        repaint();
    }

    protected Dimension getDrawingDimension() {
        if (null == drawing) {
            return getSize();
        }
        return getDrawing().getDimension();
    }

    PropertyChangeListener drawingBoundsChangeListener = new PropertyChangeListener() {
        public void propertyChange(PropertyChangeEvent evt) {
            Rectangle2D newBounds = (Rectangle2D) evt.getNewValue();
            updateTranslatingTransform(newBounds);
        }
    };

    private void updateTranslatingTransform(Rectangle2D newBounds) {
        viewPoint = GraphicObjectsFactory.makePoint2D(newBounds.getX(), newBounds.getY());
        setTranslatingTransform(makeTranslatingTransform(viewPoint));
    }


    private java.awt.geom.AffineTransform translatingTransform;

    protected AffineTransform makeTranslatingTransform(Point2D viewPoint) {
        return AffineTransform.getTranslateInstance(-viewPoint.getX(), -viewPoint.getY());
    }


    protected void setTranslatingTransform(AffineTransform newTransform) {
        if (null == translatingTransform || !translatingTransform.equals(newTransform)) {
            translatingTransform = newTransform;
            repaint();
        }
    }

    public Point2D getUntranslatedCoords(Point2D clientCoords) {
        Point2D ptDest = GraphicObjectsFactory.makePoint2D();
        try {
            translatingTransform.inverseTransform(clientCoords, ptDest);
        } catch (java.awt.geom.NoninvertibleTransformException ex) {
            Trace.gui.errorm(StringUtil.stackTraceToString(ex));
            ptDest = clientCoords;
        }
        return ptDest;
    }

    protected AffineTransform getPaintTransform(AffineTransform scaleTransform) {
        AffineTransform toApply = (AffineTransform) scaleTransform.clone();
        toApply.concatenate(translatingTransform);
        return toApply;
    }


    protected void setDrawing(FigureDrawing newDrawing) {
        if (null != this.drawing) {
            this.drawing.removeDrawingChangedListener(figureDrawingChangeListener);
            this.drawing.removePropertyChangeListener(FigureDrawing.BOUNDS_BOX_PROPERTY, drawingBoundsChangeListener);
        }
        FigureDrawing oldDrawing = this.drawing;
        this.drawing = newDrawing;
        //todo: something is wrong with options setting sequence
        //fix this
        newDrawing.setOptions(getOptions());

        newDrawing.addDrawingChangedListener(figureDrawingChangeListener);
        newDrawing.addPropertyChangeListener(FigureDrawing.BOUNDS_BOX_PROPERTY, drawingBoundsChangeListener);
        updateTranslatingTransform(newDrawing.getUserBoundsRect());
        drawingDimensionChanged(getDrawingDimension());
        firePropertyChange(DRAWING, oldDrawing, this.drawing);
    }

    public FigureDrawing getDrawing() {
        if (null == drawing) {
            makeDrawing();
            Assert.isTrue(null != drawing);
            setDrawing(drawing);
        }
        return drawing;
    }

    protected void makeDrawing() {
        setDrawing(new FigureDrawing());
    }

    protected void clearDrawing() {
        getDrawing().clear();
    }

    public Figure findFigureInReverseOrderToDrawing(double x, double y) {
        return getDrawing().findFigureInReverseOrder(x, y);
    }

    public Point2D getWorldCoords(Point srcPoint) {
        return getUntranslatedCoords(super.getWorldCoords(srcPoint));
    }


    private void drawBlankImage(Graphics g, Dimension d) {
        setBackground(Color.white);
        g.setColor(getBackground());
        g.fillRect(0, 0, d.width, d.height);
    }


    private CanvasScheme options = new DefaultCanvasScheme();

    public void setOptions(CanvasScheme options) {
        CanvasScheme oldValue = this.options;
        this.options = options;
        firePropertyChange("CanvasScheme", oldValue, this.options);
    }

    public CanvasScheme getOptions() {
        return options;
    }

    protected PaintBlock getPaintBlock() {
        if (null == paintBlock) {
            paintBlock = new PaintBlock(getOptions());
        }
        return paintBlock;
    }

    private void drawLineDiagram(Graphics g, AffineTransform scalingTransform) {
        getDrawing().initPaint();
        PaintBlock block = getPaintBlock();
        block.setGraphics(g);
        block.getGraphics2D().transform(getPaintTransform(scalingTransform));
        getDrawing().forAllFigures(block);
    }

    protected void initPaint() {
    }

    protected void doDrawOnGraphicsWithDimension(Graphics g, Dimension d, AffineTransform scalingTransform) {
        initPaint();
        drawBlankImage(g, d);
        drawLineDiagram(g, scalingTransform);
    }

    protected void visitFiguresAndRepaint(canvas.BaseFigureVisitor visitor) {
        getDrawing().applyUpdatingFigureVisitor(visitor);
    }


    public void selectFigure(Figure newSelection) {
        if (getFirstSelectedFigure() != newSelection ||
                getSelectionSize() > 1) {
            setSelection(Collections.singletonList(newSelection));
        }
    }

    private int getSelectionSize() {
        return selection.size();
    }


    IndexedSet selection = CollectionFactory.createIndexedSet();


    public Figure getFirstSelectedFigure() {
        if (hasSelection()) {
            return (Figure) selection.get(0);
        }
        return null;
    }

    protected void clearSelection() {
        selection.clear();
    }

    public Collection getSelection() {
        return Collections.unmodifiableSet(selection);
    }

    public boolean hasSelection() {
        return !selection.isEmpty();
    }

    class CanvasFigureDrawingListener implements FigureDrawingListener {
        public void dimensionChanged(Dimension newDimension) {
            drawingDimensionChanged(newDimension);
        }

        public void needUpdate() {
            repaint();
        }
    }


    public boolean isDiscernibleDifference(Point2D ptStart, Point2D ptCurrent) {
        //todo: take into account zoom factor
        return (Math.abs(ptCurrent.getX() - ptStart.getX()) > 4) || (Math.abs(ptCurrent.getY() - ptStart.getY()) > 4);
    }


    protected Tool activeTool;

    public void setActiveTool(Tool activeTool) {
        if (this.activeTool != activeTool) {
            if (null != this.activeTool) {
                this.activeTool.deactivate();
            }
            this.activeTool = activeTool;
            this.activeTool.activate();
        }
    }

    public Tool getActiveTool() {
        return activeTool;
    }

    public void moveFigures(Collection figures, double dx, double dy) {
        for (Iterator iterator = figures.iterator(); iterator.hasNext();) {
            Figure figure = (Figure) iterator.next();
            moveFigure(figure, dx, dy);
        }
    }

    public void moveFigure(Figure f, double dx, double dy) {
        f.moveBy(dx, dy);
    }

    public void updateDrawing() {
        getDrawing().applyChanges();
    }


    protected Tool SELECTION_TOOL = new SelectionTool(this);
    protected Tool DEFAULT_TOOL = SELECTION_TOOL;

    public void deactivateTool() {
        setActiveTool(getDefaultTool());
    }

    protected Tool getDefaultTool() {
        return DEFAULT_TOOL;
    }

    public void selectFiguresInRect(Rectangle2D selectionRect) {
        setSelection(getDrawing().selectFiguresInRect(selectionRect));
    }

    public void setSelection(Collection collection) {
        if (!isEqualToSelection(collection)) {
            Collection oldValue = selection;
            selection = CollectionFactory.createIndexedSet(collection);
            firePropertyChange(SELECTION_PROPERTY, oldValue, selection);
            repaint();
        }
    }

    private boolean isEqualToSelection(Collection collection) {
        return selection.containsAll(collection) && collection.containsAll(selection);
    }


    protected class MouseHandler extends MouseAdapter implements MouseMotionListener {
        public void mousePressed(MouseEvent e) {
            getActiveTool().mousePressed(e);
        }

        public void mouseReleased(MouseEvent e) {
            getActiveTool().mouseReleased(e);
        }

        public void mouseClicked(MouseEvent e) {
            getActiveTool().mouseClicked(e);
        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseDragged(MouseEvent e) {
            getActiveTool().mouseDragged(e);
        }

        public void mouseMoved(MouseEvent e) {
            getActiveTool().mouseMoved(e);
        }

    }

}
