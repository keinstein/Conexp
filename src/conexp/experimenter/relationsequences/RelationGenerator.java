package conexp.experimenter.relationsequences;

import conexp.core.ContextFactoryRegistry;
import conexp.core.ModifiableBinaryRelation;
import conexp.core.ModifiableSet;

/**
 * Insert the type's description here.
 * Creation date: (28.02.01 23:44:56)
 * @author
 */
public class RelationGenerator {
    /**
     * Insert the method's description here.
     * Creation date: (28.02.01 23:45:51)
     * @return conexp.core.BinaryRelation
     * @param size int
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
     * @return conexp.core.BinaryRelation
     * @param size int
     */
    public static ModifiableBinaryRelation makeEmpty(int size) {
        return ContextFactoryRegistry.createRelation(size, size);
    }


    /**
     * Insert the method's description here.
     * Creation date: (28.02.01 23:45:51)
     * @return conexp.core.BinaryRelation
     * @param size int
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
     * @return conexp.core.BinaryRelation
     * @param sizeX int
     * @param sizeY int
     */
    public static ModifiableBinaryRelation makeFilledWithMaxIntent(int sizeX, int sizeY, int maxIntent) {
        ModifiableBinaryRelation ret = ContextFactoryRegistry.createRelation(sizeX, sizeY);
        java.util.Random randGen = new java.util.Random(); //based on current time
        for (int i = sizeX; --i >= 0;) {
            ModifiableSet curr = ret.getModifiableSet(i);
            for (int k = maxIntent; --k >= 0;) {
                double next = randGen.nextDouble();
                int pos = ((int) (next * sizeY));
                curr.put(pos);
            }
        }
        return ret;
    }


    public static ModifiableBinaryRelation makeFilledWithMinMaxIntent(int sizeX, int sizeY, int minIntent, int maxIntent) {
        ModifiableBinaryRelation ret = ContextFactoryRegistry.createRelation(sizeX, sizeY);
        int delta = maxIntent - minIntent;
        java.util.Random randGen = new java.util.Random(); //based on current time
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
        java.util.Random randGen = new java.util.Random(); //based on current time
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
     * @return conexp.core.BinaryRelation
     * @param size int
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
     * @return conexp.core.BinaryRelation
     * @param size int
     */
    public static ModifiableBinaryRelation makeNominal(int size) {
        ModifiableBinaryRelation ret = ContextFactoryRegistry.createRelation(size, size);
        for (int i = size; --i >= 0;) {
            ret.getModifiableSet(i).put(i);
        }
        return ret;
    }
}