/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview;

import conexp.core.AttributeInformationSupplier;
import conexp.core.Dependency;


public class AssociationRuleSupportRenderer extends AssociationRuleRenderer {

    /**
     * ImplicationRenderer constructor comment.
     */
    public AssociationRuleSupportRenderer() {
        super();
    }


    /**
     * Insert the method's description here.
     * Creation date: (06.05.01 20:51:10)
     *
     * @param dep conexp.core.Dependency
     */
    public void describeRule(StringBuffer buf, AttributeInformationSupplier attrInfo,
                             Dependency dep) {
        generateSupport(buf, dep.getPremiseSupport());
        describeSet(buf, attrInfo, dep.getPremise());
        buf.append(FOLLOW);
        generateSupport(buf, dep.getRuleSupport());
        describeSet(buf, attrInfo, dep.getConclusion());
        buf.append(END_MARK);
    }
}
