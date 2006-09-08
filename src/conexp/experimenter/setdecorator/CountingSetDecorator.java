/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.setdecorator;

import conexp.core.IPartiallyOrdered;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import util.Assert;



public class CountingSetDecorator implements ModifiableSet {

    public CountingSetDecorator(ModifiableSet inner, OperationStatistic statistic) {
        Assert.isTrue(!(inner instanceof CountingSetDecorator));
        this.inner = inner;
        this.statistic = statistic;
    }

    private ModifiableSet inner;

    private ModifiableSet getInner() {
        return inner;
    }

    private OperationStatistic statistic;

    public void put(int elId) {
        statistic.register(OperationCodes.PUT_OPERATION);
        inner.put(elId);
    }

    public void remove(int elId) {
        statistic.register(OperationCodes.REMOVE_OPERATION);
        inner.remove(elId);
    }

    public void copy(Set set) {
        statistic.register(OperationCodes.COPY);
        inner.copy(getWorkingSet(set));
    }

    public void and(Set set) {
        statistic.register(OperationCodes.COPY);
        inner.and(getWorkingSet(set));
    }

    public void andNot(Set set) {
        statistic.register(OperationCodes.AND_NOT_OPERATION);
        inner.andNot(getWorkingSet(set));
    }

    public void clearSet() {
        statistic.register(OperationCodes.CLEAR_SET_OPERATION);
        inner.clearSet();
    }

    public void fill() {
        statistic.register(OperationCodes.FILL_OPERATION);
        inner.fill();
    }

    public void or(Set set) {
        statistic.register(OperationCodes.OR_OPERATION);
        inner.or(getWorkingSet(set));
    }

    private Set getWorkingSet(Set set) {
        Set toOperateOn = set;
        if (set instanceof CountingSetDecorator) {
            CountingSetDecorator decorator = (CountingSetDecorator) set;
            toOperateOn = decorator.getInner();
        }
        return toOperateOn;
    }

    public void resize(int newSize) {
        statistic.register(OperationCodes.RESIZE_OPERATION);
        inner.resize(newSize);
    }

    public void exclude(int elId) {
        statistic.register(OperationCodes.EXCLUDE_OPERATION);
        inner.exclude(elId);
    }

    public void append(Set addition) {
        statistic.register(OperationCodes.APPEND_OPERATION);
        inner.append(getWorkingSet(addition));
    }

    public int compare(Set other) {
        statistic.register(OperationCodes.COMPARE_OPERATION);
        return inner.compare(getWorkingSet(other));
    }

    public int lexCompareGanter(Set set) {
        statistic.register(OperationCodes.LEX_COMPARE_GANTER);
        return inner.lexCompareGanter(getWorkingSet(set));
    }

    /* return maximal number of elements in set*/
    public int size() {
        statistic.register(OperationCodes.SIZE);
        return inner.size();
    }

    public int elementCount() {
        statistic.register(OperationCodes.ELEMENT_COUNT);
        return inner.elementCount();
    }

    public boolean in(int Id) {
        statistic.register(OperationCodes.IN);
        return inner.in(Id);
    }

    public boolean out(int index) {
        statistic.register(OperationCodes.OUT);
        return inner.out(index);
    }

    public int firstIn() {
        statistic.register(OperationCodes.FIRST_IN);
        return inner.firstIn();
    }

    public int nextIn(int prev) {
        statistic.register(OperationCodes.NEXT_IN);
        return inner.nextIn(prev);
    }

    public int firstOut() {
        statistic.register(OperationCodes.FIRST_OUT);
        return inner.firstOut();
    }

    public int nextOut(int prev) {
        statistic.register(OperationCodes.NEXT_OUT);
        return inner.nextOut(prev);
    }

    public int length() {
        statistic.register(OperationCodes.LENGTH);
        return inner.length();
    }


    public int outUpperBound() {
        statistic.register(OperationCodes.OUT_UPPER_BOUND);
        return inner.outUpperBound();
    }

    public boolean intersects(Set other) {
        statistic.register(OperationCodes.INTERSECTS);
        return inner.intersects(getWorkingSet(other));
    }

    public boolean isEquals(Set obj) {
        statistic.register(OperationCodes.IS_EQUALS);
        return inner.isEquals(getWorkingSet(obj));
    }

    public boolean isSupersetOf(Set other) {
        statistic.register(OperationCodes.IS_SUPERSET_OF);
        return inner.isSupersetOf(getWorkingSet(other));
    }

    public boolean isSubsetOf(Set s) {
        statistic.register(OperationCodes.IS_SUBSET_OF);
        return inner.isSubsetOf(getWorkingSet(s));
    }

    public boolean isEmpty() {
        statistic.register(OperationCodes.IS_EMPTY);
        return inner.isEmpty();
    }

    public boolean isLesserThan(IPartiallyOrdered other) {
        statistic.register(OperationCodes.IS_LESSER_THAN_PART_ORDERED);
        return inner.isLesserThan(getWorkingPartiallyOrdered(other));
    }

    private Set getWorkingPartiallyOrdered(IPartiallyOrdered other) {
        return getWorkingSet((Set) other);
    }

    public boolean isEqual(IPartiallyOrdered other) {
        statistic.register(OperationCodes.IS_EQUAL_PART_ORDERED);
        return inner.isEqual(getWorkingPartiallyOrdered(other));
    }

    //todo: what about equal ???

    public int hashCode() {
        statistic.register(OperationCodes.HASH_CODE_CALL);
        return inner.hashCode();
    }

    public String toString() {
        return inner.toString();
    }

    public Object clone() {
        statistic.register(OperationCodes.CLONE);
        return new CountingSetDecorator((ModifiableSet) inner.clone(), statistic);
    }

    //todo : check, that approach about approach avoids nested registration of operations
    public ModifiableSet makeModifiableSetCopy() {
        statistic.register(OperationCodes.MAKE_MODIFIABLE_COPY);
        return new CountingSetDecorator(inner.makeModifiableSetCopy(), statistic);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CountingSetDecorator)) {
            return inner.equals(obj);
        }

        final CountingSetDecorator countingSetDecorator = (CountingSetDecorator) obj;
        if (!inner.equals(countingSetDecorator.inner)) {
            return false;
        }
        return true;
    }
}
