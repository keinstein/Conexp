/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core.associations;

import conexp.core.Dependency;
import conexp.core.DependencySet;

public class DependencySetEstimationFiller {
    private DependencySetEstimationFiller() {
    }

    public static DependencySet fillEstimations(DependencySet dependencySet, final FrequentSetSupportSupplier supportSupplier) {
        final int totalObjectCount = supportSupplier.getTotalObjectCount();
        dependencySet.forEach(new DependencySet.DependencyProcessor() {
            public void processDependency(Dependency dependency) {
                dependency.setConclusionSupportAndTotalObjectCount(supportSupplier.supportForSet(dependency.getConclusion()),
                        totalObjectCount);
            }
        });
        return dependencySet;
    }
}
