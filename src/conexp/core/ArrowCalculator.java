/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core;


public interface ArrowCalculator extends BinaryRelationProcessor {

    public ArrowCalculator makeNew();

    public void calcDownArrow(ModifiableBinaryRelation downArrowRel);

    public void calcUpArrow(ModifiableBinaryRelation upArrow);
}
