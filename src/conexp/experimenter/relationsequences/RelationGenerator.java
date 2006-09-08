/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.relationsequences;

import conexp.core.ContextFactoryRegistry;
import conexp.core.ModifiableBinaryRelation;
import conexp.core.ModifiableSet;

import java.util.Random;


public class RelationGenerator {
    private RelationGenerator() {
    }

    /**
     * Insert the method's description here.
     * Creation date: (28.02.01 23:45:51)
     *
     * @param size int
     * @return conexp.core.BinaryRelation
     */
    public static ModifiableBinaryRelation makeChain(int size) {
        ModifiableBinaryRelation ret = ContextFactoryRegistry.createRelation(size, size);
        ModifiableSet temp = ContextFactoryRegistry.createSet(size);
        temp.fill();
        for (int i = size; --i >= 0;) {
            ret.getModifiableSet(i).copy(temp);
            temp.remove(i);
        }
        return ret;
    }


    /**
     * Insert the method's description here.
     * Creation date: (28.02.01 23:45:51)
     *
     * @param size int
     * @return conexp.core.BinaryRelation
     */
    public static ModifiableBinaryRelation makeEmpty(int size) {
        return ContextFactoryRegistry.createRelation(size, size);
    }


    /**
     * Insert the method's description here.
     * Creation date: (28.02.01 23:45:51)
     *
     * @param size int
     * @return conexp.core.BinaryRelation
     */
    public static ModifiableBinaryRelation makeExponential(int size) {
        ModifiableBinaryRelation ret = ContextFactoryRegistry.createRelation(size, size);
        ModifiableSet temp = ContextFactoryRegistry.createSet(size);
        temp.fill();
        for (int i = size; --i >= 0;) {
            ModifiableSet curr = ret.getModifiableSet(i);
            curr.copy(temp);
            curr.remove(i);
        }
        return ret;
    }


    /**
     * Insert the method's description here.
     * Creation date: (28.02.01 23:45:51)
     *
     * @param sizeX int
     * @param sizeY int
     * @return conexp.core.BinaryRelation
     */
    public static ModifiableBinaryRelation makeFilledWithMaxIntent(int sizeX, int sizeY, int maxIntent) {
        ModifiableBinaryRelation ret = ContextFactoryRegistry.createRelation(sizeX, sizeY);
        Random randGen = new Random(); //based on current time
        for (int i = sizeX; --i >= 0;) {
            ModifiableSet curr = ret.getModifiableSet(i);
            for (int k = maxIntent; --k >= 0;) {
                double next = randGen.nextDouble();
                int pos = (int) (next * sizeY);
                curr.put(pos);
            }
        }
        return ret;
    }


    public static ModifiableBinaryRelation makeFilledWithMinMaxIntent(int sizeX, int sizeY, int minIntent, int maxIntent) {
        ModifiableBinaryRelation ret = ContextFactoryRegistry.createRelation(sizeX, sizeY);
        int delta = maxIntent - minIntent;
        Random randGen = new Random(); //based on current time
        for (int i = sizeX; --i >= 0;) {
            ModifiableSet curr = ret.getModifiableSet(i);
            int currDelta = randGen.nextInt() % delta; // check for 0 delta;
            int currSize = minIntent + currDelta;
            for (int k = currSize; --k >= 0;) {
                int pos = randGen.nextInt() % sizeY; //we don't check for repetition
                curr.put(pos);
            }
        }
        return ret;
    }


    public static ModifiableBinaryRelation makeFilledWithPercent(int sizeX, int sizeY, float fillFactor) {
        ModifiableBinaryRelation ret = ContextFactoryRegistry.createRelation(sizeX, sizeY);
        Random randGen = new Random(); //based on current time
        for (int i = sizeX; --i >= 0;) {
            ModifiableSet curr = ret.getModifiableSet(i);
            for (int j = sizeY; --j >= 0;) {
                double next = randGen.nextDouble();
                if (next < fillFactor) {
                    curr.put(j);
                }
            }
        }
        return ret;
    }


    /**
     * Insert the method's description here.
     * Creation date: (28.02.01 23:45:51)
     *
     * @param size int
     * @return conexp.core.BinaryRelation
     */
    public static ModifiableBinaryRelation makeFull(int size) {
        ModifiableBinaryRelation ret = ContextFactoryRegistry.createRelation(size, size);
        ModifiableSet temp = ContextFactoryRegistry.createSet(size);
        temp.fill();
        for (int i = size; --i >= 0;) {
            ModifiableSet curr = ret.getModifiableSet(i);
            curr.copy(temp);
        }
        return ret;
    }


    /**
     * Insert the method's description here.
     * Creation date: (28.02.01 23:45:51)
     *
     * @param size int
     * @return conexp.core.BinaryRelation
     */
    public static ModifiableBinaryRelation makeNominal(int size) {
        ModifiableBinaryRelation ret = ContextFactoryRegistry.createRelation(size, size);
        for (int i = size; --i >= 0;) {
            ret.getModifiableSet(i).put(i);
        }
        return ret;
    }
}
