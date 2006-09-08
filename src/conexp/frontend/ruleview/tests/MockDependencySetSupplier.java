/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview.tests;

import conexp.core.DependencySet;
import conexp.core.tests.MockAttributeInformationSupplier;
import conexp.frontend.DependencySetSupplier;
import conexp.util.gui.paramseditor.ParamInfo;

import java.beans.PropertyChangeListener;

class MockDependencySetSupplier implements DependencySetSupplier {
    public DependencySet getDependencySet() {
        return new DependencySet(new MockAttributeInformationSupplier(1));
    }

    public boolean isComputed() {
        return true;
    }

    public void clearDependencySet() {
    }

    public ParamInfo[] getParams() {
        return new ParamInfo[0];
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    }

}
