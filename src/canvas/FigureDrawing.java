/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package canvas;

import util.Assert;
import util.BasePropertyChangeSupplier;
import util.collection.CollectionFactory;
import util.collection.ReverseListIterator;
import util.gui.GraphicObjectsFactory;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Iterator;

public class FigureDrawing extends BasePropertyChangeSupplier {

    public static final String BOUNDS_BOX_PROPERTY = "BoundsBox";

    private canvas.FigureListener fFigureListener = new DrawingFigureListener();

    public FigureDrawing() {
        super();
        addPropertyChangeListener(BOUNDS_BOX_PROPERTY, new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                Rectangle2D newBounds = (Rectangle2D) evt.getNewValue();
                setDimension(makeDrawingDimension((int) newBounds.getWidth(), (int) newBounds.getHeight()));
            }
        });
        setBounds(GraphicObjectsFactory.makeRectangle2D());
        makeBoundsRectDirty();
    }

//------------------------------------------------------------
    protected CanvasScheme options = new DefaultCanvasScheme();

    public CanvasScheme getOptions() {
        return options;
    }

    public void setOptions(CanvasScheme newOpt) {
        options = newOpt;
    }

//---------------------------------------------------------------------------------------
// FigureDrawingListener methods
    protected FigureDrawingListener fListener;

    public synchronized void addDrawingChangedListener(FigureDrawingListener mcl) {
        if (fListener == null) {
            fListener = mcl;
        }
    }

    public synchronized void removeDrawingChangedListener(FigureDrawingListener mcl) {
        fListener = null;
    }

    protected synchronized void fireDimensionChanged() {
        if (null != fListener) {
            fListener.dimensionChanged(getDimension());
        }
    }

    public synchronized void fireNeedUpdate() {
        if (null != fListener) {
            fListener.needUpdate();
        }
    }

//---------------------------------------------------------------------------------
// LineDiagramFigure container methods
    private java.util.List figures = CollectionFactory.createDefaultList();
    private java.util.List foreground = CollectionFactory.createDefaultList();


    public boolean isEmpty() {
        return foreground.isEmpty() && figures.isEmpty();
    }

    public void addFigure(Figure f) {
        figures.add(f);
        doAddFigure(f);
    }

    protected void doAddFigure(Figure f) {
        //order of calls is important
        addFigureRectToBounds(f);
        f.setFigureListener(fFigureListener);
    }

    public void addForegroundFigure(Figure f) {
        foreground.add(f);
        doAddFigure(f);
    }

    public void removeForegroundFigure(Figure f) {
        checkDamage(f);
        foreground.remove(f);
        f.removeFigureListener();
    }

    public void removeFigure(Figure f) {
        checkDamage(f);
        figures.remove(f);
        //todo: what about connected figures and so on ??
        f.removeFigureListener();
    }

    public void clear() {
        class RemoveListenerBlock implements FigureBlock {
            public void exec(Figure f) {
                f.removeFigureListener();
            }
        }
        forAllFigures(new RemoveListenerBlock());
        figures.clear();
        foreground.clear();
        makeBoundsRectDirty();
    }

    //---------------------------------------------------------------
    //Search methods
    public Figure findFigureInReverseOrder(double x, double y) {
        Figure ret = findContainingFigure(new ReverseListIterator(foreground), x, y);
        if (null != ret)
            return ret;
        return findContainingFigure(new ReverseListIterator(figures), x, y);
    }

    public Figure findFigureInReverseOrderExceptFor(double x, double y, Figure toExclude) {
        Figure ret = findContainingFigureExceptFor(new ReverseListIterator(foreground), x, y, toExclude);
        if (null != ret)
            return ret;
        return findContainingFigureExceptFor(new ReverseListIterator(figures), x, y, toExclude);
    }


    private Figure findContainingFigure(Iterator iter, double x, double y) {
        while (iter.hasNext()) {
            Figure curr = (Figure) iter.next();
            if (curr.contains(x, y)) {
                return curr.findFigureInside(x, y);
            }
        }
        return null;
    }

    private Figure findContainingFigureExceptFor(Iterator iter, double x, double y, Figure toExclude) {
        while (iter.hasNext()) {
            Figure curr = (Figure) iter.next();
            if (curr != toExclude && curr.contains(x, y)) {
                return curr.findFigureInsideExceptFor(x, y, toExclude);
            }
        }
        return null;
    }

