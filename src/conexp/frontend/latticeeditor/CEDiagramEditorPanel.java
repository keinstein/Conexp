/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import canvas.Figure;
import canvas.figures.IFigureWithCoords;
import canvas.figures.LineFigure;
import conexp.core.ExtendedContextEditingInterface;
import conexp.core.LatticeDiagramChecker;
import conexp.frontend.ContextDocument;
import conexp.frontend.View;
import conexp.frontend.latticeeditor.figures.NodeFigure;
import util.ArraysUtil;
import util.collection.ObjectToIntMap;
import util.gui.ActionWithKey;

import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.List;


public class CEDiagramEditorPanel extends DiagramEditorPanel implements View {

    /**
     * DEBUG
     */
    ContextDocument document;

    public void setDocument(ContextDocument doc) {
        this.document = doc;
    }


    class SetContextInDocument extends ActionWithKey {
        public SetContextInDocument() {
            super("setContext", "Generate Context");
        }

        public void actionPerformed(ActionEvent e) {
            LatticeDiagramChecker checker = getLatticeDiagramChecker();

            document.getContext().copyFrom(checker.getContext());
        }


    }

    class CheckIsLatticeAction extends ActionWithKey {
        public CheckIsLatticeAction() {
            super("checkIsLattice", "Check conceptSetDrawing");
        }

        public void actionPerformed(ActionEvent e) {
            convertDrawingAndCheckIsDrawingOfLattice();
        }

    }


    private void convertDrawingAndCheckIsDrawingOfLattice() {
        List nodes = collectNodes();
        LatticeDiagramChecker checker = buildDiagramChecker(nodes);
/*
        System.out.println("Check lattice returned: " + checker.isDiagramOfLattice());
        System.out.println("Check is semilattice returned: " + checker.isDiagramOfSemilattice());
*/
    }

    private LatticeDiagramChecker buildDiagramChecker(List nodes) {
        List lines = collectEdges();
        ObjectToIntMap nodeToIndexMap = new ObjectToIntMap(nodes);
        LatticeDiagramChecker checker = new LatticeDiagramChecker();
        checker.setNodeCount(nodes.size());
        buildCoversRelation(checker, nodeToIndexMap, lines);
        return checker;
    }

    private static void buildCoversRelation(LatticeDiagramChecker checker, ObjectToIntMap nodeToIndexMap, List lines) {
        for (Iterator linesIter = lines.iterator(); linesIter.hasNext();) {
            LineFigure lineFigure = (LineFigure) linesIter.next();
            IFigureWithCoords start = (IFigureWithCoords) lineFigure.getStartFigure();
            IFigureWithCoords end = (IFigureWithCoords) lineFigure.getEndFigure();

            Object lesser, bigger;

            if (start.getCenter().getY() > end.getCenter().getY()) {
                lesser = start;
                bigger = end;
            } else {
                lesser = end;
                bigger = start;
            }
            checker.setLessThan(nodeToIndexMap.get(lesser), nodeToIndexMap.get(bigger));
        }
    }

    public Action[] getActions() {
        return (Action[]) ArraysUtil.concat(super.getActions(),
                new Action[]{new CheckIsLatticeAction(), new SetContextInDocument()});
    }

    public void initialUpdate() {
    }


    public LatticeDiagramChecker getLatticeDiagramChecker() {
        LatticeDiagramChecker latticeDiagramChecker = buildDiagramChecker(collectNodes());
        formObjectNamesInContext(latticeDiagramChecker, collectNodes());
        return latticeDiagramChecker;
    }

    protected static void formObjectNamesInContext(LatticeDiagramChecker checker, List nodes) {
        ExtendedContextEditingInterface cxt = checker.getContext();
        for (int i = 0; i < cxt.getObjectCount(); i++) {
            NodeFigure figure = (NodeFigure) nodes.get(i);
            cxt.getObject(i).setName(String.valueOf(figure.getId()));
        }
    }

    protected int nodeId = 0;

    protected Figure makeHierarchyNodeFigure(Point2D userCoords) {
        NodeFigure figure = new NodeFigure();
        figure.setId(nodeId++);

        figure.setCoords(userCoords);
        return figure;
    }

    protected boolean isNodeFigure(Figure f) {
        return f instanceof NodeFigure;
    }
}
