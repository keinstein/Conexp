/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.strategymodel;

import conexp.util.GenericStrategy;


public interface StrategyModel {

    int getStrategiesCount();

    GenericStrategy getStrategy(int i);

    String[] getStrategyDescription();

    int findStrategyIndex(String strategyName);

    String getStrategyName(int index);
}