//-------------------------------------------------------------------------------
//Iterating methods

    public void visitFigures(BaseFigureVisitor visitor) {
        Iterator iter = figures.iterator();
        while (iter.hasNext()) {
            ((Figure) iter.next()).visit(visitor);
        }
    }


    public void visitFiguresAndApplyChanges(BaseFigureVisitor visitor) {
        visitFigures(visitor);
        applyChanges();
    }


    public void forAllFigures(FigureBlock b) {
        forEachFigure(figures.iterator(), b);
        forEachFigure(foreground.iterator(), b);
    }

    private void forEachFigure(Iterator iter, FigureBlock b) {
        while (iter.hasNext()) {
            Figure curr = (Figure) iter.next();
            b.exec(curr);
        }
    }

//--------------------------------------------------------------------
// Should be called after moving figures

    public void applyChanges() {
        getUserBoundsRect();
    }


//-------------------------------------------------------------------
// Dimension calculation
    private boolean fBoundsRectDirty;

    private void makeBoundsRectClear() {
        fBoundsRectDirty = false;
    }

    public void makeBoundsRectDirty() {
        fBoundsRectDirty = true;
    }

    /**
     *  @test_public
     * */

    public boolean isBoundsRectDirty() {
        return fBoundsRectDirty;
    }

    public boolean layOnBorder(java.awt.Rectangle rect) {
        final double precision = 0.01;
        if (Math.abs(bounds.getMinX() - rect.x) < precision) {
            return true;
        }
        if (Math.abs(bounds.getMinY() - rect.y) < precision) {
            return true;
        }
        if (Math.abs(bounds.getMaxX() - (rect.x + rect.width)) < precision) {
            return true;
        }
        if (Math.abs(bounds.getMaxY() - (rect.y + rect.height)) < precision) {
            return true;
        }
        return false;
    }

    protected void checkDamage(Figure f) {
        Rectangle boundingRect = new Rectangle();
        f.boundingBox(boundingRect);
        if (layOnBorder(boundingRect)) {
            makeBoundsRectDirty();
        }
    }

    class DrawingFigureListener implements FigureListener {
        public void beforeFigureMove(Figure f) {
            if (isBoundsRectDirty()) {
                return;
            }
            checkDamage(f);
        }


        public void afterFigureMove(Figure f) {
            if (isBoundsRectDirty()) {
                return;
            }
            Rectangle boundingRect = new Rectangle();
            f.boundingBox(boundingRect);
            if (!bounds.contains(boundingRect)) {
                makeBoundsRectDirty();
            }
        }

    };


    private Rectangle2D bounds;

    private Dimension2D fDimension;

    private void setDimension(Dimension2D newFDimension) {
        if (null == fDimension || !fDimension.equals(newFDimension)) {
            fDimension = newFDimension;
            fireDimensionChanged();
        }
    }

    public Dimension2D makeDrawingDimension(int width, int height) {
        return new Dimension(width, height);
    }

    public Dimension getDimension() {
        if (null == fDimension || fBoundsRectDirty) {
            Rectangle rectangle = calculateUserBoundsRect();
            setBounds(rectangle);

            Assert.isTrue(null != fDimension);
        }
        return (java.awt.Dimension) fDimension.clone();
    }

    private void setBounds(Rectangle2D rectangle) {
        makeBoundsRectClear();
        if (null == bounds || !bounds.equals(rectangle)) {
            Rectangle2D oldValue = bounds;
            bounds = rectangle;
            firePropertyChange(BOUNDS_BOX_PROPERTY, oldValue, bounds);
        }
    }

    private void addFigureRectToBounds(Figure f) {
        Rectangle rect = new java.awt.Rectangle();
        f.boundingBox(rect);
        setBounds(bounds.createUnion(rect));
    }

    private Rectangle calculateUserBoundsRect() {
        Rectangle bounds = new Rectangle();

        BoundsBlock b = new BoundsBlock();
        b.init(bounds);
        forAllFigures(b);
        return bounds;
    }

    public Rectangle2D getUserBoundsRect() {
        if (fBoundsRectDirty) {
            setBounds(calculateUserBoundsRect());
            makeBoundsRectClear();
        }
        return (Rectangle2D) bounds.clone();
    }

    public void applyUpdatingFigureVisitor(BaseFigureVisitor visitor) {
        visitFiguresAndApplyChanges(visitor);
        fireNeedUpdate();
    }

    public Collection selectFiguresInRect(final Rectangle2D selectionRect) {
        final java.util.List ret = CollectionFactory.createDefaultList();
        forAllFigures(new FigureBlock() {
            Rectangle2D workingRect = GraphicObjectsFactory.makeRectangle2D();

            public void exec(Figure f) {
                f.boundingBox(workingRect);
                if (selectionRect.contains(workingRect)) {
                    ret.add(f);
                }
            }
        });
        return ret;

    }

    public void removeFigures(Collection figures) {
        for (Iterator figureIter = figures.iterator(); figureIter.hasNext();) {
            Figure figure = (Figure) figureIter.next();
            removeFigure(figure);
        }
    }


}
