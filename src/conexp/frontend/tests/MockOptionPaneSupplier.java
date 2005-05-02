/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.tests;

import conexp.frontend.OptionPaneSupplier;

import javax.swing.*;

class MockOptionPaneSupplier implements OptionPaneSupplier {
    public JComponent getOptionsPane() {
        return new JPanel();
    }
}
