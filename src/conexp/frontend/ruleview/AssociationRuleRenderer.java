/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview;

import conexp.core.AttributeInformationSupplier;
import conexp.core.Dependency;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;


public abstract class AssociationRuleRenderer extends GenericRuleRenderer {

    protected static final int NON_ZERO_SUPPORT_EXACT_RULE = 0;

    protected static final int INEXACT_RULE = 1;

    protected static final int ZERO_SUPPORT_EXACT_RULE = 2;

    /**
     * ImplicationRenderer constructor comment.
     */
    public AssociationRuleRenderer() {
        super();
    }

    /**
     * Insert the method's description here.
     * Creation date: (06.05.01 20:23:29)
     *
     * @param dep conexp.core.Dependency
     * @return javax.swing.text.SimpleAttributeSet
     */
    public javax.swing.text.SimpleAttributeSet dependencyStyle(conexp.core.Dependency dep) {
        if (dep.isExact()) {
            return dep.getRuleSupport() > 0 ? attrs[NON_ZERO_SUPPORT_EXACT_RULE] :
                    attrs[ZERO_SUPPORT_EXACT_RULE];
        }
        return attrs[INEXACT_RULE];
    }

    /**
     * Insert the method's description here.
     * Creation date: (06.05.01 20:31:58)
     */
    protected void updateDependentStyles() {
        attrs = new SimpleAttributeSet[3];
        attrs[NON_ZERO_SUPPORT_EXACT_RULE] = new SimpleAttributeSet(getBaseStyle());
        StyleConstants.setForeground(attrs[NON_ZERO_SUPPORT_EXACT_RULE], Color.blue);

        attrs[INEXACT_RULE] = new SimpleAttributeSet(getBaseStyle());
        StyleConstants.setForeground(attrs[INEXACT_RULE], new Color(0, 128, 0));

        attrs[ZERO_SUPPORT_EXACT_RULE] = new SimpleAttributeSet(getBaseStyle());
        StyleConstants.setForeground(attrs[ZERO_SUPPORT_EXACT_RULE], Color.red);
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.05.01 20:51:10)
     *
     * @param dep conexp.core.Dependency
     */
    abstract public void describeRule(StringBuffer buf, AttributeInformationSupplier attrInfo,
                                      Dependency dep);
}
