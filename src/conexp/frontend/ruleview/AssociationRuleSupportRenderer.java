package conexp.frontend.ruleview;

import conexp.core.AttributeInformationSupplier;
import conexp.core.Dependency;


/**
 * Insert the type's description here.
 * Creation date: (06.05.01 20:20:04)
 * @author
 */
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