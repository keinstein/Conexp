/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jul 25, 2002
 * Time: 4:12:49 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core.utils;

import conexp.core.ContextFactoryRegistry;
import conexp.core.ModifiableSet;
import util.Assert;

public class PowerSetIterator {

    protected ModifiableSet current;
    protected ModifiableSet next = null;
    protected int sizeOfSet;

    public PowerSetIterator(int size) {
        Assert.isTrue(size>=0);
        current = ContextFactoryRegistry.createSet(size);
        next = current;
        sizeOfSet = current.size();
    }


    public boolean hasNext() {
        return next!=null;
    }

    public ModifiableSet nextSet() {
        Assert.isTrue(hasNext());
        ModifiableSet toReturn = next.makeModifiableSetCopy();
        next = null;
        boolean wasModified = false;
        for(int i=sizeOfSet; --i>=0; ){
            if(!current.in(i)){
                current.put(i);
                wasModified = true;
                break;
            }
            current.remove(i);
        }
        if(wasModified){
            next = current;
        }
        return toReturn;
    }

}
