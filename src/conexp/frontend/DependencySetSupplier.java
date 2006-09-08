/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend;

import conexp.core.DependencySet;
import conexp.util.gui.paramseditor.ParamsProvider;
import util.PropertyChangeSupplier;

public interface DependencySetSupplier extends PropertyChangeSupplier, ParamsProvider {

    String RULE_SET_PROPERTY = "RULE_SET";
    String RULE_SET_CLEARED = "RULE_SET_CLEARED";

    boolean isComputed();

    DependencySet getDependencySet();

    void clearDependencySet();
}
