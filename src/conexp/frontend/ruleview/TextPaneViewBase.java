/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.ruleview;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;



public class TextPaneViewBase extends JTextPane {
    protected static String NEW_LINE = System.getProperty("line.separator");//$NON-NLS-1$

    public TextPaneViewBase(StyledDocument doc) {
        super(doc);
    }

    public TextPaneViewBase() {
    }

    protected void appendString(String str, SimpleAttributeSet attrSet) throws BadLocationException {
        getDocument().insertString(getDocument().getLength(),
                str,
                attrSet);
    }

    public void clear() {
        try {
            getDocument().remove(0, getDocument().getLength());
        } catch (BadLocationException ble) {
        }
    }
}
