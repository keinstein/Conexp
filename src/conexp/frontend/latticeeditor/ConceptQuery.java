/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jun 10, 2002
 * Time: 9:45:34 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.latticeeditor;

import conexp.core.Set;

import java.util.Iterator;

public interface ConceptQuery {
    Set getQueryIntent();
    Set getAttributeMask();

    int getExtentSize();
    int getOwnObjectsCount();
    boolean hasOwnObjects();
    int getStability();

    boolean isInnermost();

    ConceptQuery makeCombinedQuery(ConceptQuery other, boolean isTop, boolean isInnermost);


    boolean hasOwnAttribs();
    int getOwnAttribsCount();
    Iterator ownAttribsIterator();

    Iterator extentIterator();

    Iterator intentIterator();

    Iterator ownObjectsIterator();
}
