package conexp.experimenter.relationsequences;

import conexp.core.BinaryRelation;
import conexp.core.BinaryRelationUtils;
import conexp.experimenter.framework.IMeasurementProtocol;
import conexp.experimenter.framework.MeasurementProtocol;
import conexp.experimenter.framework.MeasurementSet;
import conexp.experimenter.framework.RelationSequence;

/**
 * Insert the type's description here.
 * Creation date: (06.07.01 13:50:59)
 * @author
 */
public abstract class BaseRelationGenerationStrategy implements RelationSequence {


    protected int count;

    protected conexp.core.BinaryRelation[] relations;

    protected MeasurementProtocol measurementProtocol;

    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 13:50:59)
     * @return conexp.core.BinaryRelation
     * @param relNo int
     */
    public abstract conexp.core.BinaryRelation makeRelation(int relNo);


    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 13:51:23)
     */
    public BaseRelationGenerationStrategy(int count) {
        this.count = count;
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 17:58:34)
     */
    protected void createRelations() {
        relations = new BinaryRelation[count];
        for (int i = 0; i < count; i++) {
            relations[i] = makeRelation(i);
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 12:25:29)
     * @return java.lang.String
     */
    public java.lang.String describeRelation(int i) {
        return conexp.core.BinaryRelationUtils.describeRelation(relations[i]);
    }


    protected MeasurementProtocol makeMeasurementProtocol() {
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
     * @return conexp.core.BinaryRelation
     * @param i int
     */
    public conexp.core.BinaryRelation getRelation(int i) {
        return relations[i];
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 12:25:29)
     * @return int
     */
    public int getRelationCount() {
        return count;
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 18:04:23)
     * @param relNo int
     */
    public int interpolateSize(int relNo, int min, int max) {
        return min + ((count > 1) ? (max - min) * relNo / (count - 1) : 0);
    }
}