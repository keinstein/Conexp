/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package cefacade.demo;

import cefacade.CEFacadeFactory;
import cefacade.ContextReducer;
import cefacade.ISimpleContext;


public class ContextCreationAndReductionDemo {
    public static void demoOne() {
        ISimpleContext context = CEFacadeFactory.makeContext();
        context.setDimension(2, 2);
        context.setObjectName(0, "Obj One");
        context.setObjectName(1, "Obj Two");
        context.setAttributeName(0, "Attr One");
        context.setAttributeName(1, "Two");

        context.setRelationAt(0, 0, true);
        context.setRelationAt(1, 0, true);

        ISimpleContext reduced = ContextReducer.getReducedContext(context);
        System.out.println(reduced.getObjectCount());
        System.out.println(reduced.getAttributeCount());
    }

    public static void main(String[] args) {
        demoOne();
    }
}
