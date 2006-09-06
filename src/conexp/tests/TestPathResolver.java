package conexp.tests;

/**
 * Class for resolving pathes of the files used in tests.
 * Was created in order to allow to build tests both in ant and maven
 *
 * @author Serhiy Yevtushenko
 */
public class TestPathResolver {
    static final String TEST_CLASSPATH = "TEST_CLASSPATH";
    static final String PRODUCTION_CLASSPATH = "PRODUCTION_CLASSPATH";

    public static String getTestPath(String relativePath) {
        return getTestClassPath() +relativePath;
    }

    private static String getTestClassPath() {
        return System.getProperty(TEST_CLASSPATH, "");
    }

    public static String getProductionPath(String relativePath) {
        return getProductionClassPath() +relativePath;
    }

    public static String getProductionClassPath() {
        return System.getProperty(PRODUCTION_CLASSPATH, "");
    }
}
