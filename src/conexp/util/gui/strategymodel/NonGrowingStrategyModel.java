package conexp.util.gui.strategymodel;

import conexp.util.GenericStrategy;


public class NonGrowingStrategyModel extends BasicStrategyModel {

    protected String strategiesDescription[];
    protected String strategiesName[];
    protected GenericStrategy strategies[];

    public NonGrowingStrategyModel() {
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