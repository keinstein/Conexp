package conexp.experimenter.relationsequences;


/**
 * Insert the type's description here.
 * Creation date: (28.07.01 0:05:47)
 * @author
 */
public class FixedIntentSizeRelationGenerationStrategy extends ParametricRelationGenerationStrategy {
    protected int intentSize;

    /**
     * FixedIntentSizeRelationGenerationStrategy constructor comment.
     * @param minSizeX int
     * @param maxSizeX int
     * @param minSizeY int
     * @param maxSizeY int
     * @param count int
     */
    public FixedIntentSizeRelationGenerationStrategy(int minSizeX, int maxSizeX, int minSizeY, int maxSizeY, int count, int intentSize) {
        super(minSizeX, maxSizeX, minSizeY, maxSizeY, count);
        this.intentSize = intentSize;
        createRelations();
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.07.01 16:23:41)
     * @return java.lang.String
     */
    public java.lang.String describeStrategy() {
        return super.describeStrategy() + "IntentSize;" + intentSize;
    }


    /**
     * Insert the method's description here.
     * Creation date: (28.07.01 0:05:47)
     * @return conexp.core.BinaryRelation
     * @param relNo int
     */
    public conexp.core.BinaryRelation makeRelation(int relNo) {
        return RelationGenerator.makeFilledWithMaxIntent(calcRelationSizeX(relNo), calcRelationSizeY(relNo), intentSize);
    }
}