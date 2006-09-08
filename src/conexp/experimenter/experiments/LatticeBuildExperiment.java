/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.experiments;

import conexp.core.Lattice;
import conexp.core.LatticeCalcStrategy;
import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;


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
