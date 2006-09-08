package conexp.experimenter.experiments;

import conexp.core.Lattice;
import conexp.core.LatticeCalcStrategy;
import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;

/**
 * Insert the type's description here.
 * Creation date: (21.07.01 13:46:49)
 *
 * @author
 */
public class LatticeBuildExperiment extends ConceptSetExperiment {

    public LatticeBuildExperiment(String strategyName) {
        super(strategyName);
    }

    protected void doLocalSetup() {
        super.doLocalSetup();
        LatticeCalcStrategy calc = (LatticeCalcStrategy) strategy;
        calc.setLattice((Lattice) coll);
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