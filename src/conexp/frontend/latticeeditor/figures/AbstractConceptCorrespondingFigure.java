/*
 * User: Serhiy Yevtushenko
 * Date: Jun 9, 2002
 * Time: 4:58:01 AM
 */
package conexp.frontend.latticeeditor.figures;

import canvas.*;
import canvas.figures.ICenterPointLocatorProvidingFigure;
import conexp.core.LatticeElement;
import conexp.core.Set;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategy;
import conexp.frontend.latticeeditor.LatticeCanvasScheme;
import util.collection.CollectionFactory;
import util.gui.ColorUtil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractConceptCorrespondingFigure extends AbstractLineDiagramFigure implements BorderCalculatingLineDiagramFigure, FigureWithDependentFigures, ICenterPointLocatorProvidingFigure{

    public AbstractConceptCorrespondingFigure() {
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
            canvas.Figure f = (canvas.Figure) it.next();
            f.moveBy(dx, dy);
        }
    }


    public abstract LatticeElement getConcept();

    public Set getIntentQuery(){
       return getConceptQuery().getQueryIntent();
    }

    public abstract ConceptQuery getConceptQuery();

    public void visit(BaseFigureVisitor visitor) {
        ((FigureVisitor)visitor).visitConceptCorrespondingFigure(this);
    }

    protected Color getNodeColor(CanvasScheme opt) {
        canvas.CanvasColorScheme colorScheme = opt.getColorScheme();
        IHighlightStrategy highlightStrategy = opt.getHighlightStrategy();
        if (highlightStrategy.highlightFigure(this)){
            return colorScheme.getHighlightColor();
        }
        return transformColor(highlightStrategy, colorScheme.getNodeBorderColor());
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

    public void draw(Graphics g, canvas.CanvasScheme opt) {
        Graphics2D g2D = (Graphics2D) g;
        LatticeCanvasScheme latticeCanvasSchema = (LatticeCanvasScheme)opt;
        drawBackgroundAndBorder(g2D, latticeCanvasSchema);
        drawInterior(g2D, latticeCanvasSchema);
    }

    protected abstract void drawInterior(Graphics2D g2D, LatticeCanvasScheme opt);

    protected void drawBackgroundAndBorder(Graphics2D g2D, LatticeCanvasScheme opt) {
        IHighlightStrategy highlightStrategy = opt.getHighlightStrategy();
        g2D.setColor(transformColor(highlightStrategy, opt.getColorScheme().getNodeColor()));
        FigureDimensionCalcStrategy figureDimensionCalcStrategy = opt.getDrawStrategiesContext().getFigureDimensionCalcStrategy();
        Ellipse2D oval = getFigureEllipse(figureDimensionCalcStrategy);
        g2D.fill(oval);
        g2D.setColor(getNodeColor(opt));
        g2D.draw(oval);
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
