package conexp.frontend.ruleview;

import conexp.core.DependencySet;
import conexp.frontend.DependencySetConsumer;
import conexp.frontend.DependencySetSupplier;
import conexp.frontend.ViewChangePanel;

import javax.swing.*;
import java.awt.BorderLayout;

/**
 * Insert the type's description here.
 * Creation date: (07.05.01 21:45:58)
 * @author
 */
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


    protected abstract RulePane makeRulePane();

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
}