/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor;

import conexp.util.GenericStrategy;


public abstract class DrawParamsIndependentStrategyModel extends conexp.frontend.latticeeditor.AbstractDrawingStrategyModel {

    public DrawParamsIndependentStrategyModel() {
        super(null);
    }


    protected DrawParamsIndependentStrategyModel(boolean unused) {
        super();
    }


    protected void createStrategies(
            DrawParameters opt) {
        String[][] createInfo = getCreateInfo();
        allocateStrategies(createInfo.length);
        for (int i = 0; i < createInfo.length; i++) {
            GenericStrategy genericStrategy = makeGenericStrategyByClassName(createInfo[i][1]);

            setStrategy(
                    i,
                    createInfo[i][0],
                    genericStrategy);


        }
    }


    public abstract String[][] getCreateInfo();
}
