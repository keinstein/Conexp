package conexp.frontend;

/**
 *  Description of the Class
 *
 *@author     Sergey
 *@created    8 N=L 2000 3.
 */
class OidNode {
    /**
     *  Description of the Field
     */
    protected String m_name;


    public OidNode(String name) {
        m_name = name;
    }


    /**
     *  Gets the Name attribute of the OidNode object
     *
     *@return    The Name value
     */
    public String getName() {
        return m_name;
    }

    /**
     *  Sets the Name attribute of the OidNode object
     *
     *@param  name  The new Name value
     */
    public void setName(String name) {
        m_name = name;
    }

    /**
     *  Description of the Method
     *
     *@return    Description of the Returned Value
     */
    public String toString() {
        return m_name;
    }
}