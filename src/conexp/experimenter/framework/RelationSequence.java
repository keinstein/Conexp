/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.framework;

import conexp.core.BinaryRelation;


public interface RelationSequence {

    /**
     * Called to fill in the information about relation with index equal to relationIndex into
     * MeasurementSet result
     *
     * @param relationIndex
     * @param result
     */


    void fillInMeasurementSet(int relationIndex, MeasurementSet result);


    /**
     * Returns the measurements, that are provided for description of relation
     *
     * @return IMeasurementProtocol
     */
    IMeasurementProtocol getMeasurementProtocol();

    /**
     * Provides information about sequence of relations. Used for desription purposes
     * Creation date: (06.07.01 16:23:41)
     *
     * @return java.lang.String
     */
    String describeStrategy();

    /**
     * Returns the next binary relation, on which experiment should be performed
     * Creation date: (21.07.01 11:47:09)
     *
     * @param relationIndex - index of relation in sequence
     * @return conexp.core.BinaryRelation
     */
    BinaryRelation getRelation(int relationIndex);


    /**
     * Returns the amount of relations in sequence.
     * Creation date: (21.07.01 11:46:39)
     *
     * @return int
     */
    int getRelationCount();
}
