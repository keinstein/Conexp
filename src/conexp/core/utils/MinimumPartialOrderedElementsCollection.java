package conexp.core.utils;

import conexp.core.IPartiallyOrdered;
import util.collection.CollectionFactory;
import util.collection.IndexedSet;

import java.util.Iterator;
import java.util.Set;

/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 */
public class MinimumPartialOrderedElementsCollection {
    Set elements = CollectionFactory.createDefaultSet();

    public void clear(){
        elements.clear();
    }

    public Iterator iterator(){
        return elements.iterator();
    }

    public boolean isEmpty(){
        return elements.isEmpty();
    }

    public boolean contains(IPartiallyOrdered ordered){
        return elements.contains(ordered);
    }

    public void add(IPartiallyOrdered ordered) {
        for (Iterator iterator = elements.iterator(); iterator.hasNext();) {
            IPartiallyOrdered partiallyOrdered = (IPartiallyOrdered) iterator.next();
            if(ordered.isLesserThan(partiallyOrdered)){
                iterator.remove();
            }
            if(partiallyOrdered.isLesserThan(ordered)){
                return;
            }
        }
        elements.add(ordered);
    }

    public int getSize() {
        return elements.size();
    }

    public String toString() {
        return elements.toString();
    }

    public Object[] toArray(){
        return elements.toArray();
    }

    public Object[] toArray(Object[] array){
        return elements.toArray(array);
    }
}
