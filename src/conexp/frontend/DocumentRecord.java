/*
 * Created by IntelliJ IDEA.
 * User: Serhiy Yevtushenko
 * Date: May 17, 2002
 * Time: 10:58:00 PM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import util.FileNameMangler;
import util.StringUtil;

public class DocumentRecord{
    FileNameMangler pathFormer;
    boolean hasCorrespondingFile=false;

    public DocumentRecord(Document document, String url) {
        if(null==document){
            throw new IllegalArgumentException();
        }
        this.document = document;
        this.pathFormer = new FileNameMangler();
        setDocumentUrl(url);
    }

    public void setDocumentUrl(String url) {
        if (!StringUtil.isEmpty(url)) {
            hasCorrespondingFile = true;
            pathFormer.setCurrFileName(url);
        }
    }

    public boolean hasCorrespondingFile() {
        return hasCorrespondingFile;
    }

    public Document getDocument() {
        return document;
    }

    public String getDocumentDirectory(){
        return pathFormer.getOutPath();
    }

    public String getDocumentPath(){
        return pathFormer.getCurrFileName();
    }

    public String getDocumentFileName(){
        return pathFormer.getFileName();
    }

    Document document;
}
