/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.strategymodel;

import conexp.util.GenericStrategy;


public class NonGrowingStrategyModel extends BasicStrategyModel {

    private String[] strategiesDescription;
    private String[] strategiesName;
    private GenericStrategy[] strategies;

    protected NonGrowingStrategyModel() {
    }

    protected void allocateStrategies(int i) {
        strategies = new GenericStrategy[i];
        strategiesDescription = new String[i];
        strategiesName = new String[i];
    }

    public int getStrategiesCount() {
        return strategies.length;
    }

    public String[] getStrategyDescription() {
        return strategiesDescription;
    }

    protected void setStrategy(int i, String description, GenericStrategy genericstrategy) {
        setStrategy(i, makeStrategyNameFromStrategy(genericstrategy), description, genericstrategy);
    }


    protected void setStrategy(int i, String strategyName, String description, GenericStrategy genericstrategy) {
        strategies[i] = genericstrategy;
        strategiesDescription[i] = description;
        strategiesName[i] = strategyName;
    }

    public GenericStrategy getStrategy(int i) {
        return strategies[i];
    }

    public String getStrategyName(int index) {
        return strategiesName[index];
    }
}
