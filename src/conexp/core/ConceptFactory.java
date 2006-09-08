/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.core;


public class ConceptFactory {
    private ConceptFactory() {
    }

    public static LatticeElement makeEmptyLatticeElement() {
        return LatticeElement.makeLatticeElementFromSets(ContextFactoryRegistry.createSet(0), ContextFactoryRegistry.createSet(0));
    }

    public static LatticeElement makeVirtual() {
        return new VirtualLatticeElement();
    }

    public static LatticeElement makeLatticeElementWithOwnAttribs() {
        LatticeElement el = makeEmptyLatticeElement();
        el.addOwnAttrib(ContextEntity.createContextAttribute("Attrib1"));
        el.addOwnAttrib(ContextEntity.createContextAttribute("Attrib2"));
        return el;
    }

    public static LatticeElement makeLatticeElementWithOwnObjects() {
        LatticeElement el = makeEmptyLatticeElement();
        el.addOwnObject(ContextEntity.createContextObject("Obj1"));
        el.addOwnObject(ContextEntity.createContextObject("Obj2"));
        return el;
    }
}
