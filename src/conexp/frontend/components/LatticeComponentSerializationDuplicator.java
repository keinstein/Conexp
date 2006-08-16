package conexp.frontend.components;

import conexp.frontend.io.ConExpXMLReader;
import conexp.frontend.io.ConExpXMLWriter;
import org.jdom.Element;
import util.DataFormatException;
import util.XMLHelper;

/**
 * User: sergey
 * Date: 23/5/2005
 * Time: 16:20:53
 */
public class LatticeComponentSerializationDuplicator implements ILatticeComponentDuplicator {
    public LatticeComponent duplicate(LatticeComponent toCopy) {

        LatticeComponent copy = new LatticeComponent(toCopy.getContext(), toCopy.attributeMask.makeCopy(),
                toCopy.objectMask.makeCopy());
        if(toCopy.isRecalcLatticeOnMaskChange()){
            copy.setUpLatticeRecalcOnMasksChange();
        }

        //todo: move storing isRecalcLatticeOnMaskChange to Reader/Writer pair
        return duplicateContent(toCopy, copy);
    }

    public LatticeComponent duplicateContent(LatticeComponent toCopy, LatticeComponent copy) {
        try {
            Element latticeElement = ConExpXMLWriter.makeLatticeElement(toCopy);
            System.out.println(XMLHelper.xmlElementDump(latticeElement));
            ConExpXMLReader.loadLatticeComponent(copy, latticeElement);
        } catch (DataFormatException e) {

        }
        return copy;
    }


}
