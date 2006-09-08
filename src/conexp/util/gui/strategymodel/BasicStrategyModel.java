/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.strategymodel;

import conexp.util.GenericStrategy;
import conexp.util.exceptions.ConfigFatalError;
import util.StringUtil;

import java.util.Arrays;

public abstract class BasicStrategyModel implements StrategyModel {
    public int findStrategyIndex(String strategyName) {
        int strategyCount = getStrategiesCount();
        for (int i = 0; i < strategyCount; i++) {
            if (getStrategyName(i).equals(strategyName)) {
                return i;
            }
        }
        return -1;
    }

    protected String makeStrategyNameFromStrategy(GenericStrategy genericstrategy) {
        return StringUtil.extractClassName(genericstrategy.getClass().toString());
    }

    protected static GenericStrategy makeGenericStrategyByClassName(String className) throws ConfigFatalError {
        GenericStrategy genericStrategy;
        try {
            genericStrategy = (GenericStrategy) Class.forName(className).newInstance();
        } catch (InstantiationException ex) {
            throw new ConfigFatalError("Error instantiation class " + className);
        } catch (ClassNotFoundException ex) {
            throw new ConfigFatalError("Error finding class " + className);
        } catch (IllegalAccessException ex) {
            throw new ConfigFatalError("Error accessing class " + className);
        }
        return genericStrategy;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StrategyModel)) {
            return false;
        }
        StrategyModel other = (StrategyModel) obj;
        if (!this.getClass().equals(other.getClass())) {
            return false;
        }
        final int strategiesCount = getStrategiesCount();
        if (strategiesCount != other.getStrategiesCount()) {
            return false;
        }
        for (int i = strategiesCount; --i >= 0;) {
            if (!getStrategy(i).equals(other.getStrategy(i))) {
                return false;
            }
            if (!getStrategyName(i).equals(other.getStrategyName(i))) {
                return false;
            }
        }
        if (!Arrays.equals(getStrategyDescription(), other.getStrategyDescription())) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return getStrategiesCount();
    }

}
