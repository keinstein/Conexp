/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend.ruleview;

import conexp.core.DependencySet;
import conexp.frontend.DependencySetConsumer;
import conexp.frontend.DependencySetSupplier;
import conexp.frontend.ViewChangePanel;

import javax.swing.*;
import java.awt.BorderLayout;


public abstract class GenericRuleView extends ViewChangePanel implements DependencySetConsumer {
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


    protected DependencySetSupplier getSupplier() {
        return supplier;
    }

    DependencySetSupplier supplier;

    public GenericRuleView(DependencySetSupplier doc, ActionMap parentActionChain) {
        super(parentActionChain);
        this.supplier = doc;
        rulePane = makeRulePane();
        setLayout(new BorderLayout());
        add(new JScrollPane(rulePane), BorderLayout.CENTER);
    }

    public DependencySet getDependencySet() {
        return supplier.getDependencySet();
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
        return new RulePane(getDependencySet(), makeRenderer(), getResources().getString("NoRulesInBaseMsg"));
    }

    // this two belongs to controller
    public void initialUpdate() {
        updateRules();
    }


    protected void updateRules() {
        DependencySet dependencySet = getDependencySet();
        setDependencySet(dependencySet);
    }

    public void setDependencySet(DependencySet dependencySet) {
        rulePane.setRuleSet(dependencySet);
    }

    protected abstract GenericRuleRenderer makeRenderer();
}
