/**
 * Copyright (c) 2000-2003, Sergey Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/

package conexp.core;


public interface EdgeIterator {
    public boolean hasNextEdge();

    public Edge nextEdge();
}
