/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.relationsequences;

import conexp.core.BinaryRelation;



public class PercentFilledRelationGenerationStrategy extends ParametricRelationGenerationStrategy {


    protected double fillPercent;

    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 13:51:23)
     */
    public PercentFilledRelationGenerationStrategy(int minSizeX, int maxSizeX, int minSizeY, int maxSizeY, int count, double fillFactor) {
        super(minSizeX, maxSizeX, minSizeY, maxSizeY, count);
        this.fillPercent = fillFactor;
        createRelations();
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 16:23:41)
     *
     * @return java.lang.String
     */
    public String describeStrategy() {
        return super.describeStrategy() + "fillFactor;" + fillPercent;
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 13:50:59)
     *
     * @param relNo int
     * @return conexp.core.BinaryRelation
     */
    public BinaryRelation makeRelation(int relNo) {
        return RelationGenerator.makeFilledWithPercent(calcRelationSizeX(relNo), calcRelationSizeY(relNo), (float) fillPercent);
    }
}
