package conexp.core.calculationstrategies;

import conexp.core.*;
import util.Assert;
import util.collection.IntStack;

/**
 *
 *@author     Sergey
 *@created    24 ���� 2000 �.
 */
public class DepthSearchArrowCalculator extends DepthSearchBinaryRelationAlgorithm implements ArrowCalculator {

    protected ModifiableSet outAttribsDelta;
    protected ModifiableSet tempObjectSet;

    //-----------------------------------------------------
    private ModifiableBinaryRelation upArrow;
    //-----------------------------------------------------
    private ModifiableBinaryRelation downArrow;
    //-----------------------------------------------------
    IntStack tmpStack = new IntStack();
    //-----------------------------------------------------------------

    public DepthSearchArrowCalculator() {
        this(null);
    }

    //-----------------------------------------------------------------
    public DepthSearchArrowCalculator(BinaryRelation rel) {
        super();
        this.rel = rel;
    }

    protected void initObjectsAndAttribs() {
        super.initObjectsAndAttribs();
        tempObjectSet = ContextFactoryRegistry.createSet(rel.getRowCount());
        outAttribsDelta = ContextFactoryRegistry.createSet(rel.getColCount());
    }

    public void tearDown() {
        super.tearDown();
        tempObjectSet = null;
        tempAttrSet = null;
        upArrow = null;
        downArrow = null;
    }

    //-----------------------------------------------------
    void doCalcDownArrow(Set objects, int depth) {
        ModifiableSet prohibitedSet = tempObjectSet;
        // here it plays this role
        //*DBG*/ System.out.println("doCalcDownArrow:====================  "+depth);
        //*DBG*/ System.out.println("prohibited "+newIntent);
        ModifiableSet _currObjects = currObjects[depth];
        _currObjects.copy(objects);
        //*DBG*/ System.out.println("objects:"+_currObjects);
        for (int i = _currObjects.length(); --i >= 0;) {
            if (_currObjects.in(i) && !prohibitedSet.in(i)) {
                newExtent.clearSet();
                Set curr = rel.getSet(i);
                //*DBG*/ System.out.println("doCalcArrow: "+i+" "+curr);
                ModifiableSet currDown = downArrow.getModifiableSet(i);
                currDown.copy(allAttrSet);
                currDown.andNot(curr);
                //*DBG*/ System.out.println("currDown : "+i+" "+currDown);
                prohibitedSet.put(i);
                for (int k = _currObjects.length(); --k >= 0;) {
                    if (_currObjects.in(k)) {
                        Set tmp = rel.getSet(k);
                        switch (curr.compare(tmp)) {
                            case Set.EQUAL:
                                prohibitedSet.put(k);
                                tmpStack.push(k);
                                break;
                            case Set.SUBSET:
                                currDown.and(tmp);
                                //*DBG*/ System.out.println(k+" :"+tmp);
                                newExtent.put(k);
                                //*DBG*/ System.out.println(i+" "+k+" :"+currDown);
                                break;
                        }
                    }
                }
                while (!tmpStack.empty()) {
                    int k = tmpStack.pop();
                    downArrow.getModifiableSet(k).copy(currDown);
                }
                doCalcDownArrow(newExtent, depth + 1);
            }
        }
        //*DBG*/ System.out.println("-----------------------------");
    }

    //-----------------------------------------------------
    void doCalcUpArrow(Set objects, Set attribs, int depth) {
        ModifiableSet prohibitedSet = tempAttrSet;
        // here it plays this role
        //*DBG*/ System.out.println("doUpCalcArrow:====================  "+depth);
        //*DBG*/ System.out.println("prohibited "+tempAttrSet);
        ModifiableSet _currObjects = currObjects[depth];
        _currObjects.copy(objects);
        //*DBG*/ System.out.println("objects:"+_currObjects);
        ModifiableSet _currAttribs = currAttribs[depth];
        _currAttribs.copy(attribs);
        //*DBG*/ System.out.println("attribs:"+_currAttribs);
        for (int j = _currAttribs.length(); --j >= 0;) {
            //objects, that lay outside from current extent
            newExtent.clearSet();
            //attribs, that have greater or equal extent with current
            newIntent.copy(_currAttribs);
            outerSet.clearSet();
            if (_currAttribs.in(j) && !prohibitedSet.in(j)) {
                for (int i = _currObjects.length(); --i >= 0;) {
                    if (_currObjects.in(i)) {
                        Set tmp = rel.getSet(i);
                        if (tmp.in(j)) {
                            newIntent.and(tmp);
                        } else {
                            outerSet.or(tmp);
                            newExtent.put(i);
                        }
                    }
                }
                outAttribsDelta.copy(newIntent);
                outAttribsDelta.andNot(outerSet);
                //*DBG*/ System.out.println( j+" outAttribsDelta="+outAttribsDelta);
                //now in outAttribsDelta attribs equal with current
                prohibitedSet.or(outAttribsDelta);

                newIntent.and(outerSet);
                //*DBG*/ System.out.println( j+" less than "+newIntent);
                //*DBG*/ System.out.println("out objects "+newExtent);
                //now in newIntent attribs with greater extent width.r.t. current
                if (!newExtent.isEmpty()) {
                    for (int i = newExtent.length(); --i >= 0;) {
                        if (newExtent.in(i)) {
                            Set tmp = rel.getSet(i);
                            //*DBG*/ System.out.println(i+" :"+tmp);
                            if (newIntent.isSubsetOf(tmp)) {
                                upArrow.getModifiableSet(i).or(outAttribsDelta);
                            }
                        }
                    }
                    if (!newIntent.isEmpty()) {
                        doCalcUpArrow(newExtent, newIntent, depth + 1);
                    }
                }
            }
        }
        //*DBG*/ System.out.println("------------------------------------");
    }
    //-----------------------------------------------------------------
    //-----------------------------------------------------------------
    /**
     *  Description of the Method
     *
     *@param  attr  Description of Parameter
     */


    //-----------------------------------------------------
    /**
     *  Description of the Method
     */
    public void calcDownArrow(ModifiableBinaryRelation downArrowRel) {
        initObjectsAndAttribs();
        downArrow = downArrowRel;

        downArrow.setDimension(rel.getRowCount(), rel.getColCount());
        newExtent.fillByOne(rel.getRowCount());
        //clearing prohibited set
        tempAttrSet.clearSet();
        downArrow.clearRelation();
        if(downArrow.getColCount()>0){
            doCalcDownArrow(newExtent, 0);
        }
    }


    //-----------------------------------------------------
    /**
     *  Description of the Method
     */
    public void calcUpArrow(ModifiableBinaryRelation upArrowRel) {
        initObjectsAndAttribs();
        this.upArrow = upArrowRel;

        int cols = rel.getColCount();
        int rows = rel.getRowCount();

        upArrow.setDimension(rows, cols);

        newIntent.fillByOne(cols);

        newExtent.fillByOne(rows);
        Assert.isTrue(newExtent.length() <= rel.getRowCount());
        //clearing prohibited set
        tempAttrSet.clearSet();

        upArrow.clearRelation();

        doCalcUpArrow(newExtent, newIntent, 0);
    }


}