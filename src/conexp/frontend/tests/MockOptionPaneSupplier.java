/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.tests;

import conexp.frontend.OptionPaneSupplier;

import javax.swing.JComponent;
import javax.swing.JPanel;

class MockOptionPaneSupplier implements OptionPaneSupplier {
    public JComponent getOptionsPane() {
        return new JPanel();
    }
}
