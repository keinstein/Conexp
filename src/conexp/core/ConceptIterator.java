package conexp.core;

import java.util.Iterator;

public interface ConceptIterator extends Iterator{
    LatticeElement nextConcept();
}