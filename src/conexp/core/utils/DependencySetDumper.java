/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.utils;

import conexp.core.AttributeInformationSupplier;
import conexp.core.AttributeInformationSupplierUtil;
import conexp.core.Dependency;
import conexp.core.DependencySet;

import java.util.Iterator;
import java.util.List;

public class DependencySetDumper {
    private DependencySetDumper() {
    }

    private static String dumpDependencySet(DependencySet dependencies) {
        StringBuffer res = new StringBuffer();
        res.append("Start ================================");
        final AttributeInformationSupplier attributesInformation = dependencies.getAttributesInformation();
        for (int i = 0; i < dependencies.getSize(); i++) {
            Dependency dep = dependencies.getDependency(i);
            dumpRule(res, attributesInformation, dep);
        }
        res.append("End ================================");
        return res.toString();
    }

    private static void dumpRule(StringBuffer res, final AttributeInformationSupplier attributesInformation, Dependency dep) {
        AttributeInformationSupplierUtil.describeSet(res,
                attributesInformation, dep.getPremise(), " & ", "{}");
        res.append(" -> ");
        AttributeInformationSupplierUtil.describeSet(res,
                attributesInformation, dep.getConclusion(), " & ", "{}");
        res.append('(');
        res.append(dep.getRuleSupport());
        res.append(';');
        res.append(dep.getConfidence());
        res.append(")\n");
    }

    public static String dumpRule(final AttributeInformationSupplier attributesInformation, Dependency dep) {
        StringBuffer buf = new StringBuffer();
        dumpRule(buf, attributesInformation, dep);
        return buf.toString();
    }


    public static String dumpDependencySet(DependencySet prototype, List rules) {
        return dumpDependencySet(convertListToDependencySet(prototype, rules));
    }

    private static DependencySet convertListToDependencySet(DependencySet prototype, List rules) {
        DependencySet ret = prototype.makeCompatibleDependencySet();
        for (Iterator rulesIter = rules.iterator(); rulesIter.hasNext();) {
            Dependency dependency = (Dependency) rulesIter.next();
            ret.addDependency(dependency);
        }
        return ret;
    }
}
