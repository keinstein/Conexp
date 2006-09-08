/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.components;


public class LatticeComponentDuplicatorService {
    static ILatticeComponentDuplicator ourInstance = new LatticeComponentSerializationDuplicator();

    public static ILatticeComponentDuplicator getInstance() {
        return ourInstance;
    }

}
