/*
 * User: Serhiy Yevtushenko
 * Date: Sep 15, 2002
 * Time: 3:16:48 AM
 */
package conexp.core.associations;

import conexp.core.Dependency;
import conexp.core.DependencySet;

public class DependencySetEstimationFiller {
    public static DependencySet fillEstimations(DependencySet dependencySet, final FrequentSetSupportSupplier supportSupplier){
        final int totalObjectCount = supportSupplier.getTotalObjectCount();
        dependencySet.forEach(new DependencySet.DependencyProcessor(){
            public void processDependency(Dependency dependency) {
                dependency.setConclusionSupportAndTotalObjectCount(
                    supportSupplier.supportForSet(dependency.getConclusion()),
                        totalObjectCount
                );
            }
        });
        return dependencySet;
    }
}
