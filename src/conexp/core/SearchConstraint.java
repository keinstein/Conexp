/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jun 26, 2002
 * Time: 5:39:19 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core;



public interface SearchConstraint {
    public boolean continueSearch(Set  intent, int objectCount);
}
