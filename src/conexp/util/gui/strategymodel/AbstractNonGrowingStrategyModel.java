/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.util.gui.strategymodel;

import conexp.util.GenericStrategy;


public abstract class AbstractNonGrowingStrategyModel extends NonGrowingStrategyModel {
    protected AbstractNonGrowingStrategyModel() {
        this(true);
    }

    protected AbstractNonGrowingStrategyModel(boolean createStrategies) {
        if (createStrategies) {
            createStrategies();
        }
    }

    protected void createStrategies() {
        String[][] createInfo = getCreateInfo();
        allocateStrategies(createInfo.length);
        for (int i = 0; i < createInfo.length; i++) {
            GenericStrategy genericStrategy = makeGenericStrategyByClassName(createInfo[i][2]);

            setStrategy(i,
                    createInfo[i][1],
                    createInfo[i][0],
                    genericStrategy);


        }

    }

    protected abstract String[][] getCreateInfo();
}
