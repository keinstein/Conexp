package conexp.core;

import java.util.List;
import java.util.Comparator;
import java.util.Collections;

/**
 * User: sergey
 * Date: 29/4/2005
 * Time: 15:02:23
 */
abstract class LatticeElementCollectionBase implements LatticeElementCollection {
    List collection;

    public LatticeElementCollectionBase(List collection) {
        this.collection = collection;
    }

    protected abstract LatticeElement doGet(Object collElement);

    public int getSize() {
        return collection.size();
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public LatticeElement get(int index) {
        return doGet(collection.get(index));
    }

    public ConceptIterator iterator() {
        return new EdgeCollectionIterator();
    }

    public void sort(final Comparator latticeElementComparator) {
        Collections.sort(collection, new Comparator(){
            public int compare(Object o1, Object o2) {
                LatticeElement one = doGet(o1);
                LatticeElement two = doGet(o2);
                return latticeElementComparator.compare(one, two);
            }
        });
    }


    protected abstract void removeFromOtherCollection(Object lastObject);

    private class EdgeCollectionIterator extends LatticeElement.EdgeIteratorWrapper {
        public EdgeCollectionIterator() {
            super(collection.iterator());
        }

        Object lastObject;

        public LatticeElement nextConcept() {
            lastObject = innerIterator.next();
            return doGet(lastObject);
        }

        public void remove() {
            super.remove();
            removeFromOtherCollection(lastObject);
            lastObject = null;
        }

    }


}
