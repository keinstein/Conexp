/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import canvas.BaseFigureVisitor;
import canvas.CanvasColorScheme;
import canvas.CanvasScheme;
import canvas.Figure;
import canvas.FigureWithDependentFigures;
import canvas.IHighlightStrategy;
import conexp.core.LatticeElement;
import conexp.core.Set;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategy;
import conexp.frontend.latticeeditor.LatticeCanvasScheme;
import util.collection.CollectionFactory;
import util.gui.ColorUtil;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractConceptCorrespondingFigure extends AbstractLineDiagramFigure implements FigureWithDependentFigures {

    protected AbstractConceptCorrespondingFigure() {
        super();
    }

    private List connected;

    protected List getConnected() {
        if (null == connected) {
            connected = CollectionFactory.createDefaultList();
        }
        return connected;
    }

    public void addDependend(Figure f) {
        getConnected().add(f);
    }

    public void removeDependend(Figure f) {
        getConnected().remove(f);
    }

    public void removeAllDependend() {
        getConnected().clear();
    }

    public Iterator dependendFigures() {
        return getConnected().iterator();
    }

    protected void basicMoveBy(double dx, double dy) {
        super.basicMoveBy(dx, dy);

        Iterator it = dependendFigures();
        while (it.hasNext()) {
            Figure f = (Figure) it.next();
            f.moveBy(dx, dy);
        }
    }


    public abstract LatticeElement getConcept();

    public Set getIntentQuery() {
        return getConceptQuery().getQueryIntent();
    }

    public abstract ConceptQuery getConceptQuery();

    public void visit(BaseFigureVisitor visitor) {
        ((FigureVisitor) visitor).visitConceptCorrespondingFigure(this);
    }

    protected Color getNodeBorderColor(CanvasScheme opt) {
        CanvasColorScheme colorScheme = opt.getColorScheme();
        if (hasCollision()) {
            return colorScheme.getCollisionColor();
        }
        IHighlightStrategy highlightStrategy = opt.getHighlightStrategy();
        if (highlightStrategy.highlightFigure(this)) {
            return colorScheme.getHighlightColor();
        }
        return transformColor(highlightStrategy, colorScheme.getNodeBorderColor());
    }

    protected Color getNodeBackColor(LatticeCanvasScheme opt) {
        return transformColor(opt.getHighlightStrategy(), opt.getColorScheme().getNodeColor());
    }

    protected Color transformColor(IHighlightStrategy highlightStrategy, Color color) {
        if (!highlightStrategy.isActive()) {
            return color;
        }
        if (highlightStrategy.highlightFigure(this)) {
            return color;
        }
        return ColorUtil.fadeOut(color);
    }

    public void draw(Graphics g, CanvasScheme opt) {
        Graphics2D g2D = (Graphics2D) g;
        LatticeCanvasScheme latticeCanvasSchema = (LatticeCanvasScheme) opt;
        drawBackground(g2D, latticeCanvasSchema);
        drawInterior(g2D, latticeCanvasSchema);
        drawBorder(g2D, latticeCanvasSchema);
    }

    protected abstract void drawInterior(Graphics2D g2D, LatticeCanvasScheme opt);

    private void drawBackground(Graphics2D g2D, LatticeCanvasScheme opt) {
        g2D.setColor(getNodeBackColor(opt));
        g2D.fill(getFigureEllipse(opt));
    }

    private void drawBorder(Graphics2D g2D, LatticeCanvasScheme opt) {
        g2D.setColor(getNodeBorderColor(opt));
        float thickness = hasCollision() ? 2.0f : 1.0f;
        Stroke oldStroke = g2D.getStroke();
        g2D.setStroke(getStroke(thickness));
        g2D.draw(getFigureEllipse(opt));
        g2D.setStroke(oldStroke);
    }

    private static Stroke getStroke(float thickness) {
        return new BasicStroke(thickness);
    }

    private Ellipse2D getFigureEllipse(LatticeCanvasScheme opt) {
        return getFigureEllipse(opt.getDrawStrategiesContext().getFigureDimensionCalcStrategy());
    }

    public abstract Ellipse2D getFigureEllipse(FigureDimensionCalcStrategy figureDimensionCalcStrategy);

    public boolean contains(double x, double y) {
        return getFigureEllipse(getDimensionCalcStrategy()).contains(x, y);
    }

    public void boundingBox(Rectangle2D rect) {
        rect.setFrame(getFigureEllipse(getDimensionCalcStrategy()).getBounds2D());
    }

    public void borderAt(Point2D outPoint, Point2D result) {
        borderAt(outPoint, result);
    }

}
