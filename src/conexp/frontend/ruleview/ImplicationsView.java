/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview;

import conexp.frontend.DependencySetSupplier;
import conexp.frontend.ResourceLoader;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.util.ResourceBundle;

public class ImplicationsView extends GenericRuleView {


    private static ResourceBundle resources;

    //---------------------------------------------------------------
    static {
        resources = ResourceLoader.getResourceBundle("conexp/frontend/resources/RuleViewPanel");
    }

    public ImplicationsView(DependencySetSupplier doc, ActionMap parentActionChain) {
        super(doc, parentActionChain);
    }

    public Action[] getActions() {
        return new Action[]{new SortBySupportAction()};
    }

    public ResourceBundle getResources() {
        return resources;
    }


    protected GenericRuleRenderer makeRenderer() {
        return new ImplicationRenderer();
    }

    protected JComponent makeViewOptions() {
        return new JPanel();
    }
}
