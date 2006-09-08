/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.tests;



import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ConExpDependenciesTest extends TestCase {


    public static void testPackageDependencies() throws IOException {
        JDepend jdepend = new JDepend();
        jdepend.addDirectory(TestPathResolver.getProductionClassPath());
        jdepend.analyze();

        JavaPackage corePackage = jdepend.getPackage("conexp.core");
        JavaPackage frontEndPackage = jdepend.getPackage("conexp.frontend");
        JavaPackage canvasPackage = jdepend.getPackage("canvas");


        assertFalse("canvas does not depend on conexp.core", corePackage.getAfferents().contains(canvasPackage));
//        assertFalse("conexp.core does not depend on canvas", corePackage.getEfferents().contains(canvasPackage));

        assertTrue("conexp.core does not depend on canvas", packageAndChildrenDoesNotDependOnPackage(jdepend, "conexp.core", "canvas"));
        assertTrue("canvas should not depend on conexp", packageAndChildrenDoesNotDependOnPackage(jdepend, "canvas", "conexp"));

        assertTrue("Front end depends on core", corePackage.getAfferents().contains(frontEndPackage));
        assertFalse("Core does not depend on frontend", frontEndPackage.getAfferents().contains(corePackage));
        assertTrue("Core package should not depend on frontend", packageAndChildrenDoesNotDependOnPackage(jdepend,
                "conexp.core", "conexp.frontend"));
        assertTrue("Core package should not depend on research", packageAndChildrenDoesNotDependOnPackage(jdepend,
                "conexp.core", "research"));

        assertTrue("FrontEnd package should not depend on research", packageAndChildrenDoesNotDependOnPackage(jdepend,
                "conexp.frontend", "research"));
    }


    private static boolean packageAndChildrenDoesNotDependOnPackage(JDepend jDepend, String packageName, String name) {
        List childPackages = collectPackagesStartingWithName(jDepend, packageName);
        for (Iterator iterator = childPackages.iterator(); iterator.hasNext();) {
            JavaPackage aPackage = (JavaPackage) iterator.next();
            if (containsPackageWithPrefix(aPackage.getEfferents(), name)) {
                System.out.println(aPackage.getName() + " depends on " + name);
                return false;
            }
        }
        return true;
    }

    private static List collectPackagesStartingWithName(JDepend jDepend, String packageName) {
        List childPackages = new ArrayList();
        Collection packages = jDepend.getPackages();
        for (Iterator iterator = packages.iterator(); iterator.hasNext();) {
            JavaPackage aPackage = (JavaPackage) iterator.next();
            if (aPackage.getName().startsWith(packageName)) {
                childPackages.add(aPackage);
            }
        }
        return childPackages;
    }

    private static boolean containsPackageWithPrefix(Collection javaPackages, String name) {
        for (Iterator iterator = javaPackages.iterator(); iterator.hasNext();) {
            JavaPackage javaPackage = (JavaPackage) iterator.next();
            if (javaPackage.getName().startsWith(name)) {
                return true;
            }
        }
        return false;
    }

    public static void printOutPackages(String prefix, Collection javaPackagesNames) {
        System.out.print(prefix + " : ");
        boolean first = true;
        for (Iterator iterator = javaPackagesNames.iterator(); iterator.hasNext();) {
            if (first) {
                first = false;
            } else {
                System.out.print(", ");
            }
            JavaPackage javaPackage = (JavaPackage) iterator.next();
            System.out.print(javaPackage.getName());
        }
        System.out.println();
    }
}
