/*
 * Date: Feb 25, 2002
 * Time: 7:21:56 PM
 */
package conexp.frontend.util;

import conexp.frontend.DocumentLoader;
import conexp.frontend.DocumentWriter;
import util.StringUtil;
import util.gui.fileselector.ExtensionFileFilter;
import util.gui.fileselector.GenericFileFilter;

public class StorageFormatRecord{
    String extension;
    String description;
    DocumentLoader loader;
    DocumentWriter writer;

    public StorageFormatRecord(String description, String ext, DocumentLoader loader, DocumentWriter writer) {
        this.description = StringUtil.safeTrim(description);
        this.extension = bringExtensionToCanonicalForm(ext);
        this.loader = loader;
        this.writer = writer;
    }

    private String bringExtensionToCanonicalForm(String ext) {
        return appendDotIfNeeded(StringUtil.safeTrim(ext)).toLowerCase();
    }

    private static String appendDotIfNeeded(String name) {
        if(!name.startsWith(".")){
           name = "."+name;
        }
        return name;
    }

    public void setLoader(DocumentLoader loader) {
        this.loader = loader;
    }

    public boolean hasLoader(){
        return loader!=null;
    }

    public boolean hasWriter(){
        return writer!=null;
    }

    public DocumentLoader getLoader() {
        return loader;
    }

    public DocumentWriter getWriter() {
        return writer;
    }

    public String getExtension() {
        return extension;
    }

    public boolean acceptsExtension(String ext){
        return extension.equals(bringExtensionToCanonicalForm(ext));
    }

    public GenericFileFilter getFilter(){
        if(!StringUtil.isEmpty(description)){
            return new ExtensionFileFilter(description, getExtension());
        }else{
            return new ExtensionFileFilter(getExtension());
        }
    }
}
