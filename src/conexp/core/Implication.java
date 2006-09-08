/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;


public class Implication extends GenericDependency implements Cloneable {

    public Implication(Set premise, Set conclusion) {
        this(premise, conclusion, 0);
    }

    public Implication(Set premise, Set conclusion, int numObjects) {
        super(premise, conclusion, numObjects, numObjects);
    }

    public Object clone() {
        return new Implication((Set) premise.clone(), (Set) conclusion.clone(), getObjectCount());
    }

    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (!(obj instanceof Implication)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Implication that = (Implication) obj;
        return premise.equals(that.premise) && conclusion.equals(that.conclusion) &&
                getCharacteristics().equals(that.getCharacteristics());

    }

    public int getObjectCount() {
        return getRuleSupport();
    }

    public boolean isDisjoint() {
        return !conclusion.intersects(premise);
    }

    public boolean isEmptyObjectSet() {
        return 0 == getObjectCount();
    }

    public void makeDisjoint() {
        conclusion.andNot(premise);
    }

    public boolean isRespectedBySet(Set set) {
        if (!premise.isSubsetOf(set)) {
            return true;
        }
        return conclusion.isSubsetOf(set);
    }

    public ModifiableSet getModifiablePremise() {
        return premise;
    }

}
