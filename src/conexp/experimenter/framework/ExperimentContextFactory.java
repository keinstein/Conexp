package conexp.experimenter.framework;

import conexp.core.ContextFactory;
import conexp.core.ModifiableBinaryRelation;
import conexp.core.ModifiableSet;
import conexp.core.SetRelation;
import conexp.core.bitset.BitSet;
import conexp.experimenter.setdecorator.CountingSetDecorator;
import conexp.experimenter.setdecorator.OperationCountHolder;
import conexp.experimenter.setdecorator.OperationStatistic;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 26/8/2003
 * Time: 17:21:51
 */

public class ExperimentContextFactory implements ContextFactory {
    private OperationStatistic statistic = new OperationStatistic();

    public ModifiableBinaryRelation createRelation(int rowCount, int colCount) {
        return new SetRelation(rowCount, colCount);
    }

    public ModifiableSet createSet(int size) {
        return new CountingSetDecorator(new BitSet(size), statistic);
    }

    public OperationCountHolder getSnapshot() {
        return statistic.makeCopy();
    }

    public void resetStatictics() {
        statistic.clear();
    }
}
