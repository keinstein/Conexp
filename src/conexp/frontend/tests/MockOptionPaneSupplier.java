/*
 * User: Serhiy Yevtushenko
 * Date: 08.03.2002
 * Time: 23:46:57
  */
package conexp.frontend.tests;

import conexp.frontend.OptionPaneSupplier;

import javax.swing.*;

class MockOptionPaneSupplier implements OptionPaneSupplier {
    public JComponent getOptionsPane() {
        return new JPanel();
    }
}
