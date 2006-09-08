/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.layout.chaindecomposition;

import com.visibleworkings.trace.Trace;
import conexp.core.BinaryRelation;
import conexp.core.BinaryRelationUtils;
import conexp.core.ContextFactoryRegistry;
import conexp.core.LatticeElement;
import conexp.core.ModifiableBinaryRelation;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import conexp.core.layout.NonIncrementalLayouter;
import conexp.util.gui.paramseditor.ButtonParamInfo;
import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.StrategyValueItemParamInfo;
import conexp.util.gui.strategymodel.StrategyValueItem;
import util.Assert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class ChainDecompositionLayout extends NonIncrementalLayouter {


    private double[] vectorsX;
    private double[] vectorsY;

    public static final String CONCEPT_PLACEMENT_EVENT = "placementStrategy";
    private transient StrategyValueItem conceptPlacementStrategyItem;
    public static final String DECOMPOSITION_STRATEGY_EVENT = "decompositionStrategy";
    private transient StrategyValueItem decompositionStrategyItem;
    private PropertyChangeSupport propertyChange;
    private PropertyChangeListener propertyChangeListener;

    private final static int unknown = -1;
    private int[] chains;
    private double base;
    private double stretch;

    /**
     * ScorskyLayout constructor comment.
     */
    public ChainDecompositionLayout() {
        super();
    }

    public void calcInitialPlacement() {
        getDecompositionStrategy().setContext(lattice.getContext());
        base = 1.0;
        stretch = 1.0;
        computeDiagram();
    }

    private void computePosition(Set set, Point2D res) {
        double x = 0;
        double y = 0;
        for (int i = set.length(); --i >= 0;) {
            if (set.in(i)) {
                x += vectorsX[i];
                y += vectorsY[i];
            }
        }
        res.setLocation(x, y);
    }

    public void performLayout() {
        calcInitialPlacement();
    }

    private ChainDecompositionStrategy getDecompositionStrategy() {
        return (ChainDecompositionStrategy) getDecompositionStrategyItem().getStrategy();
    }

    private ConceptPlacementStrategy getPlacementStrategy() {
        return (ConceptPlacementStrategy) getConceptPlacementStrategyItem().getStrategy();
    }

    private synchronized PropertyChangeSupport getPropertyChange() {
        if (null == propertyChange) {
            propertyChange = new PropertyChangeSupport(this);
            propertyChange.addPropertyChangeListener(getPropertyChangeListener());
        }
        return propertyChange;
    }

    private synchronized PropertyChangeListener getPropertyChangeListener() {
        if (null == propertyChangeListener) {
            propertyChangeListener = new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    Trace.gui.debugm("ChainDecompositionLayout get property change ", evt.getPropertyName());
                    if (CONCEPT_PLACEMENT_EVENT.equals(evt.getPropertyName())) {
                        performLayout();
                    }
                    if (DECOMPOSITION_STRATEGY_EVENT.equals(evt.getPropertyName())) {
                        performLayout();
                    }
                }
            };
        }
        return propertyChangeListener;
    }

    protected ParamInfo[] makeParams() {

        return new ParamInfo[]{
                new StrategyValueItemParamInfo("Representation", getDecompositionStrategyItem()),
                new StrategyValueItemParamInfo("Placement", getConceptPlacementStrategyItem()),
                new ButtonParamInfo("Rotate left", "<<", new ActionListener() {
                    public void actionPerformed(ActionEvent ev) {
                        rotateChainsLeft();
                    }
                }),
                new ButtonParamInfo("Rotate right", ">>", new ActionListener() {
                    public void actionPerformed(ActionEvent ev) {
                        rotateChainsRight();
                    }
                })

        };
    }

    public synchronized void setPropertyChangeListener(PropertyChangeListener newPropertyChangeListener) {
        propertyChangeListener = newPropertyChangeListener;
    }

    private static void assignChainNumbersForEntities(int[] chains, BinaryRelation order, Set reducibles, Set endsOfEdgesOfMatching, int[] matching) {

        for (int v = order.getRowCount(); --v >= 0;) {
            chains[v] = -1;
        }

        ModifiableBinaryRelation connected = order.makeModifiableCopy();
        BinaryRelationUtils.makeSymmetric(connected);
        BinaryRelationUtils.transitiveClosure(connected);


        int chain = 0;
        for (int v = endsOfEdgesOfMatching.firstOut(); v >= 0; v = endsOfEdgesOfMatching.nextOut(v)) {
            int w = v;
            while (w >= 0 && chains[w] == -1) {
                int x = w;
                if (!reducibles.in(x)) {
                    do {
                        chains[x] = chain;
                        x = matching[x];
                    } while (x != unknown);

                    chain++;
                }

                do {
                    w = endsOfEdgesOfMatching.nextOut(w);
                } while (w >= 0 && !(connected.getRelationAt(v, w) && chains[w] == -1));

            }
        }
    }


    protected void assignCoordsToLattice() {
        Point2D pt = new Point2D.Double();
        computePosition(getDecompositionStrategy().conceptRepresentation(lattice.getZero()), pt);
        double maxY = pt.getY();
        computePosition(getDecompositionStrategy().conceptRepresentation(lattice.getOne()), pt);
        double minY = pt.getY();
        double yDiff = Math.abs(maxY - minY);
        lattice.calcHeight();
        double realYSize = lattice.getHeight() * drawParams.getGridSizeY();
        double yScale = yDiff != 0 ? realYSize / yDiff : 1;
        double xScale = yScale * drawParams.getGridSizeX() / drawParams.getGridSizeY();

        for (int i = lattice.conceptsCount(); --i >= 0;) {
            LatticeElement concept = lattice.elementAt(i);
            computePosition(getDecompositionStrategy().conceptRepresentation(concept), pt);
            LayoutConceptInfo conceptInfo = getLayoutConceptInfo(concept);
            conceptInfo.setX(xScale * pt.getX());
            conceptInfo.setY(yScale * (pt.getY() - minY));
        }
        fireLayoutChanged();
    }

    private void calcConceptsPlacement() {
        computeVectors();
        assignCoordsToLattice();
    }

    private static BinaryRelation calcOrderGraphOfIrreducibleEntities(BinaryRelation order, Set reducibles) {
        ModifiableBinaryRelation edges = order.makeModifiableCopy();
        //Here is calculated graph of order, in which removed edges between same vertices
        // (diagonal elements of order)
        // and all edges, which has at least one reducible element in vertices
        int size = order.getRowCount();
        for (int v = 0; v < size; v++) {
            ModifiableSet currEdge = edges.getModifiableSet(v);
            if (reducibles.in(v)) {
                currEdge.clearSet();
            } else {
                currEdge.remove(v);
                currEdge.andNot(reducibles);
            }
        }
        return edges;
    }

    private void computeChainDecomposition(BinaryRelation order) {
        Assert.isTrue(null != order);
        Assert.isTrue(order.getRowCount() == order.getColCount());

        Set reducibles = findReducibleEntities(order);
        BinaryRelation edges = calcOrderGraphOfIrreducibleEntities(order, reducibles);

        final int size = order.getRowCount();

        ModifiableSet notInStartOfEdgesOfMatching = ContextFactoryRegistry.createSet(size);
        ModifiableSet endsOfEdgesOfMatching = ContextFactoryRegistry.createSet(size);
        int[] matching = new int[size]; // matching[v] - end of edge in matching for entity v, or -1, if entity isn't in matching

        notInStartOfEdgesOfMatching.fill();
        endsOfEdgesOfMatching.clearSet();

        ModifiableSet temp = ContextFactoryRegistry.createSet(size);
        //building of initial matching
        for (int v = 0; v < size; v++) {
            temp.copy(edges.getSet(v));
            temp.andNot(endsOfEdgesOfMatching);

            matching[v] = temp.firstIn();
            if (matching[v] != unknown) {

                notInStartOfEdgesOfMatching.remove(v);
                endsOfEdgesOfMatching.put(matching[v]);
            }
        }


        final int unlabelled = -1;
//	label[v] has sense only for edges, which are in matching
//  if(label[v]>=0) this mean, that edge (label[v], matching[v]) can be put in matching,
//  as alternative to edge (v, matching[v])

        int[] label = new int[size];

// Edges, that's start in entities, that are already in matching,
        // end ends in entities, that are not in matching
        int[] exposed = new int[size];
        ModifiableSet Q = (ModifiableSet) notInStartOfEdgesOfMatching.clone();

        for (int v = 0; v < size; v++) {
            if (notInStartOfEdgesOfMatching.in(v)) {
                exposed[v] = unknown;
            } else {
                temp.copy(edges.getSet(v));
                temp.andNot(endsOfEdgesOfMatching);
                exposed[v] = temp.firstIn();
            }
            label[v] = unlabelled;
        }

        while (!Q.isEmpty()) {
            int v = Q.firstIn();
            Q.remove(v);

            if (label[v] != unlabelled &&
                    exposed[v] != unknown &&
                    !endsOfEdgesOfMatching.in(exposed[v])) {
                //we find a path, that enlarges the matching.
                //calculating new matching
                endsOfEdgesOfMatching.put(exposed[v]);
                while (label[v] != unlabelled) {
                    exposed[label[v]] = matching[v];
                    matching[v] = exposed[v];
                    v = label[v];
                }

                // v is the first entry of improving path.
                matching[v] = exposed[v];
                exposed[v] = unknown;
                notInStartOfEdgesOfMatching.remove(v);

                //restarting search for improving path
                Q.copy(notInStartOfEdgesOfMatching);

                for (v = notInStartOfEdgesOfMatching.firstOut();
                     v >= 0;
                     v = notInStartOfEdgesOfMatching.nextOut(v)) {
                    temp.copy(edges.getSet(v));
                    temp.andNot(endsOfEdgesOfMatching);

                    exposed[v] = temp.firstIn();
                    label[v] = unlabelled;
                }

            } else {
                // v, by definition, isn't in matching
                // width - is a start of one edges in matching
                //searching edges, which can be in matching
                for (int w = notInStartOfEdgesOfMatching.firstOut(); w >= 0; w = notInStartOfEdgesOfMatching.nextOut(w))
                {
                    //if(v == matching[width]) ==> edges.getRelationAt(v, matching[width]) == false);
                    //if(v==width) ==> edges.getRelationAt(v, matching[width]) == false
                    if (label[w] == unlabelled
                            && matching[w] >= 0
                            && edges.getRelationAt(v, matching[w])) {
                        //this mean, that edge (width, matching[width])	in matching can be replaced by edge (v, matching[width])
                        Q.put(w);
                        label[w] = v;
                    }
                }
            }
        }

        assignChainNumbersForEntities(chains, order, reducibles, endsOfEdgesOfMatching, matching);
    }

    private void computeDiagram() {
        int size = getDecompositionStrategy().getEntitiesCount();

        chains = new int[size];
        vectorsX = new double[size];
        vectorsY = new double[size];

        BinaryRelation order = getDecompositionStrategy().computeEntitiesOrder();

        computeChainDecomposition(order);

        calcConceptsPlacement();

    }

    private void computeVectors() {

        int max = findChainCount();
        int sign = getDecompositionStrategy().getYSign();
        for (int i = 0; i < chains.length; i++) {
            if (chains[i] < 0) {
                vectorsX[i] = 0;
                vectorsY[i] = 0;
            } else {
                vectorsX[i] = stretch * getPlacementStrategy().calcXCoord(base, chains[i], max);
                vectorsY[i] = sign * getPlacementStrategy().calcYCoord(base, chains[i], max);
            }
        }
    }

    private int findChainCount() {
        int max = 0;
        for (int i = chains.length; --i >= 0;) {
            if (max < chains[i]) {
                max = chains[i];
            }
        }
        return max;
    }

    private Set findReducibleEntities(BinaryRelation order) {
        int size = order.getRowCount();

        ModifiableSet reducibles = ContextFactoryRegistry.createSet(size);

        for (int v = 0; v < size; v++) {

            boolean isReducible = !getDecompositionStrategy().isEntityIrreducible(v);

            for (int w = order.getSet(v).firstIn();
                 w < v && w != -1;
                 w = order.getSet(v).nextIn(w)) {
                isReducible = isReducible || order.getRelationAt(w, v);
            }

            if (isReducible) {
                reducibles.put(v);
            }
        }
        return reducibles;
    }

    public synchronized StrategyValueItem getConceptPlacementStrategyItem() {
        if (null == conceptPlacementStrategyItem) {
            conceptPlacementStrategyItem = new StrategyValueItem(CONCEPT_PLACEMENT_EVENT, new ConceptPlacementStrategyModel(), getPropertyChange());
        }
        return conceptPlacementStrategyItem;
    }

    public synchronized StrategyValueItem getDecompositionStrategyItem() {
        if (null == decompositionStrategyItem) {
            decompositionStrategyItem = new StrategyValueItem(DECOMPOSITION_STRATEGY_EVENT, new ChainDecompositionStrategyModel(), getPropertyChange());
        }
        return decompositionStrategyItem;
    }

    private void rotateChainsLeft() {
        int max = findChainCount();
        for (int i = chains.length; --i >= 0;) {
            if (chains[i] < 0) {
                continue;
            }
            chains[i]--;
            if (chains[i] < 0) {
                chains[i] = max;
            }
        }
        calcConceptsPlacement();
    }

    private void rotateChainsRight() {
        int max = findChainCount();
        for (int i = chains.length; --i >= 0;) {
            if (chains[i] < 0) {
                continue;
            }
            chains[i]++;
            if (chains[i] > max) {
                chains[i] = 0;
            }
        }
        calcConceptsPlacement();
    }
}
