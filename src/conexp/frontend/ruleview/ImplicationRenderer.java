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


public class ImplicationRenderer extends GenericRuleRenderer {

    public ImplicationRenderer() {
        super();
    }

    public void describeRule(StringBuffer buf, AttributeInformationSupplier attrInfo, Dependency impl) {
        generateSupport(buf, impl.getRuleSupport());
        describeSet(buf, attrInfo, impl.getPremise());
        buf.append(FOLLOW);
        describeSet(buf, attrInfo, impl.getConclusion());
        buf.append(END_MARK);
    }

    static final int NON_ZERO_SUPPORT_STYLE = 0;
    static final int ZERO_SUPPORT_STYLE = 1;

    public javax.swing.text.SimpleAttributeSet dependencyStyle(conexp.core.Dependency dep) {
        return 0 == dep.getRuleSupport() ? attrs[ZERO_SUPPORT_STYLE] : attrs[NON_ZERO_SUPPORT_STYLE];
    }

    protected void updateDependentStyles() {
        attrs = new SimpleAttributeSet[2];
        attrs[NON_ZERO_SUPPORT_STYLE] = new SimpleAttributeSet(getBaseStyle());
        StyleConstants.setForeground(attrs[NON_ZERO_SUPPORT_STYLE], Color.blue);

        attrs[ZERO_SUPPORT_STYLE] = new SimpleAttributeSet(getBaseStyle());
        StyleConstants.setForeground(attrs[ZERO_SUPPORT_STYLE], Color.red);
    }
}
