/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jun 28, 2002
 * Time: 8:19:44 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core.searchconstraints;

import conexp.core.SearchConstraint;
import conexp.core.Set;

public class MinSupportConstrainer implements SearchConstraint{

    int minSupport;

    public MinSupportConstrainer(int minSupport) {
        this.minSupport = minSupport;
    }

    public boolean continueSearch(Set intent, int objectCount) {
        if(objectCount<minSupport){
            return false;
        }
        return true;
    }
}
