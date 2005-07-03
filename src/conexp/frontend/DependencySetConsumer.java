/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;

import conexp.core.DependencySet;

public interface DependencySetConsumer {
    void setDependencySetSupplier(DependencySetSupplier dependencySetSupplier);
}
