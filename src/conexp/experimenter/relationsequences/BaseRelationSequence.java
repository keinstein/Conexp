/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.relationsequences;

import conexp.core.BinaryRelation;
import conexp.core.BinaryRelationUtils;
import conexp.experimenter.framework.IMeasurementProtocol;
import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;
import conexp.experimenter.framework.RelationSequence;



public abstract class BaseRelationSequence implements RelationSequence {
    protected BinaryRelation[] relations;
    protected MeasurementProtocol measurementProtocol;

    protected static MeasurementProtocol makeMeasurementProtocol() {
        return MeasurementProtocol.buildMeasurementProtocolFromStrings(
                new String[][]{
                        {"Rows", "false"},
                        {"Cols", "false"},
                        {"Filled cells", "false"}
                }
        );
    }

    public void fillInMeasurementSet(int i, MeasurementSet res) {
        BinaryRelation rel = relations[i];
        res.setMeasurement("Rows", new Integer(rel.getRowCount()));
        res.setMeasurement("Cols", new Integer(rel.getColCount()));
        res.setMeasurement("Filled cells", new Integer(BinaryRelationUtils.calculateFilledCells(rel)));
    }

    public IMeasurementProtocol getMeasurementProtocol() {
        if (null == measurementProtocol) {
            measurementProtocol = makeMeasurementProtocol();
        }
        return measurementProtocol;
    }

    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 12:25:29)
     *
     * @param i int
     * @return conexp.core.BinaryRelation
     */
    public BinaryRelation getRelation(int i) {
        return relations[i];
    }

    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 12:25:29)
     *
     * @return int
     */
    public int getRelationCount() {
        return relations.length;
    }
}
