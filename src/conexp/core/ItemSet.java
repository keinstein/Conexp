/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jun 24, 2002
 * Time: 7:21:18 PM
 * To change template for new interface use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core;

public interface ItemSet {
    int LESS = 0;
    int EQUAL = LESS + 1;
    int GREATER = EQUAL + 1;
    int NOT_COMPARABLE = GREATER + 1;

    int compare(ItemSet that);

    int getObjCnt();

    Set getAttribs();

    int getIndex();
}
