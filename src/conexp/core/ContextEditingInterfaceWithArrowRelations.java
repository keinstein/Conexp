/*
 * User: sergey
 * Date: Dec 13, 2001
 * Time: 7:54:30 PM
 */
package conexp.core;

public interface ContextEditingInterfaceWithArrowRelations extends ContextEditingInterface {

    public boolean hasDownArrow(int row, int col);

    //---------------------------------------------------------------
    public boolean hasUpArrow(int row, int col);
}
