package conexp.core;

/**
 * Insert the type's description here.
 * Creation date: (22.03.01 1:08:15)
 * @author
 */
public interface ArrowCalculator extends BinaryRelationProcessor {

    public void calcDownArrow(ModifiableBinaryRelation downArrowRel);

    public void calcUpArrow(ModifiableBinaryRelation upArrow);
}