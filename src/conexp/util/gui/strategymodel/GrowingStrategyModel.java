/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.strategymodel;

import conexp.util.GenericStrategy;

import java.util.ArrayList;
import java.util.List;

public class GrowingStrategyModel extends BasicStrategyModel {

    static class StrategyInfo {
        String strategyKey;
        String strategyDescription;
        GenericStrategy strategy;

        public StrategyInfo(String strategyKey, String strategyDescription, GenericStrategy strategy) {
            this.strategyKey = strategyKey;
            assert null != strategyKey:"Strategy key shouln't be null";
            this.strategyDescription = strategyDescription;
            assert null != strategyDescription:"Strategy description shouldn't be null";
            this.strategy = strategy;
            assert null != strategy:"Strategy is expected to be not null";
        }
    }

    ;

    private List strategies = new ArrayList();

    private StrategyInfo getStrategyInfo(int index) {
        return (StrategyInfo) strategies.get(index);
    }

    public int getStrategiesCount() {
        return strategies.size();
    }

    public GenericStrategy getStrategy(int i) {
        return getStrategyInfo(i).strategy;
    }

    public String[] getStrategyDescription() {
        int strategiesCount = getStrategiesCount();
        String[] ret = new String[strategiesCount];
        for (int i = 0; i < strategiesCount; i++) {
            ret[i] = getStrategyInfo(i).strategyDescription;
        }
        return ret;
    }

    public String getStrategyName(int index) {
        return getStrategyInfo(index).strategyKey;
    }

    public void addStrategy(String key, String description, GenericStrategy strategy) {
        int index = findStrategyIndex(key);
        StrategyInfo newStrategyInfo = new StrategyInfo(key, description, strategy);
        if (-1 != index) {
            strategies.set(index, newStrategyInfo);
        } else {
            strategies.add(newStrategyInfo);
        }
    }

    public void addStrategy(String description, GenericStrategy strategy) {
        addStrategy(makeStrategyNameFromStrategy(strategy), description, strategy);
    }


}
