/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.util.gui.paramseditor.ParamInfo;
import conexp.util.gui.paramseditor.ParamsProvider;
import conexp.util.gui.strategymodel.StrategyModel;
import conexp.util.gui.strategymodel.StrategyValueItem;
import util.BasePropertyChangeSupplier;

import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class BasicStrategiesContext extends BasePropertyChangeSupplier implements ParamsProvider {

    public BasicStrategiesContext(java.beans.PropertyChangeSupport propertyChange) {
        this.propertyChange = propertyChange;
    }

    protected StrategyValueItem makeStrategyValueItem(String propertyName, StrategyModel model) {
        return new StrategyValueItem(propertyName, model, getPropertyChangeSupport());
    }

    Preferences preferences;


    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    private transient ParamInfo[] params;

    protected ParamInfo[] makeParamInfo() {
        return new ParamInfo[0];
    }

    synchronized public ParamInfo[] getParams() {
        if (null == params) {
            params = makeParamInfo();
        }
        return params;
    }

    public void printPreferences() {
        try {
            getPreferences().exportNode(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BackingStoreException e) {
            e.printStackTrace();  
        }
    }

}
