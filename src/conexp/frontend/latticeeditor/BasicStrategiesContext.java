/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.latticeeditor;

import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.ParamsProvider;
import conexp.util.gui.strategymodel.StrategyModel;
import conexp.util.gui.strategymodel.StrategyValueItem;
import util.BasePropertyChangeSupplier;

public class BasicStrategiesContext extends BasePropertyChangeSupplier implements ParamsProvider {

    public BasicStrategiesContext(java.beans.PropertyChangeSupport propertyChange) {
        this.propertyChange = propertyChange;
    }

    protected StrategyValueItem makeStrategyValueItem(String label, StrategyModel model) {
        return new StrategyValueItem(label, model, getPropertyChangeSupport());
    }

    private ParamInfo[] params;

    protected ParamInfo[] makeParamInfo() {
        return new ParamInfo[0];
    }

    synchronized public ParamInfo[] getParams() {
        if (null == params) {
            params = makeParamInfo();
        }
        return params;
    }

}
