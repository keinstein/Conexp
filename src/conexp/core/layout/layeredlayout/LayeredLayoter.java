/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.core.layout.layeredlayout;

import conexp.core.BinaryRelation;
import conexp.core.Context;
import conexp.core.ContextFactoryRegistry;
import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import conexp.core.layout.GenericLayouter;
import conexp.core.layout.HeightInLatticeLayerAssignmentFunction;
import conexp.core.layout.ILayerAssignmentFunction;
import conexp.core.layout.NonIncrementalLayouter;
import conexp.core.utils.MinimumPartialOrderedElementsCollection;
import conexp.util.gui.paramseditor.ButtonParamInfo;
import conexp.util.gui.paramseditor.ParamInfo;
import util.collection.CollectionFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class LayeredLayoter extends NonIncrementalLayouter {
    ILayerAssignmentFunction layerAssignmentFunction;
    private LatticeElement[][] elementsByLayers;
    private ModifiableSet irreducibleElements;
    private int layerCount;
    private double xScale;

    public static class LayeredLayoutConceptInfo extends LayoutConceptInfo {
        int layer = -1;

        public LayeredLayoutConceptInfo() {

        }

        public int getLayer() {
            return layer;
        }
    }

    protected GenericLayouter.LayoutConceptInfo makeConceptInfo() {
        return new LayeredLayoutConceptInfo();
    }

    public ILayerAssignmentFunction getLayerAssignmentFunction() {
        return layerAssignmentFunction;
    }

    public LayeredLayoutConceptInfo getElementInfo(LatticeElement latticeElement) {
        return (LayeredLayoutConceptInfo) getLayoutConceptInfo(latticeElement);
    }

    public void setLayerAssignmentFunction(ILayerAssignmentFunction layerAssignmentFunction) {
        this.layerAssignmentFunction = layerAssignmentFunction;
    }

    public LayeredLayoter() {
        setLayerAssignmentFunction(HeightInLatticeLayerAssignmentFunction.getInstance());
    }

    public void calcInitialPlacement() {
        performLayout();
    }

    public void performLayout() {
        currentLayoutIndex = 0;
        bestLayoutsResults = new DirectionVectorEvaluationResultsPair[0];
        elementsByLayers = buildLayersInfo();
        initEvaluationFunctions();
        performSearchForBestLattice();
        assignCoordsToLattice();
    }

    AllConceptOnOneLayerHaveDifferentXCoordinatesEvaluationFunction latticeAcceptanceFunction;
    List evaluationFunctions;

    private void initEvaluationFunctions() {
        latticeAcceptanceFunction = new AllConceptOnOneLayerHaveDifferentXCoordinatesEvaluationFunction(this, elementsByLayers);
        evaluationFunctions = CollectionFactory.createDefaultList();
        evaluationFunctions.add(new NumberOfSimmetricallyAllocatedChildrenEvaluationFunction(lattice, this));
        evaluationFunctions.add(new DifferentEdgeVectorsEvaluationFunction(lattice, this));
        evaluationFunctions.add(new ThreeElementsChainCountEvaluationFunction(lattice, this));
        evaluationFunctions.add(new LengthOfEdgesEvaluationFunction(lattice, this));
        evaluationFunctions.add(new EdgeIntersectionEvaluationFunction(lattice, this));
    }

    private void assignLayersToLatticeElements() {
        getLayerAssignmentFunction().calculateLayersForLattice(lattice, new ILayerAssignmentFunction.ILayerAssignmentFunctionCallback() {
            public void layerForLatticeElement(LatticeElement latticeElement, int layer) {
                getElementInfo(latticeElement).layer = layer;
            }
        });
    }

    private LatticeElement[][] buildLayersInfo() {
        assignLayersToLatticeElements();
        layerCount = findMaxLayerIndex() + 1;
        int[] layerSizes = calculateLayersDimension(layerCount);
        return allocateNodesToLayers(layerSizes);
    }

    private LatticeElement[][] allocateNodesToLayers(int[] layerSizes) {
        final LatticeElement[][] elementsByLayers = new LatticeElement[layerSizes.length][];
        for (int i = 0; i < layerSizes.length; i++) {
            elementsByLayers[i] = new LatticeElement[layerSizes[i]];
        }
        final int[] currentPositionsInLayers = new int[layerSizes.length];
        lattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                LayeredLayoutConceptInfo elementInfo = getElementInfo(node);
                int layer = elementInfo.layer;
                int positionInLayer = currentPositionsInLayers[layer]++;
                elementsByLayers[layer][positionInLayer] = node;
            }
        });
        return elementsByLayers;
    }

    private int[] calculateLayersDimension(int layerCount) {
        final int[] layerSizes = new int[layerCount];
        lattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                layerSizes[getElementInfo(node).layer]++;
            }
        });
        return layerSizes;
    }

    private int findMaxLayerIndex() {
        MaxLayerNumberElementVisitor visitor = new MaxLayerNumberElementVisitor();
        lattice.forEach(visitor);
        return visitor.getMaxLayer();
    }


    MinimumPartialOrderedElementsCollection bestLayouts = new MinimumPartialOrderedElementsCollection();
    DirectionVectorEvaluationResultsPair[] bestLayoutsResults = new DirectionVectorEvaluationResultsPair[0];
    int currentLayoutIndex = 0;

    private void performSearchForBestLattice() {
        Context cxt = lattice.getContext();
        irreducibleElements = findIrreducibleAttributes(cxt);
        searchBestLatticesByBacktracking();
    }

    private void searchBestLatticesByBacktracking() {
        bestLayouts.clear();
        assignYCoordsToLattice();
/*
        System.out.println("Start search");
        System.out.println("irreducible elements:" + irreducibleElements.elementCount());
*/
        double[] directionVector = new double[irreducibleElements.elementCount()];
        double[] currentEvaluation = new double[evaluationFunctions.size()];

        BacktrackingAlgorithm algorithm = new BacktrackingAlgorithm();
        algorithm.setRange(-5, 5);
        algorithm.setStep(1);
        int searchSteps = 0;
        for (algorithm.firstPoint(directionVector); algorithm.hasMorePoints(directionVector); algorithm.nextPoint(directionVector))
        {
            assignXCoordinatesToLattice(directionVector);
            if (isLatticeSatisfactory()) {
                evaluateLattice(currentEvaluation);
                bestLayouts.add(new DirectionVectorEvaluationResultsPair(directionVector, currentEvaluation));
            }
            searchSteps++;
            if (searchSteps > 100000 && !bestLayouts.isEmpty()) {
                break;
            }
        }
    }

    private void evaluateLattice(double[] currentEvaluation) {
        for (int i = 0; i < evaluationFunctions.size(); i++) {
            currentEvaluation[i] = ((IEvaluationFunction) evaluationFunctions.get(i)).getEvaluationForLattice();
        }
    }

    private void assignYCoordsToLattice() {
        xScale = drawParams.getGridSizeX() / drawParams.getGridSizeY();
        lattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                LayeredLayoutConceptInfo elementInfo = getElementInfo(node);
                elementInfo.setY((layerCount - 1 - elementInfo.getLayer()) * drawParams.getGridSizeY());
            }
        });
    }

    private boolean isLatticeSatisfactory() {
        return latticeAcceptanceFunction.getEvaluationForLattice() >= 0;
    }

    private void assignXCoordinatesToLattice(final double[] directionVector) {
        lattice.forEach(new Lattice.LatticeElementVisitor() {
            public void visitNode(LatticeElement node) {
                getElementInfo(node).setX(xScale * drawParams.getGridSizeX() * calculateXPosition(node.getAttribs(), directionVector));
            }
        });
    }

    double calculateXPosition(Set attributes, double[] vectorsX) {
        double x = 0;
        int posInVector = 0;
        for (int index = irreducibleElements.firstIn(); index != Set.NOT_IN_SET;
             index = irreducibleElements.nextIn(index)) {
            if (attributes.in(index)) {
                x += vectorsX[posInVector];
            }
            posInVector++;
        }
        return x;
    }

    public static ModifiableSet findIrreducibleAttributes(Context cxt) {
        int attributeCount = cxt.getAttributeCount();
        ModifiableSet irreducibleElements = ContextFactoryRegistry.createSet(attributeCount);
        BinaryRelation upArrow = cxt.getUpArrow();
        int rowCount = upArrow.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            irreducibleElements.or(upArrow.getSet(i));
        }
        ModifiableSet elementLessOrEqual = ContextFactoryRegistry.createSet(attributeCount);
        ModifiableSet elementOutOfSet = ContextFactoryRegistry.createSet(attributeCount);
        BinaryRelation relation = cxt.getRelation();
        for (int i = irreducibleElements.firstIn(); i != Set.NOT_IN_SET; i = irreducibleElements.nextIn(i)) {
            elementLessOrEqual.fill();
            elementOutOfSet.clearSet();
            for (int row = 0; row < relation.getRowCount(); row++) {
                Set set = relation.getSet(row);
                if (set.in(i)) {
                    elementLessOrEqual.and(set);
                } else {
                    elementOutOfSet.or(set);
                }
            }
            elementLessOrEqual.andNot(elementOutOfSet);
            //now elementLessOrEqual contains equivalent attributes to i;
            elementLessOrEqual.remove(i);
            irreducibleElements.andNot(elementLessOrEqual);
        }
        return irreducibleElements;
    }

    protected void assignCoordsToLattice() {
        bestLayoutsResults = new DirectionVectorEvaluationResultsPair[bestLayouts.getSize()];
        bestLayouts.toArray(bestLayoutsResults);
        if (!bestLayouts.isEmpty()) {
//            System.out.println("bestLayouts:" + bestLayouts);
            setCurrentLayout(0);
        } else {
            fireLayoutChanged(); //simple for proform. This is posi
        }
    }

    private void setCurrentLayout(int layoutIndex) {
        if (layoutIndex < 0) {
            return;
        }
        if (layoutIndex >= getBestLayoutCount()) {
            return;
        }
        currentLayoutIndex = layoutIndex;
        DirectionVectorEvaluationResultsPair pair = bestLayoutsResults[currentLayoutIndex];
//        System.out.println("current layout params: "+pair);
        assignXCoordinatesToLattice(pair.getDirectionVectors());
        fireLayoutChanged();
    }

    protected ParamInfo[] makeParams() {
        return new ParamInfo[]{
                new ButtonParamInfo("Next layout", ">>", new ActionListener() {
                    public void actionPerformed(ActionEvent ev) {
                        selectNextLayout();
                    }
                }),
                new ButtonParamInfo("Prev layout", "<<", new ActionListener() {
                    public void actionPerformed(ActionEvent ev) {
                        selectPrevLayout();
                    }
                }),

        };
    }

    private void selectPrevLayout() {
        int nextIndex = currentLayoutIndex - 1;
        if (nextIndex < 0) {
            nextIndex = getBestLayoutCount() - 1;
        }
        setCurrentLayout(nextIndex);
    }

    private void selectNextLayout() {
        int nextIndex = currentLayoutIndex + 1;
        if (nextIndex >= getBestLayoutCount()) {
            nextIndex = 0;
        }
        setCurrentLayout(nextIndex);
    }

    private int getBestLayoutCount() {
        return bestLayoutsResults.length;
    }

    private class MaxLayerNumberElementVisitor implements Lattice.LatticeElementVisitor {
        int maxLayer = -1;

        public int getMaxLayer() {
            return maxLayer;
        }

        public void visitNode(LatticeElement node) {
            int currentElementLayer = getElementInfo(node).layer;
            if (currentElementLayer > maxLayer) {
                maxLayer = currentElementLayer;
            }
        }
    }

}
