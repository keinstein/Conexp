package conexp.experimenter.experiments;

import conexp.core.*;
import conexp.core.calculationstrategies.DepthSearchCalculator;
import conexp.core.calculationstrategies.NextClosedSetCalculator;
import research.conexp.core.calculationstrategies.AddAtomCalcStrategy;
import conexp.core.enumcallbacks.NextClosedSetLatticeBuilderCallback;
import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;
import conexp.experimenter.experiments.ConceptSetExperiment;

/**
 * Insert the type's description here.
 * Creation date: (21.07.01 13:46:49)
 * @author
 */
public class LatticeBuildExperiment extends ConceptSetExperiment {

    public LatticeBuildExperiment(String strategyName) {
        super(strategyName);
    }

    protected void doLocalSetup() {
        super.doLocalSetup();
        if (strategy instanceof NextClosedSetCalculator) {

            ConceptEnumCallback callback = new NextClosedSetLatticeBuilderCallback((Lattice) coll);
            ((NextClosedSetCalculator) strategy).setCallback(callback);
            return;
        }
        if (strategy instanceof ConceptSetNeedingCalcStrategy) {
            ConceptSetNeedingCalcStrategy calcStrategy = (ConceptSetNeedingCalcStrategy) strategy;
            calcStrategy.setConceptSet((ConceptsCollection) coll);
            return;
        }
        if (strategy instanceof DepthSearchCalculator) {
            DepthSearchCalculator calc = (DepthSearchCalculator) strategy;
            calc.setLattice((Lattice) coll);
            return;
        }
        if(strategy instanceof AddAtomCalcStrategy){
           AddAtomCalcStrategy calc = (AddAtomCalcStrategy) strategy;
            calc.setLattice((Lattice)coll);
            return;
        }

    }

    protected static final String EDGE_COUNT = "Edge count";

    protected void declareMeasures(MeasurementProtocol protocol) {
        super.declareMeasures(protocol);
        protocol.addMeasurement(makeValidatingMeasurement(EDGE_COUNT));
    }

    int getEdgeCount() {
        return ((Lattice) coll).edgeCount();
    }

    public void saveResults(MeasurementSet results) {
        super.saveResults(results);
        results.setMeasurement(EDGE_COUNT, getEdgeCount());
    }

    protected Object makeConceptsCollection() {
        return new Lattice();
    }

    public void perform() {
        ((LatticeCalcStrategy) strategy).buildLattice();
    }
}