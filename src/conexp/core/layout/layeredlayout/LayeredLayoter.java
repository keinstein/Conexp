package conexp.core.layout.layeredlayout;

import conexp.core.Lattice;
import conexp.core.LatticeElement;
import conexp.core.layout.*;
import util.collection.CollectionFactory;
import util.gui.GraphicObjectsFactory;

import java.util.SortedSet;
import java.awt.geom.Point2D;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public class LayeredLayoter extends NonIncrementalLayouter {
    ILayerAssignmentFunction layerAssignmentFunction;
    private LatticeElement[][] elementsByLayers;

    public class LayeredLayoutConceptInfo extends LayoutConceptInfo{
        int layer=-1;

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

    public LayeredLayoutConceptInfo getElementInfo(LatticeElement latticeElement){
         return (LayeredLayoutConceptInfo)getLayoutConceptInfo(latticeElement);
    }

    public void setLayerAssignmentFunction(ILayerAssignmentFunction layerAssignmentFunction) {
        this.layerAssignmentFunction = layerAssignmentFunction;
    }

    public LayeredLayoter() {
        setLayerAssignmentFunction(HeightInLatticeLayerAssignmentFunction.getInstance());
    }

    public void performLayout() {
        elementsByLayers = buildLayersInfo();
        initEvaluationFunctions();
        performSearchForBestLattice();
    }

    private void initEvaluationFunctions() {

    }

    private void assignLayersToLatticeElements() {
        getLayerAssignmentFunction().calculateLayersForLattice(lattice, new ILayerAssignmentFunction.ILayerAssignmentFunctionCallback(){
            public void layerForLatticeElement(LatticeElement latticeElement, int layer) {
                getElementInfo(latticeElement).layer = layer;
            }
        });
    }

    private LatticeElement[][] buildLayersInfo() {
        assignLayersToLatticeElements();
        int layerCount = findMaxLayerIndex()+1;
        int[] layerSizes = calculateLayersDimension(layerCount);
        return allocateNodesToLayers(layerSizes);
    }

    private LatticeElement[][] allocateNodesToLayers(int[] layerSizes) {
        final LatticeElement[][] elementsByLayers = new LatticeElement[layerSizes.length][];
        for(int i=0; i<layerSizes.length; i++){
            elementsByLayers[i] = new LatticeElement[layerSizes[i]];
        }
        final int[] currentPositionsInLayers = new int[layerSizes.length];
        lattice.forEach(new Lattice.LatticeElementVisitor(){
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
        lattice.forEach(new Lattice.LatticeElementVisitor(){
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

    private void performSearchForBestLattice() {

    }


    protected void assignCoordsToLattice() {

    }

    private class MaxLayerNumberElementVisitor implements Lattice.LatticeElementVisitor {
        int maxLayer = -1;

        public int getMaxLayer() {
            return maxLayer;
        }

        public void visitNode(LatticeElement node) {
            int currentElementLayer = getElementInfo(node).layer;
            if(currentElementLayer > maxLayer){
                maxLayer = currentElementLayer;
            }
        }
    }

}
