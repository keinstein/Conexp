package conexp.util.gui.strategymodel;

import conexp.util.GenericStrategy;

/**
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: 8/4/2005
 * Time: 16:27:47
 * To change this template use File | Settings | File Templates.
 */
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

    public abstract String[][] getCreateInfo();
}