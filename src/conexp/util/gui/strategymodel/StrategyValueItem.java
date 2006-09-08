/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.util.gui.strategymodel;

import com.visibleworkings.trace.Trace;
import conexp.util.GenericStrategy;

import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import java.util.prefs.Preferences;

public class StrategyValueItem implements ActionListener {

    private volatile int value;
    private final String propertyName;
    private final StrategyModel model;
    private transient PropertyChangeSupport propertyChange;

    public StrategyValueItem(String propertyName, StrategyModel strategymodel, PropertyChangeSupport propertychangesupport) {
        model = strategymodel;
        this.propertyName = propertyName;
        propertyChange = propertychangesupport;
    }

    public void actionPerformed(ActionEvent actionevent) {
        JComboBox jcombobox = (JComboBox) actionevent.getSource();
        int i = jcombobox.getSelectedIndex();
        if (-1 != i) {
            setValue(i);
        }
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String[] getDescription() {
        return model.getStrategyDescription();
    }

    public void setValue(int newValue) {
        if (newValue < 0 || newValue >= model.getStrategiesCount()) {
            Trace.gui.errorm("attempt to set bad value " + newValue + " for property " + propertyName + "]", this);
            return;
        }
        if (newValue != value) {
            int oldValue = value;
            value = newValue;
            Trace.gui.debugm("set value for strategymodel for property " + propertyName + "=[" + getDescription()[value] + " , " + value + "]", this);
            getPropertyChange().firePropertyChange(propertyName, model.getStrategy(oldValue), model.getStrategy(newValue));
        }
    }

    public synchronized GenericStrategy getStrategy() {
        return model.getStrategy(value);
    }

    public int getValue() {
        return value;
    }

    public String getValueDescription() {
        return getDescription()[value];
    }

    public String getStrategyKey() {
        return model.getStrategyName(value);
    }

    public int findStrategyByKey(String key) {
        return model.findStrategyIndex(key);
    }

    public synchronized java.beans.PropertyChangeSupport getPropertyChange() {
        if (null == propertyChange) {
            propertyChange = new java.beans.PropertyChangeSupport(this);
        }
        return propertyChange;
    }

    public boolean setValueByKey(String key) {
        int index = findStrategyByKey(key);
        if (-1 == index) {
            return false;
        }
        setValue(index);
        return true;
    }

    public String toString() {
        return "StrategyValueItem[" + getPropertyName() + ":" + getValueDescription() + "]";
    }

    public void restoreFromPreferences(Preferences preferences) {
        setValueByKey(preferences.get(getPropertyName(), ""));
    }

    public void storeToPreferences(Preferences preferences) {
        preferences.put(getPropertyName(), getStrategyKey());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StrategyValueItem)) {
            return false;
        }

        final StrategyValueItem strategyValueItem = (StrategyValueItem) obj;

        if (value != strategyValueItem.value) {
            return false;
        }
        if (!model.equals(strategyValueItem.model)) {
            return false;
        }
        if (!propertyName.equals(strategyValueItem.propertyName)) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = value;
        result = 29 * result + propertyName.hashCode();
        result = 29 * result + model.hashCode();
        return result;
    }

}
