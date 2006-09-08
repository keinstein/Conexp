/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview;

import conexp.core.AttributeInformationSupplier;
import conexp.core.AttributeInformationSupplierUtil;
import conexp.core.Set;
import conexp.util.GenericStrategy;

import javax.swing.text.SimpleAttributeSet;


public abstract class GenericRuleRenderer implements RuleRenderer, GenericStrategy {
    protected SimpleAttributeSet[] attrs;
    private SimpleAttributeSet baseStyle;

    protected static final String FOLLOW = " ==> ";

    protected static final String END_MARK = ";";

    /**
     * ImplicationRenderer constructor comment.
     */
    public GenericRuleRenderer() {
        super();
        updateDependentStyles();
    }


    /**
     * Insert the method's description here.
     * Creation date: (07.05.01 21:35:20)
     */
    protected void generateSupport(StringBuffer buf, int support) {
        buf.append("< " + support + " > ");
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.05.01 21:27:42)
     *
     * @return javax.swing.text.SimpleAttributeSet
     */
    public javax.swing.text.SimpleAttributeSet getBaseStyle() {
        if (null == baseStyle) {
            baseStyle = new SimpleAttributeSet();
        }
        return baseStyle;
    }


    protected static void describeSet(StringBuffer buffer, AttributeInformationSupplier attrInfo, Set set) {
        AttributeInformationSupplierUtil.describeSet(buffer, attrInfo, set, " ", "{ }");
    }

    /**
     * Insert the method's description here.
     * Creation date: (06.05.01 20:31:58)
     */
    protected abstract void updateDependentStyles();
}
