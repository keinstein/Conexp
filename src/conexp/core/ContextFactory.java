/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 7, 2001
 * Time: 9:19:47 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core;

public interface ContextFactory {
    ModifiableBinaryRelation createRelation(int rowCount, int colCount);

    ModifiableSet createSet(int size);
}
