/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core;

public class NullDependencySet extends DependencySet {
    public NullDependencySet() {
        super(new NullAttributeInformationSupplier());
    }

}
