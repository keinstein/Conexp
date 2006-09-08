/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.experimenter.experiments;

import conexp.core.BinaryRelation;
import conexp.core.ImplicationCalcStrategy;
import conexp.core.ImplicationSet;
import conexp.core.tests.MockAttributeInformationSupplier;
import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;

public abstract class ImplicationSetExperiment extends BasicExperiment {
    protected ImplicationCalcStrategy implicationCalcStrategy;
    protected ImplicationSet implications;

    protected Object getActionObject() {
        return implicationCalcStrategy;
    }

    public void setUp(BinaryRelation rel) {
        implicationCalcStrategy = makeImplicationsCalcStrategy();
        implicationCalcStrategy.setRelation(rel);
        implications = new ImplicationSet(new MockAttributeInformationSupplier(rel.getColCount()));
        implicationCalcStrategy.setImplications(implications);
    }

    protected abstract ImplicationCalcStrategy makeImplicationsCalcStrategy();

    public void perform() {
        implicationCalcStrategy.calcImplications();
    }

    protected static final String BASE_SIZE = "Size of implication base";
    protected static final String IMPLICATION_BASE = "Implications";

    protected void declareMeasures(MeasurementProtocol protocol) {
        super.declareMeasures(protocol);
        protocol.addMeasurement(makeValidatingMeasurement(BASE_SIZE));
//        protocol.addMeasurement(makeValidatingMeasurement(IMPLICATION_BASE));
    }

    public void saveResults(MeasurementSet results) {
        super.saveResults(results);
        results.setMeasurement(BASE_SIZE, getBaseSize());
//        results.setMeasurement(IMPLICATION_BASE, implications);
    }

    private int getBaseSize() {
        return implications.getSize();
    }

    public void tearDown() {
        implications = null;
        implicationCalcStrategy.tearDown();
    }
}
