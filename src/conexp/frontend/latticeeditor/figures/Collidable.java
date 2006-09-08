/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend.latticeeditor.figures;



public interface Collidable {
    boolean hasCollision();

    void setCollision(boolean value);
}
