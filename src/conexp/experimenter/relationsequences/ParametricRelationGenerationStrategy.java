/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.relationsequences;



public abstract class ParametricRelationGenerationStrategy extends BaseRelationGenerationStrategy {
    protected int minSizeX;
    protected int maxSizeX;

    protected int minSizeY;
    protected int maxSizeY;


    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 16:23:41)
     *
     * @return java.lang.String
     */
    public String describeStrategy() {
        return "MinSize X;" + minSizeX + ";MaxSizeX;" + maxSizeX + ";minSizeY;" + minSizeY + ";maxSizeY;" + maxSizeY + ';';
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 13:51:23)
     */
    protected ParametricRelationGenerationStrategy(int minSizeX, int maxSizeX, int minSizeY, int maxSizeY, int count) {
        super(count);
        this.minSizeX = minSizeX;
        this.maxSizeX = maxSizeX;
        this.minSizeY = minSizeY;
        this.maxSizeY = maxSizeY;
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 17:28:47)
     *
     * @param relNo int
     */
    public int calcRelationSizeX(int relNo) {
        return interpolateSize(relNo, minSizeX, maxSizeX);
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 17:29:32)
     *
     * @param relNo int
     */
    public int calcRelationSizeY(int relNo) {
        return interpolateSize(relNo, minSizeY, maxSizeY);
    }
}
