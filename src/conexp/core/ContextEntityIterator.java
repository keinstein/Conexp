/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 1, 2001
 * Time: 5:40:54 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class ContextEntityIterator implements Iterator {

    final ExtendedContextEditingInterface cxt;
    final Set entities;
    int currEntity = Set.NOT_IN_SET;

    public ContextEntityIterator(ExtendedContextEditingInterface cxt, Set entities) {
        this.cxt = cxt;
        this.entities = entities;
        checkConsistency();
        currEntity = entities.firstIn();
    }

    public boolean hasNext() {
        return currEntity != Set.NOT_IN_SET;
    }

    protected abstract void checkConsistency();

    protected abstract ContextEntity getEntity(int entityIndex);

    public Object next() {
        if (currEntity == Set.NOT_IN_SET) {
            throw new NoSuchElementException();
        }
        int retIndex = currEntity;
        currEntity = entities.nextIn(currEntity);
        return getEntity(retIndex);
    }

    public void remove() {
        throw new UnsupportedOperationException("Context modification isn't supported through iterators");
    }

}
