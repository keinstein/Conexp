package conexp.frontend.ruleview;

import conexp.core.AttributeInformationSupplier;
import conexp.core.Dependency;

public interface RuleRenderer {
    /**
     * Insert the method's description here.
     * Creation date: (05.05.01 1:58:17)
     * @return javax.swing.text.SimpleAttributeSet
     * @param dep conexp.core.Dependency
     */
    javax.swing.text.SimpleAttributeSet dependencyStyle(Dependency dep);


    /**
     * Insert the method's description here.
     * Creation date: (05.05.01 1:55:11)
     * @return java.lang.String
     * @param dep conexp.core.Dependency
     */
    void describeRule(StringBuffer buf, AttributeInformationSupplier attrInfo, Dependency dep);


    /**
     * Insert the method's description here.
     * Creation date: (06.05.01 21:21:57)
     * @return javax.swing.text.SimpleAttributeSet
     */
    javax.swing.text.SimpleAttributeSet getBaseStyle();


    /**
     * Insert the method's description here.
     * Creation date: (06.05.01 20:25:51)
     */
    void setFontParams(String family, int size);
}