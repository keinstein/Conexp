/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jun 28, 2002
 * Time: 8:04:26 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core.searchconstraints;

import conexp.core.SearchConstraint;
import conexp.core.Set;

public class NoSearchConstraint implements SearchConstraint{
    public boolean continueSearch(Set intent, int objectCount) {
        return false;
    }
}
