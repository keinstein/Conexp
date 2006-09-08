/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package canvas.figures;


public interface ICenterPointLocatorProvidingFigure {
    CenterPointLocator getCenterPointLocator();

    void setCenterPointLocator(CenterPointLocator centerPointLocator);
}
