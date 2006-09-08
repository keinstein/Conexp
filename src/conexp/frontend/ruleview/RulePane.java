/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview;

import conexp.core.Dependency;
import conexp.core.DependencySet;
import conexp.frontend.DependencySetSupplier;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import java.util.Iterator;


public class RulePane extends TextPaneViewBase {
    protected RuleRenderer renderer;
    private DependencySetSupplier ruleSetSupplier;
    private RulePaneMessages messages;

    public RulePane(DependencySetSupplier ruleSetSupplier, RuleRenderer renderer,
                    RulePaneMessages messages) {
        super(new DefaultStyledDocument());
        this.messages = messages;
        this.renderer = renderer;
        setDependencySetSupplier(ruleSetSupplier);
    }


    public void generateContent() {
        clear();
        try {
            if (!ruleSetSupplier.isComputed()) {
                appendString(messages.getRuleSetShouldBeRecalculated() + NEW_LINE,
                        renderer.getBaseStyle());
                return;
            }
            if (getRuleSet().getSize() > 0) {
                int cnt = 0;
                Iterator iter = getRuleSet().iterator();
                StringBuffer tmp = new StringBuffer();
                while (iter.hasNext()) {
                    tmp.setLength(0);
                    Dependency dep = (Dependency) iter.next();
                    tmp.append((++cnt));
                    tmp.append(" ");
                    renderer.describeRule(tmp, getRuleSet().getAttributesInformation(), dep);
                    tmp.append(NEW_LINE);
                    appendString(tmp.toString(), renderer.dependencyStyle(dep));
                }
            } else {
                appendString(messages.getEmptyRulesetMessage() + NEW_LINE,
                        renderer.getBaseStyle());
            }
        } catch (BadLocationException ble) {
            clear();
            util.Assert.isTrue(false, "error showing ruleset");
            return;
        }

    }


    public void setDependencySetSupplier(
            DependencySetSupplier dependencySetSupplier) {
        ruleSetSupplier = dependencySetSupplier;
        generateContent();
    }

    private DependencySet getRuleSet() {
        return ruleSetSupplier.getDependencySet();
    }
}
