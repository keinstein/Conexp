package conexp.frontend.ruleview;

import conexp.frontend.DependencySetSupplier;
import conexp.frontend.ResourceLoader;
import conexp.util.gui.paramseditor.ParamEditorTable;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.util.ResourceBundle;


/**
 * Insert the type's description here.
 * Creation date: (18.11.00 9:35:47)
 * @author Sergey
 */
public class AssociationRulesView extends GenericRuleView {

    private static ResourceBundle resources;

    static {
        resources = ResourceLoader.getResourceBundle("conexp/frontend/resources/AssociationRulesView");
    }

    public ResourceBundle getResources() {
        return resources;
    }


    /**
     * RuleView constructor comment.
     * @param doc conexp.frontend.ContextDocument
     */
    public AssociationRulesView(DependencySetSupplier doc, ActionMap parentActionChain) {
        super(doc, parentActionChain);
    }


    public Action[] getActions() {
        Action[] actions = {new SortBySupportAction()};
        return actions;
    }


    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 21:45:04)
     */
    protected RulePane makeRulePane() {
        return new RulePane(getDependencySet(), new AssociationRuleConfidenceRenderer(), resources.getString("NoRulesInBaseMsg"));
    }

    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 22:08:33)
     */
    protected JComponent makeViewOptions() {
        JScrollPane paneDrawing = new JScrollPane();
        paneDrawing.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        ParamEditorTable table = new ParamEditorTable();
        table.getParamsModel().addParams(getSupplier().getParams());
        paneDrawing.add(table);
        paneDrawing.setViewportView(table);
        return paneDrawing;
    }

}