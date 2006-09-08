/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview;

import conexp.core.AttributeInformationSupplier;
import util.StringUtil;


public class AssociationRuleConfidenceRenderer extends AssociationRuleRenderer {
    /**
     * AssociationRuleConfidenceRenderer constructor comment.
     */
    public AssociationRuleConfidenceRenderer() {
        super();
    }


    /**
     * Creation date: (09.05.01 9:30:19)
     *
     * @param dep conexp.core.Dependency
     */
    public void describeRule(StringBuffer buf, AttributeInformationSupplier attrInfo, conexp.core.Dependency dep) {
        generateSupport(buf, dep.getPremiseSupport());
        describeSet(buf, attrInfo, dep.getPremise());
        buf.append(" =[");
        buf.append(StringUtil.formatPercents(dep.getConfidence()));
        buf.append("]=> ");
        generateSupport(buf, dep.getRuleSupport());
        describeSet(buf, attrInfo, dep.getConclusion());
        buf.append(END_MARK);
    }
}
