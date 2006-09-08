/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.ruleview;

import conexp.core.AttributeInformationSupplier;
import conexp.core.Dependency;

public interface RuleRenderer {
    /**
     * Insert the method's description here.
     * Creation date: (05.05.01 1:58:17)
     *
     * @param dep conexp.core.Dependency
     * @return javax.swing.text.SimpleAttributeSet
     */
    javax.swing.text.SimpleAttributeSet dependencyStyle(Dependency dep);


    /**
     * Insert the method's description here.
     * Creation date: (05.05.01 1:55:11)
     *
     * @param dep conexp.core.Dependency
     * @return java.lang.String
     */
    void describeRule(StringBuffer buf, AttributeInformationSupplier attrInfo, Dependency dep);


    /**
     * Insert the method's description here.
     * Creation date: (06.05.01 21:21:57)
     *
     * @return javax.swing.text.SimpleAttributeSet
     */
    javax.swing.text.SimpleAttributeSet getBaseStyle();


}
