/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;

import conexp.core.Context;

public interface DependencySetCalculator extends DependencySetSupplier {
    void setContext(Context cxt);

    void findDependencies();
}
