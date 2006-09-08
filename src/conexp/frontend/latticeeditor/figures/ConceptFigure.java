/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import canvas.CanvasColorScheme;
import canvas.IHighlightStrategy;
import canvas.Selectable;
import canvas.figures.FigureUtils;
import conexp.core.ExtendedContextEditingInterface;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategy;
import conexp.frontend.latticeeditor.LatticeCanvasScheme;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;
import util.StringUtil;

import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class ConceptFigure extends ConceptCorrespondingFigure implements Selectable {

    ConceptQuery query;

    public ConceptFigure(ConceptNodeQuery conceptQuery) {
        super(conceptQuery.getConcept());
        query = conceptQuery;
    }

    public void borderAt(Point2D outPoint, Point2D result) {
        doCalcBorderAt(getDimensionCalcStrategy(), outPoint, result);
    }

    private void doCalcBorderAt(FigureDimensionCalcStrategy dimensionCalcStrategy, Point2D outPoint, Point2D result) {
        FigureUtils.calcEllipseBorder(dimensionCalcStrategy.calcNodeRadius(getConceptQuery()),
                getCenterX(), getCenterY(), outPoint, result);
    }

    public ConceptQuery getConceptQuery() {
        return query;
    }

    public void boundingBox(Rectangle2D rect) {
        doBoundingBox(getDimensionCalcStrategy(), rect);
    }

    private void doBoundingBox(FigureDimensionCalcStrategy dimensionCalcStrategy, Rectangle2D rect) {
        rect.setFrame(getFigureEllipse(dimensionCalcStrategy).getBounds2D());
    }

    public Ellipse2D getFigureEllipse(FigureDimensionCalcStrategy figureDimensionCalcStrategy) {
        double nodeRadius = figureDimensionCalcStrategy.calcNodeRadius(getConceptQuery());
        return new Ellipse2D.Double(getCenterX() - nodeRadius, getCenterY() - nodeRadius, nodeRadius * 2, nodeRadius * 2);
    }


    protected void drawInterior(Graphics2D g2D, LatticeCanvasScheme opt) {
        double nodeRadius = opt.getDrawStrategiesContext().getFigureDimensionCalcStrategy().calcNodeRadius(getConceptQuery());
        CanvasColorScheme colorScheme = opt.getColorScheme();
        ///*DBG*/ System.out.println("colorScheme in concept figure:"+colorScheme);
        IHighlightStrategy highlightStrategy = opt.getHighlightStrategy();
        g2D.setColor(transformColor(highlightStrategy, colorScheme.getNodeFillColor()));
        Arc2D arc = new Arc2D.Double(getCenterX() - nodeRadius, getCenterY() - nodeRadius, nodeRadius * 2, nodeRadius * 2, 0, 180, Arc2D.CHORD);
        if (getConcept().hasOwnAttribs()) {
            g2D.fill(arc);
        }
        g2D.setColor(transformColor(highlightStrategy, colorScheme.getNodeBorderColor()));
        if (getConcept().hasOwnObjects()) {
            arc.setAngleStart(0);
            arc.setAngleExtent(-180);
            g2D.fill(arc);
        }
    }

    public String getDescription(ExtendedContextEditingInterface cxt) {
        StringBuffer ret = new StringBuffer(100);
        int bound = getConcept().getAttribs().length();
        ret.append("<html><b> Intent: </b><i>");
        for (int i = 0; i < bound; i++) {
            if (getConcept().getAttribs().in(i)) {
                ret.append("<br>" + cxt.getAttribute(i).getName());
            }
        }
        ret.append("</i><br><b>Extent:</b><br>" + makeObjDesc(getConcept().getObjCnt(), cxt.getObjectCount()));
        ret.append("<br><b>Own Objects:</b><br>" + makeObjDesc(getConcept().getOwnObjCnt(), cxt.getObjectCount()));
        ret.append("</html>");
        return ret.toString();
    }

    private static String makeObjDesc(int cnt, int totalCnt) {
        return "<i>" + cnt + " objects (" + StringUtil.formatPercents(totalCnt != 0 ? (double) cnt / totalCnt : 0.) + ")</i>";
    }

    protected String getContentDescription() {
        return super.getContentDescription() + " Concept:" + getConcept();
    }

    boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


}
