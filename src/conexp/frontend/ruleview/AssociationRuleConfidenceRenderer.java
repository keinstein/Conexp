package conexp.frontend.ruleview;

import conexp.core.AttributeInformationSupplier;
import util.StringUtil;

/**
 * Insert the type's description here.
 * Creation date: (09.05.01 9:30:19)
 * @author
 */
public class AssociationRuleConfidenceRenderer extends AssociationRuleRenderer {
    /**
     * AssociationRuleConfidenceRenderer constructor comment.
     */
    public AssociationRuleConfidenceRenderer() {
        super();
    }


    /**
     * Creation date: (09.05.01 9:30:19)
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