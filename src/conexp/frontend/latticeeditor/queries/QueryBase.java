/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: Jun 11, 2002
 * Time: 3:10:22 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.latticeeditor.queries;

import conexp.core.*;
import conexp.frontend.latticeeditor.ConceptQuery;
import util.Assert;
import util.collection.CollectionFactory;
import util.collection.NullIterator;

import java.util.Iterator;
import java.util.List;

public abstract class QueryBase implements ConceptQuery {
    ExtendedContextEditingInterface cxt;
    Set attributeMask;

    public Set getAttributeMask() {
        return attributeMask;
    }

    public QueryBase(ExtendedContextEditingInterface cxt, Set attributeMask) {
        this.cxt = cxt;
        this.attributeMask = attributeMask;
    }

    protected ExtendedContextEditingInterface getContext(){
        return cxt;
    }

    boolean stabilityCalculated = false;
    int cachedStability  = -1;

    public boolean hasOwnObjects() {
        return getOwnObjectsCount()>0;
    }

    public boolean hasOwnAttribs() {
        return false;
    }

    public Iterator ownAttribsIterator() {
        return NullIterator.makeNull();
        //TODO: implement me
    }

    public int getStability() {
        if(!stabilityCalculated){
            cachedStability = ContextFunctions.stability(getQueryIntent(), cxt);
            stabilityCalculated = true;
        }
        return cachedStability;
    }

    public ConceptQuery makeCombinedQuery(ConceptQuery other, boolean isTop, boolean isInnermost) {
        Assert.isTrue(other instanceof QueryBase);
        QueryBase that = (QueryBase)other;
        //now requiring that core was same
        Assert.isTrue(this.getContext()==that.getContext());

        final int attributeCount = getContext().getAttributeCount();
        ModifiableSet combinedQuery = ContextFactoryRegistry.createSet(attributeCount);
        combinedQuery.copy(getQueryIntent());
        combinedQuery.or(that.getQueryIntent());

        ModifiableSet combinedAttributeMask = ContextFactoryRegistry.createSet(attributeCount);
        combinedAttributeMask.copy(getAttributeMask());
        combinedAttributeMask.or(that.getAttributeMask());

        List attributeContingent= null;
        if(isTop){
           if(hasOwnAttribs()){
               attributeContingent = CollectionFactory.createDefaultList();

               for (Iterator ownAttribsIter = ownAttribsIterator(); ownAttribsIter.hasNext();) {
                   attributeContingent.add(ownAttribsIter.next());
               }
           }
        }

        return new GenericNodeQuery(getContext(), combinedQuery, attributeContingent,
                    isInnermost, combinedAttributeMask);
    }

    public Iterator extentIterator() {
        return NullIterator.makeNull();
        //TODO: implement me

    }

    public Iterator intentIterator() {
        return NullIterator.makeNull();
        //TODO: implement me
    }

}
