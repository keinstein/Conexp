/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.frontend.contexteditor;


public class DrawArrowsStrategyModel extends conexp.util.gui.strategymodel.NonGrowingStrategyModel {
    /**
     * Insert the method's description here.
     * Creation date: (22.04.01 21:05:23)
     */
    public DrawArrowsStrategyModel() {
        super();
        createStrategies();
    }

    /**
     * Insert the method's description here.
     * Creation date: (22.04.01 21:05:01)
     */
    public void createStrategies() {
        allocateStrategies(2);
        setStrategy(0, "don't show", new SimpleContextDrawStrategy());
        setStrategy(1, "show arrow relation", new ArrowRelDrawStrategy());
    }
}
