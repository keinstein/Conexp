/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.ruleview;

import conexp.core.Dependency;
import conexp.core.DependencySet;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import java.util.Iterator;


public class RulePane extends TextPaneViewBase {
    protected RuleRenderer renderer;
    protected DependencySet ruleSet;
    protected final java.lang.String emptySetMessage;

    public RulePane(DependencySet ruleSet, RuleRenderer renderer,
                    String emptyMsg) {
        super(new DefaultStyledDocument());
        emptySetMessage = emptyMsg;
        this.renderer = renderer;
        this.ruleSet = ruleSet;
        generateContent();
    }


    public void generateContent() {
        clear();
        try {
            if (ruleSet.getSize() > 0) {
                int cnt = 0;
                Iterator iter = ruleSet.iterator();
                StringBuffer tmp = new StringBuffer();
                while (iter.hasNext()) {
                    tmp.setLength(0);
                    Dependency dep = (Dependency) iter.next();
                    tmp.append((++cnt));
                    tmp.append(" ");
                    renderer.describeRule(tmp, ruleSet.getAttributesInformation(), dep);
                    tmp.append(NEW_LINE);
                    appendString(tmp.toString(), renderer.dependencyStyle(dep));
                }
            } else {
                appendString(emptySetMessage + NEW_LINE,
                        renderer.getBaseStyle());
            }
        } catch (BadLocationException ble) {
            clear();
            util.Assert.isTrue(false, "error showing ruleset");
            return;
        }

    }


    public void setRuleSet(conexp.core.DependencySet newRuleSet) {
        ruleSet = newRuleSet;
        generateContent();
    }
}
