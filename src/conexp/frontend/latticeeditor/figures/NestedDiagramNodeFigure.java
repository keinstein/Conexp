/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor.figures;

import canvas.BaseFigureVisitor;
import canvas.Figure;
import canvas.figures.CenterPointLocator;
import canvas.figures.ConnectionFigure;
import conexp.core.Edge;
import conexp.core.ItemSet;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.ConceptSetDrawing;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategy;
import conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider;
import conexp.frontend.latticeeditor.LatticeCanvasScheme;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

public class NestedDiagramNodeFigure extends ConceptCorrespondingFigure {
    //todo: make a subclass of CompositeFigureWithFigureDimensionCalcStrategyProvider

    ConceptSetDrawing innerDiagram;
    CompositeFigureWithFigureDimensionCalcStrategyProvider innerFigures;
    ConceptQuery nativeConceptQuery;
    ConceptQuery outerConceptQuery;
    boolean isTop;

    public NestedDiagramNodeFigure(ConceptSetDrawing innerDiagram, ConceptNodeQuery nativeConceptQuery, ConceptQuery outerConceptQuery, boolean isTop) {
        super(nativeConceptQuery.getConcept());
        this.nativeConceptQuery = nativeConceptQuery;
        this.innerDiagram = innerDiagram;
        this.outerConceptQuery = outerConceptQuery;
        this.isTop = isTop;
        buildInnerFigures();
    }

    public void setFigureDimensionCalcStrategyProvider(FigureDimensionCalcStrategyProvider figureDimensionProvider) {
        super.setFigureDimensionCalcStrategyProvider(figureDimensionProvider);
        innerFigures.setFigureDimensionCalcStrategyProvider(figureDimensionProvider);
        //what about inner diagrams
    }


    public ConceptQuery getConceptQuery() {
        if (null == outerConceptQuery) {
            return nativeConceptQuery;
        }
        return outerConceptQuery;
    }

    class InnerFigureCenterPointLocator implements CenterPointLocator {
        AbstractConceptCorrespondingFigure innerFigure;
        CenterPointLocator innerFigureCenterPointLocator;

        public InnerFigureCenterPointLocator(AbstractConceptCorrespondingFigure innerFigure) {
            this.innerFigure = innerFigure;
            innerFigureCenterPointLocator = innerFigure.getCenterPointLocator();
        }

        public double getCenterX() {
            final double outerFigureCenterX = NestedDiagramNodeFigure.this.getCenterX();

            Rectangle2D innerBounds = innerDiagram.getUserBoundsRect();
            double innerFigureCenterX = innerFigureCenterPointLocator.getCenterX();
            return outerFigureCenterX + (innerFigureCenterX - innerBounds.getCenterX());
        }

        public double getCenterY() {
            final double outerFigureCenterY = NestedDiagramNodeFigure.this.getCenterY();
            Rectangle2D innerBounds = innerDiagram.getUserBoundsRect();
            double innerFigureCenterY = innerFigureCenterPointLocator.getCenterY();
            return outerFigureCenterY + (innerFigureCenterY - innerBounds.getCenterY());
        }

        public void setCenterCoords(double x, double y) {
        }
    }


    AbstractConceptCorrespondingFigure[] elementFigureMap;

    private void makeEdgeFigures() {
        Lattice lattice = innerDiagram.getLattice();
        lattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                Iterator edges = node.successorsEdges();
                while (edges.hasNext()) {
                    Edge e = (Edge) edges.next();
                    innerFigures.addFigure(makeEdgeFigure(e));
                }
            }
        });
    }

    private ConnectionFigure makeEdgeFigure(Edge e) {
        return new EdgeFigure(getFigureForConcept(e.getStart()),
                getFigureForConcept(e.getEnd()));
    }

    public AbstractConceptCorrespondingFigure getFigureForConcept(ItemSet c) {
        return elementFigureMap[c.getIndex()];
    }

    private void buildInnerFigures() {
        innerFigures = new CompositeFigureWithFigureDimensionCalcStrategyProvider();
        innerFigures.setFigureDimensionCalcStrategyProvider(getFigureDimensionProvider());
        Lattice lattice = innerDiagram.getLattice();
        elementFigureMap = new AbstractConceptCorrespondingFigure[lattice.conceptsCount()];

        lattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                AbstractConceptCorrespondingFigure innerDiagramNodeFigure = innerDiagram.getFigureForConcept(node);
                NestedDiagramDecoratingFigure nestedNodeFigure =
                        new NestedDiagramDecoratingFigure(innerDiagramNodeFigure, getConceptQuery(), isTop);
                nestedNodeFigure.setCenterPointLocator(new InnerFigureCenterPointLocator(innerDiagramNodeFigure));
                innerFigures.addFigure(nestedNodeFigure);
                elementFigureMap[node.getIndex()] = nestedNodeFigure;
            }
        });
        makeEdgeFigures();

    }

    public void borderAt(Point2D outPoint, Point2D result) {
        double dx = outPoint.getX() - getCenterX();
        double dy = outPoint.getY() - getCenterY();
        double len = Math.sqrt(dx * dx + dy * dy);
        dx = getRadiusX() * dx / len;
        dy = getRadiusY() * dy / len;
        result.setLocation(getCenterX() + dx, getCenterY() + dy);
    }

    protected void drawInterior(Graphics2D g2D, LatticeCanvasScheme opt) {
        innerFigures.draw(g2D, opt);
    }

    protected void basicMoveBy(double dx, double dy) {
        super.basicMoveBy(dx, dy);
        innerFigures.basicMoveBy(dx, dy);
    }

    public Ellipse2D getFigureEllipse(FigureDimensionCalcStrategy figureDimensionCalcStrategy) {
        final double radiusX = getRadiusX();
        final double radiusY = getRadiusY();
        Ellipse2D oval = new Ellipse2D.Double(getCenterX() - radiusX,
                getCenterY() - radiusY,
                radiusX * 2, radiusY * 2);
        return oval;
    }

    public double getRadiusX() {
        Rectangle2D bounds = this.innerDiagram.getUserBoundsRect();
        if (bounds.getHeight() > 2 * bounds.getWidth()) {
            return bounds.getHeight() / 3.4;
        } else {
            return bounds.getWidth() / 1.7;
        }
    }

    public double getRadiusY() {
        Rectangle2D bounds = this.innerDiagram.getUserBoundsRect();
        if (bounds.getWidth() > 2 * bounds.getHeight()) {
            return bounds.getWidth() / 3.4;
        } else {
            return bounds.getHeight() / 1.7;
        }
    }

    public String toString() {
        return super.toString() +
                "Concept: " + getConcept();
    }

    public Figure findFigureInside(double x, double y) {
        if (innerFigures.contains(x, y)) {
            return innerFigures.findFigureInside(x, y);
        }
        return super.findFigureInside(x, y);
    }


    public void visit(BaseFigureVisitor visitor) {
        super.visit(visitor);
        innerFigures.visit(visitor);
    }
}
