package conexp.experimenter.framework;

import conexp.core.BinaryRelation;

/**
 * Insert the type's description here.
 * Creation date: (21.07.01 11:46:20)
 * @author
 */
public interface RelationSequence {

    void fillInMeasurementSet(int i, MeasurementSet res);

    IMeasurementProtocol getMeasurementProtocol();

    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 16:23:41)
     * @return java.lang.String
     */
    String describeStrategy();

    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 11:47:09)
     * @return conexp.core.BinaryRelation
     * @param i int
     */
    BinaryRelation getRelation(int i);


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 11:46:39)
     * @return int
     */
    int getRelationCount();
}