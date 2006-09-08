/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.tests;


public class TestPathResolver {
    static final String TEST_CLASSPATH = "TEST_CLASSPATH";
    static final String PRODUCTION_CLASSPATH = "PRODUCTION_CLASSPATH";

    public static String getTestPath(String relativePath) {
        return getTestClassPath() + relativePath;
    }

    private static String getTestClassPath() {
        return System.getProperty(TEST_CLASSPATH, "");
    }

    public static String getProductionPath(String relativePath) {
        return getProductionClassPath() + relativePath;
    }

    public static String getProductionClassPath() {
        return System.getProperty(PRODUCTION_CLASSPATH, "");
    }
}
