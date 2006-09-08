/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import canvas.figures.ConnectionFigure;
import conexp.core.ConceptsCollection;
import conexp.core.Edge;
import conexp.core.ItemSet;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;
import conexp.frontend.latticeeditor.figures.EdgeFigure;
import conexp.frontend.latticeeditor.figures.NestedDiagramNodeFigure;
import conexp.frontend.latticeeditor.queries.ConceptNodeQuery;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;


public class NestedLineDiagramDrawing extends ConceptSetDrawing {
    LatticeDrawing outerDiagram;
    ConceptSetDrawing innerDiagram;
    ConceptsCollection conceptSet;

    public NestedLineDiagramDrawing(LatticeDrawing outerDiagram, ConceptSetDrawing innerDiagram, ConceptsCollection conceptSet) {
        buildNestedDrawing(outerDiagram, innerDiagram, conceptSet);
    }

    public void clear() {
        super.clear();
        elementFigureMap = null;
    }

    public void drawingParametersChanged() {

    }

    public void buildNestedDrawing(LatticeDrawing outerDiagram, ConceptSetDrawing innerDiagram, ConceptsCollection conceptSet) {
        if (hasConceptSet()) {
            getLabelingStrategiesContext().shutdownStrategies(this);
        }
        this.outerDiagram = outerDiagram;
        this.innerDiagram = innerDiagram;
        this.conceptSet = conceptSet;
        clear();
        if (hasConceptSet()) {
            buildNestedDiagram();
            initStrategies();
        }
    }

    public NestedLineDiagramDrawing() {
    }

    private void buildNestedDiagram() {
        makeConceptFigures();
        makeEdgeFigures();
        setCoordinatesForConceptFigures();
    }

    private void setCoordinatesForConceptFigures() {
        //we are doing the following thing
        //calculating size of inner node
        //rescaling line diagram of outer node by muptiplying coordinates

        double usualNodeRadius = getLatticeDrawingOptions().getDrawParams().getMaxNodeRadius();
        AbstractConceptCorrespondingFigure innerNodeFigure = getFigureForConcept(outerDiagram.getLattice().getOne());
        //TODO: think about better design
        NestedDiagramNodeFigure nestedFigure = (NestedDiagramNodeFigure) innerNodeFigure;
        double currentXNodeRadius = nestedFigure.getRadiusX();
        double currentYNOdeRadius = nestedFigure.getRadiusY();

        final double xScale = currentXNodeRadius / usualNodeRadius;
        final double yScale = currentYNOdeRadius / usualNodeRadius;

        final Rectangle2D boundsRect = outerDiagram.getUserBoundsRect();

        Lattice outerLattice = outerDiagram.getLattice();
        outerLattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement concept) {

                Point2D originalCoords = outerDiagram.getFigureForConcept(concept).getCenter();
                double xValueToScale = (originalCoords.getX() - boundsRect.getMinX()) / 2;
                double yValueToScale = (originalCoords.getY() - boundsRect.getMinY()) / 2;
                Point2D newCoords = new Point2D.Double(originalCoords.getX() + xScale * xValueToScale,
                        originalCoords.getY() + yScale * yValueToScale);

                getFigureForConcept(concept).setCoords(newCoords);
            }
        });
        applyChanges();
    }

    AbstractConceptCorrespondingFigure elementFigureMap[];

    private void makeConceptFigures() {
        final Lattice outerLattice = outerDiagram.getLattice();
        elementFigureMap = new AbstractConceptCorrespondingFigure[outerLattice.conceptsCount()];
        outerLattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                NestedDiagramNodeFigure f = new NestedDiagramNodeFigure(innerDiagram,
                        new ConceptNodeQuery(outerLattice.getContext(), node, false, outerLattice.getAttributesMask())
                        , null, node == outerLattice.getOne());
                //later this can be change for explicit pointing, whether this is top node or not
                //for more levels of nesting
                elementFigureMap[node.getIndex()] = f;
                addFigure(f);
            }
        });
    }

    private void makeEdgeFigures() {
        Lattice outerLattice = outerDiagram.getLattice();
        outerLattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                Iterator edges = node.successorsEdges();
                while (edges.hasNext()) {
                    Edge e = (Edge) edges.next();
                    addFigure(makeEdgeFigure(e));
                }
            }
        });
    }

    public AbstractConceptCorrespondingFigure getFigureForConcept(ItemSet c) {
        return elementFigureMap[c.getIndex()];
    }

    private ConnectionFigure makeEdgeFigure(Edge e) {
        return new EdgeFigure(getFigureForConcept(e.getStart()),
                getFigureForConcept(e.getEnd()));
    }

    public ConceptsCollection getConceptSet() {
        return conceptSet;
    }

    public Lattice getLattice() {
        return outerDiagram.getLattice();
    }

    public int getNumberOfLevelsInDrawing() {
        return outerDiagram.getLattice().getHeight();
    }

    public boolean hasConceptSet() {
        return conceptSet != null;
    }
}


