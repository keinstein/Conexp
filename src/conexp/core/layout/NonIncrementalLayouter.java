/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.core.layout;


public abstract class NonIncrementalLayouter extends GenericLayouter {
    public NonIncrementalLayouter() {
        super();
    }

    public boolean isDone() {
        return true;
    }

    public boolean isIncremental() {
        return false;
    }
}
