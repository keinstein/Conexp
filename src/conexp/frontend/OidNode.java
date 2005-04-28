/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/


package conexp.frontend;


class OidNode {


    protected String m_name;


    public OidNode(String name) {
        m_name = name;
    }


    public void setName(String name) {
        m_name = name;
    }


    public String toString() {
        return m_name;
    }
}
