/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Dec 8, 2001
 * Time: 11:06:23 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core.compareutils;

public class DefaultCompareInfoFactory implements CompareInfoFactory {
    public CompareInfo makeCompareInfo(Object obj, int type) {
        return new CompareInfo(obj, type);
    }
}
