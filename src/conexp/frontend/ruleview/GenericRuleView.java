/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview;

import conexp.core.DependencySet;
import conexp.frontend.DependencySetConsumer;
import conexp.frontend.DependencySetSupplier;
import conexp.frontend.ViewChangePanel;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.ResourceBundle;


public abstract class GenericRuleView extends ViewChangePanel implements DependencySetConsumer {

    static final String RULE_SET_SHOULD_BE_RECOMPUTED_MESSAGE = "RuleSetShouldBeRecomputed";
    static final String NO_RULES_IN_BASE_MESSAGE = "NoRulesInBaseMsg";

    protected class SortBySupportAction extends AbstractAction {
        SortBySupportAction() {
            super("sortBySupport");
        }

        public void actionPerformed(java.awt.event.ActionEvent e) {
            getDependencySet().sort(DependencySupportDescComparator.getComparator());
            updateRules();
        }
    }

    protected RulePane rulePane;

    protected JComponent viewOptions;

    protected abstract JComponent makeViewOptions();

    public abstract ResourceBundle getResources();

    protected DependencySetSupplier getDependencySetSupplier() {
        return dependencySetSupplier;
    }

    DependencySetSupplier dependencySetSupplier;

    public GenericRuleView(DependencySetSupplier doc, ActionMap parentActionChain) {
        super(parentActionChain);
        this.dependencySetSupplier = doc;
        rulePane = makeRulePane();
        setLayout(new BorderLayout());
        add(new JScrollPane(rulePane), BorderLayout.CENTER);
    }

    public DependencySet getDependencySet() {
        return dependencySetSupplier.getDependencySet();
    }


    public JComponent getViewOptions() {
        if (null == viewOptions) {
            viewOptions = makeViewOptions();
        }
        return viewOptions;
    }


    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 21:45:04)
     */
    protected RulePane makeRulePane() {
        RulePaneMessages messages = makeRulePaneMessages();
        return new RulePane(dependencySetSupplier, makeRenderer(),
                messages);
    }

    /**
     * todo:sye - change visibility to package local when the project will be
     * refactored
     *
     * @return non-null implementation of RulePaneMessages
     */
    public RulePaneMessages makeRulePaneMessages() {
        RulePaneMessages messages = new RulePaneMessages() {
            public String getEmptyRulesetMessage() {
                return getLocalizedString(NO_RULES_IN_BASE_MESSAGE);
            }

            public String getRuleSetShouldBeRecalculated() {
                return getLocalizedString(RULE_SET_SHOULD_BE_RECOMPUTED_MESSAGE);
            }
        };
        return messages;
    }

    // this two belongs to controller
    public void initialUpdate() {
        updateRules();
    }


    protected void updateRules() {
        setDependencySetSupplier(dependencySetSupplier);
    }

    public void setDependencySetSupplier(
            DependencySetSupplier dependencySetSupplier) {
        rulePane.setDependencySetSupplier(dependencySetSupplier);
    }

    protected abstract GenericRuleRenderer makeRenderer();
}
