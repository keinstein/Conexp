package conexp.core;

/**
 * Author: Serhiy Yevtushenko
 * Date: Jan 5, 2003
 * Time: 11:48:19 PM
 */
public interface ExtendedContextEditingInterface extends ContextEditingInterfaceWithArrowRelations {
    void purifyAttributes();

    void purifyObjects();

    void reduceAttributes();

    void reduceObjects();

    void transpose();
}
