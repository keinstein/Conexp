package conexp.frontend.ruleview;

import conexp.frontend.DependencySetSupplier;
import conexp.frontend.ResourceLoader;

import javax.swing.*;
import java.util.ResourceBundle;


/**
 * Insert the type's description here.
 * Creation date: (18.11.00 9:35:47)
 * @author
 */
public class ImplicationsView extends GenericRuleView {


    private static ResourceBundle resources;

    //---------------------------------------------------------------
    /**
     *  Description of the Class
     *
     *@author     Sergey
     *@created    8 Èþíü 2000 ã.
     */
    static {
        resources = ResourceLoader.getResourceBundle("conexp/frontend/resources/RuleViewPanel");
    }

    /**
     * RuleView constructor comment.
     * @param doc conexp.frontend.ContextDocument
     */
    public ImplicationsView(DependencySetSupplier doc, ActionMap parentActionChain) {
        super(doc, parentActionChain);
    }

    /**
     * Insert the method's description here.
     * Creation date: (23.11.00 0:11:35)
     * @return javax.swing.Action[]
     */
    public Action[] getActions() {
        Action[] ret = {new SortBySupportAction()};
        return ret;
    }

    /**
     * Insert the method's description here.
     * Creation date: (22.11.00 23:19:07)
     * @return java.util.ResourceBundle
     */
    public ResourceBundle getResources() {
        return resources;
    }

    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 21:45:04)
     */
    protected RulePane makeRulePane() {
        return new RulePane(getDependencySet(), new ImplicationRenderer(), resources.getString("NoRulesInBaseMsg"));
    }


    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 22:08:33)
     */
    protected JComponent makeViewOptions() {
        return new JPanel();
    }
}